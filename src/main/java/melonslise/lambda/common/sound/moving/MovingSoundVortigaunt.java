package melonslise.lambda.common.sound.moving;

import melonslise.lambda.common.entity.alien.EntityVortigaunt;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundVortigaunt extends MovingSound
{
	protected EntityVortigaunt entity;

	public MovingSoundVortigaunt(EntityVortigaunt entity, SoundEvent sound, SoundCategory category)
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