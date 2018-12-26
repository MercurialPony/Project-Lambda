package melonslise.lambda.common.entity;

import melonslise.lambda.common.entity.api.AEntityCollidable;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

// TODO Rename?
// TODO Write existed ticks and life to nbt?
public class EntityGrenade extends AEntityCollidable
{
	protected int life;

	public EntityGrenade(World world)
	{
		super(world);
		this.setSize(0.2F, 0.2F);
	}

	public EntityGrenade(World world, int life)
	{
		this(world);
		this.life = life;
	}

	@Override
	protected void entityInit() {}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(!this.world.isRemote) System.out.println(this.ticksExisted);
		if(this.ticksExisted >= this.life || this.isBurning()) this.explode();
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

	public float getFriction()
	{
		return 0.9F;
	}
}