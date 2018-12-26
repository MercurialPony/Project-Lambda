package melonslise.lambda.common.network.message.server;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.network.message.api.AMessageUse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TODO To client version of this packet?
public class ServerMessageUseEntity extends AMessageUse
{
	private int id;

	public ServerMessageUseEntity() {}

	public ServerMessageUseEntity(boolean state, int type, int id)
	{
		super(state, type);
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.id = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeInt(this.id);
	}



	public static class Handler implements IMessageHandler<ServerMessageUseEntity, IMessage>
	{
		@Override
		public IMessage onMessage(ServerMessageUseEntity message, MessageContext context)
		{
			EntityPlayerMP player = context.getServerHandler().player;
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					if(message.getState())
					{
						Entity target = player.world.getEntityByID(message.id);
						if(target != null) target.processInitialInteract(player, EnumHand.MAIN_HAND);
					}
				}
			};
			player.getServerWorld().addScheduledTask(action);
			return null;
		}
	}
}