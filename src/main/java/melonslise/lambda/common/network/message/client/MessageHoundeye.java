package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.entity.alien.EntityHoundeye;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.common.sound.moving.MovingSoundHoundeye;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHoundeye implements IMessage
{
	private int id, value;
	private EHoundeyeAction action;

	public MessageHoundeye() {}

	public MessageHoundeye(EntityHoundeye entity, EHoundeyeAction action, int value)
	{
		this.id = entity.getEntityId();
		this.action = action;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = buffer.readInt();
		this.action = LambdaUtilities.readEnum(buffer, EHoundeyeAction.class);
		this.value = (int) buffer.readShort();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.id);
		LambdaUtilities.writeEnum(buffer, this.action);
		buffer.writeShort((short) this.value);
	}



	public static class Handler implements IMessageHandler<MessageHoundeye, IMessage>
	{
		@Override
		public IMessage onMessage(MessageHoundeye message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = mc.world.getEntityByID(message.id);
					if(!(entity instanceof EntityHoundeye)) return;
					EntityHoundeye houndeye = (EntityHoundeye) entity;
					switch(message.action)
					{
					case START_CHARGE: houndeye.charge = message.value; mc.getSoundHandler().playSound(new MovingSoundHoundeye(houndeye, LambdaSounds.alien_houndeye_attack, houndeye.getSoundCategory())); break;
					case CHARGE: houndeye.charge = message.value; break;
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
	public static enum EHoundeyeAction
	{
		START_CHARGE,
		CHARGE,
	}
}