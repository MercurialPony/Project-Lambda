package melonslise.lambda.common.sound.moving;

import melonslise.lambda.common.entity.alien.EntityHoundeye;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundHoundeye extends MovingSound
{
	protected EntityHoundeye entity;

	public MovingSoundHoundeye(EntityHoundeye entity, SoundEvent sound, SoundCategory category)
	{
		super(sound, category);
		this.entity = entity;
	}

	@Override
	public void update()
	{
		if(this.entity.isDead || this.entity.charge <= 0) this.donePlaying = true;
		this.xPosF = (float) this.entity.posX;
		this.yPosF = (float) this.entity.posY;
		this.zPosF = (float) this.entity.posZ;
	}
}