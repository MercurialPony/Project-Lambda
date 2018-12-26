package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.common.sound.moving.MovingSoundCharger;
import melonslise.lambda.common.sound.moving.MovingSoundGluonBeam;
import melonslise.lambda.common.sound.moving.MovingSoundGluonCharge;
import melonslise.lambda.common.sound.moving.MovingSoundTauCannon;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSound implements IMessage
{
	private int id;
	private ESound sound;

	public MessageSound() {}

	public MessageSound(int id, ESound sound)
	{
		this.id = id;
		this.sound = sound;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = buffer.readInt();
		this.sound = LambdaUtilities.readEnum(buffer, ESound.class);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.id);
		LambdaUtilities.writeEnum(buffer, this.sound);
	}



	public static class Handler implements IMessageHandler<MessageSound, IMessage>
	{
		@Override
		public IMessage onMessage(MessageSound message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = mc.world.getEntityByID(message.id);
					if(!(entity instanceof EntityLivingBase)) return;
					EntityLivingBase living = (EntityLivingBase) entity;
					ISound sound = null;
					switch(message.sound)
					{
					case TAU_CHARGE: sound = new MovingSoundTauCannon(living, LambdaSounds.weapon_tau_charge, SoundCategory.PLAYERS); break;
					case GLUON_CHARGE: sound = new MovingSoundGluonCharge(living, LambdaSounds.weapon_gluon_charge, SoundCategory.PLAYERS); break;
					case GLUON_BEAM: sound = new MovingSoundGluonBeam(living, LambdaSounds.weapon_gluon_beam, SoundCategory.PLAYERS); break;
					case CHARGER_HEALTH_USE: sound = new MovingSoundCharger(living, LambdaSounds.item_medcharge, SoundCategory.BLOCKS, false); break;
					case CHARGER_HEALTH_EMPTY: sound = new MovingSoundCharger(living, LambdaSounds.item_medshotno, SoundCategory.BLOCKS, true); break;
					case CHARGER_POWER_USE: sound = new MovingSoundCharger(living, LambdaSounds.item_suitcharge, SoundCategory.BLOCKS, false); break;
					case CHARGER_POWER_EMPTY: sound = new MovingSoundCharger(living, LambdaSounds.item_suitchargeno, SoundCategory.BLOCKS, true); break;
					default: break;
					}
					mc.getSoundHandler().playSound(sound);
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}



	public static enum ESound
	{
		TAU_CHARGE,
		GLUON_CHARGE,
		GLUON_BEAM,
		CHARGER_HEALTH_USE,
		CHARGER_HEALTH_EMPTY,
		CHARGER_POWER_USE,
		CHARGER_POWER_EMPTY;
	}
}