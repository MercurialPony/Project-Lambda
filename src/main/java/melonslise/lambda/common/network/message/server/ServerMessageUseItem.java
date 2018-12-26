package melonslise.lambda.common.network.message.server;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.network.message.api.AMessageUse;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerMessageUseItem extends AMessageUse
{
	private EnumHand hand;

	public ServerMessageUseItem() {}

	public ServerMessageUseItem(boolean state, int type, EnumHand hand)
	{
		super(state, type);
		this.hand = hand;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.hand = LambdaUtilities.readEnum(buffer, EnumHand.class);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		LambdaUtilities.writeEnum(buffer, this.hand);
	}

	public EnumHand getHand()
	{
		return this.hand;
	}



	public static class Handler implements IMessageHandler<ServerMessageUseItem, IMessage>
	{
		@Override
		public IMessage onMessage(ServerMessageUseItem message, MessageContext context)
		{
			EntityPlayerMP player = context.getServerHandler().player;
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					ICapabilityUsingItem using = LambdaUtilities.getUsingItem(player);
					if(message.getState()) using.startUsing(message.hand, message.getType());
					else using.stopUsing(message.hand, message.getType());
				}
			};
			player.getServerWorld().addScheduledTask(action);
			return null;
		}
	}
}