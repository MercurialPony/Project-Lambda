package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.entity.alien.EntityVortigaunt;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.common.sound.moving.MovingSoundVortigaunt;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageVortigaunt implements IMessage
{
	private int id, value;
	private EVortigauntAction action;

	public MessageVortigaunt() {}

	public MessageVortigaunt(EntityVortigaunt entity, EVortigauntAction action, int value)
	{
		this.id = entity.getEntityId();
		this.action = action;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = buffer.readInt();
		this.action = LambdaUtilities.readEnum(buffer, EVortigauntAction.class);
		this.value = (int) buffer.readShort();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.id);
		LambdaUtilities.writeEnum(buffer, this.action);
		buffer.writeShort((short) this.value);
	}



	public static class Handler implements IMessageHandler<MessageVortigaunt, IMessage>
	{
		@Override
		public IMessage onMessage(MessageVortigaunt message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = mc.world.getEntityByID(message.id);
					if(!(entity instanceof EntityVortigaunt)) return;
					EntityVortigaunt vortigaunt = (EntityVortigaunt) entity;
					switch(message.action)
					{
					case CLAW: vortigaunt.claw = message.value; break;
					case START_CHARGE: vortigaunt.charge = message.value; mc.getSoundHandler().playSound(new MovingSoundVortigaunt(vortigaunt, LambdaSounds.zap, vortigaunt.getSoundCategory())); break;
					case CHARGE: vortigaunt.charge = message.value; break;
					default: break;
					}
				}
			};
			mc.addScheduledTask(runnable);
			return null;
		}
	}



	// TODO Rename?
	// TODO Move to entity?
	public static enum EVortigauntAction
	{
		CLAW,
		START_CHARGE,
		CHARGE
	}
}