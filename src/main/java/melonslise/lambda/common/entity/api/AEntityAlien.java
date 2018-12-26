package melonslise.lambda.common.entity.api;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class AEntityAlien extends EntityMob
{
	public AEntityAlien(World world)
	{
		super(world);
	}

	@Override
	public float getSoundPitch()
	{
		return 1F;
	}

	// TODO Helper / play sound methods for null check
	@Override
	public void setAttackTarget(@Nullable EntityLivingBase entity)
	{
		if(entity != null && this.getAttackTarget() != entity) this.playSound(this.getAlertSound(), this.getSoundVolume(), this.getSoundPitch());
		super.setAttackTarget(entity);
	}

	protected abstract SoundEvent getAlertSound();
}