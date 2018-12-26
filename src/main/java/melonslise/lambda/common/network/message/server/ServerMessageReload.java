package melonslise.lambda.common.network.message.server;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TODO send state too to stop?
public class ServerMessageReload implements IMessage
{
	private EnumHand hand;

	public ServerMessageReload() {}

	public ServerMessageReload(EnumHand hand)
	{
		this.hand = hand;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.hand = LambdaUtilities.readEnum(buffer, EnumHand.class);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		LambdaUtilities.writeEnum(buffer, this.hand);
	}

	public EnumHand getHand()
	{
		return this.hand;
	}



	public static class Handler implements IMessageHandler<ServerMessageReload, IMessage>
	{
		@Override
		public IMessage onMessage(ServerMessageReload message, MessageContext context)
		{
			EntityPlayerMP player = context.getServerHandler().player;
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					LambdaUtilities.getReloading(player).startReloading(message.hand);
				}
			};
			player.getServerWorld().addScheduledTask(action);
			return null;
		}
	}
}