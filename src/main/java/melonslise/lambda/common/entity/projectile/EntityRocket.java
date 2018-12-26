package melonslise.lambda.common.entity.projectile;

import melonslise.lambda.common.entity.api.AEntityProjectile;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

// TODO Turning speed
// TODO Box
// TODO Own indirect source
// TODO Damage configure
// TODO Speed configurable
public class EntityRocket extends AEntityProjectile
{
	protected double speed = 2D;
	protected boolean mode;

	public EntityRocket(World world)
	{
		super(world);
		this.setNoGravity(true);
	}

	public EntityRocket(World world, boolean mode)
	{
		this(world);
		this.mode = mode;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void onHit(RayTraceResult result)
	{
		this.explode();
	}

	// TODO Distance
	// TODO Hit entities
	// TODO Side?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		LambdaUtilities.rotateTowardsMotion(this, 0.4F);
		if(this.isBurning()) this.explode();
		if(this.owner == null || this.isInWater()) this.mode = false;
		if(this.mode)
		{
			RayTraceResult result = LambdaUtilities.rayTraceBlocks(this.owner, 64D, false ,true);
			Vec3d end = result != null ? result.hitVec : this.owner.getLookVec().scale(64D).addVector(this.owner.posX, this.owner.posY, this.owner.posZ);
			this.fire(this.posX, this.posY, this.posZ, new Vec3d(end.x - this.posX, end.y - this.posY, end.z - this.posZ).normalize(), this.speed, 0F);
		}
	}

	// TODO Configurable damage?
	private void explode()
	{
		if(!this.world.isRemote)
		{
			this.world.createExplosion(this.owner != null ? this.owner : this, this.posX, this.posY, this.posZ, 4F, ForgeEventFactory.getMobGriefingEvent(this.world, this));
			this.setDead();
		}
	}

	@Override
	public double getWaterResistance()
	{
		return 0.7D;
	}

	// TODO Rename
	public static final String keySpeed = "speed",  keyMode = "mode";

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setDouble(keySpeed, this.speed);
		nbt.setBoolean(keyMode, this.mode);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.speed = nbt.getDouble(keySpeed);
		this.mode = nbt.getBoolean(keyMode);
	}
}