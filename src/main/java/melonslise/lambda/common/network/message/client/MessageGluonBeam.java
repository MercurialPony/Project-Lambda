package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.client.effect.EffectGluonBeam;
import melonslise.lambda.client.effect.EffectHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TODO Don't use double?
public class MessageGluonBeam implements IMessage
{
	private int source;

	public MessageGluonBeam() {}

	public MessageGluonBeam(int source)
	{
		this.source = source;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.source = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.source);
	}



	// TODO Null check
	public static class Handler implements IMessageHandler<MessageGluonBeam, IMessage>
	{
		@Override
		public IMessage onMessage(MessageGluonBeam message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					// TODO Range
					Entity entity = mc.world.getEntityByID(message.source);
					if(entity instanceof EntityLivingBase) EffectHandler.effects.add(new EffectGluonBeam((EntityLivingBase) entity, 64D));
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}
}