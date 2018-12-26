package melonslise.lambda.common.entity.api;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;

public abstract class AEntityProjectile extends Entity
{
	protected int ticksAir;
	public EntityLivingBase owner;

	public AEntityProjectile(World world)
	{
		super(world);
	}

	// TODO Try to merge?
	public void fire(EntityLivingBase entity, double speed, float inaccuracy)
	{
		this.owner = entity;
		this.fire(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ, entity.getLookVec(), speed, inaccuracy);
	}

	// TODO Add motion?
	// TODO Offset param?
	public void fire(EntityLivingBase entity, EnumHand hand, double speed, float inaccuracy)
	{
		this.owner = entity;
		Vec3d start = new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
		double length = MathHelper.clamp(inaccuracy == 0 ? 64D : Math.PI * 2.5D / MathHelper.sqrt(inaccuracy), 0D, 64D);
		Vec3d end = entity.getLookVec().scale(length).add(start);
		RayTraceResult result = this.world.rayTraceBlocks(start, end, false, true, false);
		if(result != null) end = result.hitVec;
		Vec3d offset = start.add(LambdaUtilities.getHeldOffset(entity, hand, new Vec3d(-0.3D, -0.15D, 0.5D)));
		this.fire(offset.x, offset.y, offset.z, end.subtract(offset), speed, inaccuracy);
	}

	// TODO Move to utils?
	public void fire(double x, double y, double z, Vec3d direction, double speed, float inaccuracy)
	{
		this.setPosition(x, y, z);
		direction = direction.normalize();
		Vec3d motion = direction;
		if(inaccuracy != 0F) motion = LambdaUtilities.sampleSphereCap(direction, inaccuracy);
		motion = motion.scale(speed);
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;
		LambdaUtilities.rotateTowardsMotion(this, 1F);
	}

	// TODO Event?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(this.ticksExisted > 600)
		{
			this.setDead();
			return;
		}
		++this.ticksAir;
		Vec3d start = this.getPositionVector();
		Vec3d end = start.addVector(this.motionX, this.motionY, this.motionZ);
		RayTraceResult result = null;
		ArrayList<RayTraceResult> results = LambdaUtilities.rayTraceBlocks(this.world, start, start.addVector(this.motionX, this.motionY, this.motionZ), false, true, this.getPenetratedBlocks());
		if(!results.isEmpty() && (this.getPenetratedBlocks() == null || !this.getPenetratedBlocks().apply(this.world.getBlockState(results.get(results.size() - 1).getBlockPos()))))
		{
			result = results.get(results.size() - 1);
			end = results.get(results.size() - 1).hitVec;
			results.remove(results.size() - 1);
		}
		RayTraceResult result1 = LambdaUtilities.rayTraceClosestEntity(this.world, start, end, this.ticksAir >= 20 ? Lists.newArrayList(this) : Lists.newArrayList(this, this.owner), LambdaSelectors.PROJECTILE_TARGETS);
		if(result1 != null)
		{
			end = result1.hitVec;
			result = result1;
		}
		double distance = start.squareDistanceTo(end);
		Iterator<RayTraceResult> iterator = results.iterator();
		while(iterator.hasNext())
		{
			RayTraceResult result2 = iterator.next();
			if(start.squareDistanceTo(result2.hitVec) < distance && !ForgeEventFactory.onProjectileImpact(this, result2)) this.onHit(result2);
		}
		if(result != null && !ForgeEventFactory.onProjectileImpact(this, result)) this.onHit(result); // TODO Own event?
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		this.setPosition(this.posX, this.posY, this.posZ);
		if (this.isInWater())
		{
			for (int a = 0; a < 4; ++a) this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
			double multiplier = this.getWaterResistance();
			this.motionX *= multiplier;
			this.motionY *= multiplier;
			this.motionZ *= multiplier;
		}
		if(!this.hasNoGravity()) this.motionY -= this.getGravity();
	}

	protected Predicate<IBlockState> getPenetratedBlocks()
	{
		return null;
	}

	protected abstract void onHit(RayTraceResult result);

	public double getWaterResistance()
	{
		return 0.9D;
	}

	public double getGravity()
	{
		return 0.04D;
	}

	public static final String keyOwner = "owner";

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		if(this.owner != null) nbt.setUniqueId(keyOwner, this.owner.getUniqueID());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		if(nbt.hasKey(keyOwner)) this.owner = (EntityLivingBase) ((WorldServer) this.world).getEntityFromUuid(nbt.getUniqueId(keyOwner));
	}

	@Override
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10D;
		if (Double.isNaN(d0)) d0 = 1D;
		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}
}