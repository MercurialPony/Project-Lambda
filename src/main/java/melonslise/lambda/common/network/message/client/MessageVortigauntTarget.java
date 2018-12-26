package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.entity.alien.EntityVortigaunt;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageVortigauntTarget implements IMessage
{
	private int id;
	private Vec3d target;

	public MessageVortigauntTarget() {}

	public MessageVortigauntTarget(EntityVortigaunt entity, Vec3d target)
	{
		this.id = entity.getEntityId();
		this.target = target;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = buffer.readInt();
		this.target = LambdaUtilities.readFloatVector(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.id);
		LambdaUtilities.writeFloatVector(buffer, this.target);
	}



	public static class Handler implements IMessageHandler<MessageVortigauntTarget, IMessage>
	{
		@Override
		public IMessage onMessage(MessageVortigauntTarget message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = mc.world.getEntityByID(message.id);
					if(!(entity instanceof EntityVortigaunt)) return;
					((EntityVortigaunt) entity).target = message.target;
				}
			};
			mc.addScheduledTask(runnable);
			return null;
		}
	}
}