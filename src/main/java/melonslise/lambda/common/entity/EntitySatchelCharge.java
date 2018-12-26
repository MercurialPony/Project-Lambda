package melonslise.lambda.common.entity;

import java.util.concurrent.ThreadLocalRandom;

import melonslise.lambda.common.entity.api.AEntityCollidable;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntitySatchelCharge extends AEntityCollidable
{
	public float motionYaw;

	// TODO Rotations
	public EntitySatchelCharge(World world)
	{
		super(world);
		this.setSize(0.4F, 0.2F);
		this.motionYaw = (ThreadLocalRandom.current().nextFloat() * 2F + 4F) * (float) Math.PI;
	}

	@Override
	protected void entityInit() {}

	// TODO Configurable motion
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.rotationYaw -= this.motionYaw;
		if(this.motionYaw <= 0F) this.motionYaw = 0F;
		else this.motionYaw -= 1F;
		if(this.isBurning()) this.explode();
	}

	public void explode()
	{
		if(!this.world.isRemote)
		{
			this.world.createExplosion(this.owner != null ? this.owner : this, this.posX, this.posY, this.posZ, 4F, ForgeEventFactory.getMobGriefingEvent(this.world, this));
			this.setDead();
		}
	}

	public float getFriction()
	{
		return 1.2F;
	}
}