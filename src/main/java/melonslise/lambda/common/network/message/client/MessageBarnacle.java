package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.entity.alien.EntityBarnacle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBarnacle implements IMessage
{
	private int entityID;
	private double tongueLength;

	public MessageBarnacle() {}

	public MessageBarnacle(EntityBarnacle entity, double tongueLength)
	{
		this.entityID = entity.getEntityId();
		this.tongueLength = tongueLength;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.entityID = buffer.readInt();
		this.tongueLength = (double) buffer.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.entityID);
		buffer.writeFloat((float) this.tongueLength);
	}



	public static class Handler implements IMessageHandler<MessageBarnacle, IMessage>
	{
		@Override
		public IMessage onMessage(MessageBarnacle message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = mc.world.getEntityByID(message.entityID);
					if(!(entity instanceof EntityBarnacle)) return;
					((EntityBarnacle) entity).tongueLength = message.tongueLength;
				}
			};
			mc.addScheduledTask(runnable);
			return null;
		}
	}
}