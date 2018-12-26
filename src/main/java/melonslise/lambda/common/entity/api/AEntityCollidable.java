package melonslise.lambda.common.entity.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

// TODO Figure out/fix constructors for inheriting classes
// TODO Rename?
public abstract class AEntityCollidable extends Entity
{
	protected EntityLivingBase owner;

	public AEntityCollidable(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit() {}

	// TODO Copy from Projectile
	public void fire(EntityLivingBase entity, double speed)
	{
		this.owner = entity;
		this.fire(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ, entity.getLookVec().scale(speed));
	}

	// TODO Rotate correctly
	public void fire(double x, double y, double z, Vec3d motion)
	{
		this.setPosition(x, y, z);
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;
	}

	// TODO Configurable bounciness?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		double oldX = this.motionX, oldY = this.motionY, oldZ = this.motionZ;
		if(!this.hasNoGravity()) this.motionY -= this.getGravity();
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		float multiplier = 0.98F;
		if (this.onGround)
		{
			BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
			IBlockState underState = this.world.getBlockState(underPos);
			multiplier = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * this.getFriction();
		}
		this.motionX *= (double) multiplier;
		this.motionY *= 0.98D;
		this.motionZ *= (double) multiplier;
		if(this.onGround) this.motionY *= -0.5D;
		this.handleWaterMovement();
		if (!this.world.isRemote)
		{
			double deltaX = this.motionX - oldX, deltaY = this.motionY - oldY, deltaZ = this.motionZ - oldZ;
			if(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 0.01D) this.isAirBorne = true;
		}
	}

	public double getGravity()
	{
		return 0.04D;
	}

	public float getFriction()
	{
		return 0.98F;
	}

	public static final String keyOwner = "owner";

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		if(nbt.hasKey(keyOwner)) this.owner = (EntityLivingBase) ((WorldServer) this.world).getEntityFromUuid(nbt.getUniqueId(keyOwner));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		if(this.owner != null) nbt.setUniqueId(keyOwner, this.owner.getUniqueID());
	}
}