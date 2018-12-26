package melonslise.lambda.common.entity.projectile;

import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityBolt extends EntityBullet
{
	public EntityBolt(World world)
	{
		super(world);
	}

	public EntityBolt(World world, float damage, int soundChance)
	{
		super(world, damage, soundChance);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		LambdaUtilities.rotateTowardsMotion(this, 0.2F);
	}

	@Override
	public double getWaterResistance()
	{
		return 0.85D;
	}

	@Override
	public double getGravity()
	{
		return 0.004D;
	}

	@Override
	public SoundEvent getHitBodySound()
	{
		return LambdaSounds.projectile_bolt_hitbody;
	}

	@Override
	public SoundEvent getHitSound()
	{
		return LambdaSounds.projectile_bolt_hit;
	}
}