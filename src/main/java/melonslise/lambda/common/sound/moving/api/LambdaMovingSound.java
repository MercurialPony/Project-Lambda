package melonslise.lambda.common.sound.moving.api;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

// TODO Generic entity
// TODO Move?
// TODO Rename?
// TODO Constructor volume
public class LambdaMovingSound extends MovingSound
{
	protected Entity entity;

	public LambdaMovingSound(Entity entity, SoundEvent sound, SoundCategory category)
	{
		super(sound, category);
		this.entity = entity;
		this.repeat = true;
		this.repeatDelay = 0;
	}

	@Override
	public void update()
	{
		if(this.entity.isDead) this.donePlaying = true;
		this.xPosF = (float) this.entity.posX;
		this.yPosF = (float) this.entity.posY;
		this.zPosF = (float) this.entity.posZ;
	}
}