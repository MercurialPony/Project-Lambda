package melonslise.lambda.common.entity.projectile;

import melonslise.lambda.common.entity.api.AEntityProjectile;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

// TODO Increase damage
// TODO indirect source
public class EntityImpactGrenade extends AEntityProjectile
{
	public EntityImpactGrenade(World world)
	{
		super(world);
		this.setSize(0.1F, 0.1F);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void onHit(RayTraceResult result)
	{
		this.explode();
	}

	// TODO Side?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		LambdaUtilities.rotateTowardsMotion(this, 0.3F);
		if (this.isBurning()) this.explode();
	}

	// TODO Configurable damage?
	private void explode()
	{
		if(!this.world.isRemote)
		{
			this.world.createExplosion(this.owner != null ? this.owner : this, this.posX, this.posY, this.posZ, 2F, ForgeEventFactory.getMobGriefingEvent(this.world, this));
			this.setDead();
		}
	}
}