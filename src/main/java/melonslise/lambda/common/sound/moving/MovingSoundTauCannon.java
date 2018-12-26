package melonslise.lambda.common.sound.moving;

import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class MovingSoundTauCannon extends MovingSound
{
	protected EntityLivingBase entity;

	public MovingSoundTauCannon(EntityLivingBase entity, SoundEvent sound, SoundCategory category)
	{
		super( sound, category);
		this.entity = entity;
		this.repeat = true;
		this.repeatDelay = 0;
	}

	// TODO Don't increase pitch if no ammo?
	@Override
	public void update()
	{
		ICapabilityUsingItem using = LambdaUtilities.getUsingItem(this.entity);
		if(this.entity.isDead || !using.get() || using.getType() != 1 || using.getStack().getItem() != LambdaItems.weapon_tau) this.donePlaying = true;
		this.xPosF = (float) this.entity.posX;
		this.yPosF = (float) this.entity.posY;
		this.zPosF = (float) this.entity.posZ;
		this.pitch = 1.1F + MathHelper.clamp((float) using.getTicks() / 80F, 0F, 1F);
	}
}