package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.entity.alien.EntityHeadcrabZombie;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHeadcrabZombie implements IMessage
{
	private int id, value;
	private EHeadcrabZombieAction action;

	public MessageHeadcrabZombie() {}

	public MessageHeadcrabZombie(EntityHeadcrabZombie entity, EHeadcrabZombieAction action, int value)
	{
		this.id = entity.getEntityId();
		this.action = action;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = buffer.readInt();
		this.action = LambdaUtilities.readEnum(buffer, EHeadcrabZombieAction.class);
		this.value = (int) buffer.readShort();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.id);
		LambdaUtilities.writeEnum(buffer, this.action);
		buffer.writeShort((short) this.value);
	}



	public static class Handler implements IMessageHandler<MessageHeadcrabZombie, IMessage>
	{
		@Override
		public IMessage onMessage(MessageHeadcrabZombie message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = mc.world.getEntityByID(message.id);
					if(!(entity instanceof EntityHeadcrabZombie)) return;
					EntityHeadcrabZombie zombie = (EntityHeadcrabZombie) entity;
					switch(message.action)
					{
					case START_SWING: zombie.swing = message.value; zombie.playSound(LambdaSounds.alien_zombie_attack, 1F, 1F); break;
					case SWING: zombie.swing = message.value; break;
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
	public static enum EHeadcrabZombieAction
	{
		START_SWING,
		SWING
	}
}