package melonslise.lambda.common.sound.moving;

import melonslise.lambda.common.capability.entity.ICapabilityUsingTile;
import melonslise.lambda.common.tileentity.api.charger.ATileEntityCharger;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundCharger extends MovingSound
{
	protected EntityLivingBase entity;
	protected boolean empty;

	public MovingSoundCharger(EntityLivingBase entity, SoundEvent sound, SoundCategory category, boolean empty)
	{
		super(sound, category);
		this.entity = entity;
		this.repeat = true;
		this.repeatDelay = 0;
		this.empty = empty;
	}

	@Override
	public void update()
	{
		ICapabilityUsingTile using = LambdaUtilities.getUsingTile(this.entity);
		TileEntity tile = this.entity.world.getTileEntity(using.getPosition());
		if(this.entity.isDead || !using.get() || !(tile instanceof ATileEntityCharger))
		{
			this.donePlaying = true;
			return;
		}
		ATileEntityCharger charger = (ATileEntityCharger) tile;
		if(this.empty && charger.getCharge() > 0F || !empty && charger.getCharge() <= 0F) this.donePlaying = true;
		this.xPosF = (float) this.entity.posX;
		this.yPosF = (float) this.entity.posY;
		this.zPosF = (float) this.entity.posZ;
	}
}