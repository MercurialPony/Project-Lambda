package melonslise.lambda.common.entity.projectile;

import com.google.common.collect.Lists;

import melonslise.lambda.common.entity.api.AEntityProjectile;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

// TODO Hornet wiggly flight path
// TODO Fix going through blocks
// TODO Configurable speed
public class EntityHornet extends AEntityProjectile
{
	protected Entity target;
	protected double speed = 0.8D;
	protected boolean homing;

	public EntityHornet(World world)
	{
		super(world);
		this.setSize(0.1F, 0.1F);
		this.setNoGravity(true);
	}

	public EntityHornet(World world, boolean homing)
	{
		this(world);
		this.homing = homing;
	}

	/*
	@Override
	public void fire(Entity entity, double speed)
	{
		super.fire(entity, speed);
		this.speed = speed;
	}

	@Override
	public void fire(Vec3d motion)
	{
		super.fire(motion);
		this.speed = motion.lengthVector();
	}
	 */

	@Override
	protected void entityInit() {}

	// TODO Sound cat
	// TODO Improve/shorten
	// TODO Die in water?
	@Override
	protected void onHit(RayTraceResult result)
	{
		if(this.world.isRemote) return;
		this.playSound(LambdaSounds.alien_hornet_buzz, 1F, 1F);
		if(result.entityHit != null) this.damage(result.entityHit);
		this.setDead();
	}

	// TODO Damage
	// TODO Rename?
	protected void damage(Entity entity)
	{
		this.world.playSound(null, this.posX, this.posY, this.posZ, LambdaSounds.alien_hornet_hit, SoundCategory.PLAYERS, 1F, 1F);
		if(entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner), 3F)) entity.hurtResistantTime = 0;
	}

	// TODO Range
	// TODO Center fix
	// TODO Entity is not dead checks
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		LambdaUtilities.rotateTowardsMotion(this, 0.9F);
		if(!this.world.isRemote) if(this.isInWater() || this.ticksExisted > 120) this.setDead();
		if(!this.world.isRemote && this.homing)
		{
			// TODO Don't target pets and shit
			Vec3d position = this.getPositionVector();
			if(this.target == null) { if(this.ticksExisted % 3 == 0) this.target = LambdaUtilities.getClosestEntity(position, LambdaUtilities.subtract(this.world.getEntitiesInAABBexcluding(this, LambdaUtilities.createAABB(position, position).grow(3D), LambdaSelectors.HORNET_TARGETS), Lists.newArrayList(this.owner)), null); }
			else this.fire(this.posX, this.posY, this.posZ, LambdaUtilities.getCenter(this.target.getEntityBoundingBox()).subtract(position).normalize(), speed, 0F);
		}
	}

	public static final String keyHoming = "homing";

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		//nbt.setDouble(keySpeed, this.speed);
		nbt.setBoolean(keyHoming, this.homing);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		//this.speed = nbt.getDouble(keySpeed);
		this.homing = nbt.getBoolean(keyHoming);
	}
}