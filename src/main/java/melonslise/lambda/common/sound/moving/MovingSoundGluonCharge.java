package melonslise.lambda.common.sound.moving;

import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundGluonCharge extends MovingSound
{
	protected EntityLivingBase entity;

	public MovingSoundGluonCharge(EntityLivingBase entity, SoundEvent sound, SoundCategory category)
	{
		super(sound, category);
		this.entity = entity;
	}

	@Override
	public void update()
	{
		ICapabilityUsingItem using = LambdaUtilities.getUsingItem(this.entity);
		if(this.entity.isDead || !using.get() || using.getStack().getItem() != LambdaItems.weapon_gluon) this.donePlaying = true;
		this.xPosF = (float) this.entity.posX;
		this.yPosF = (float) this.entity.posY;
		this.zPosF = (float) this.entity.posZ;
	}
}