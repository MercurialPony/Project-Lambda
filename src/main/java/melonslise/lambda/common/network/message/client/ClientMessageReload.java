package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.capability.entity.ICapabilityReloading;
import melonslise.lambda.common.network.message.server.ServerMessageReload;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientMessageReload extends ServerMessageReload
{
	private int id;
	private boolean state;

	public ClientMessageReload() {}

	public ClientMessageReload(Entity entity, boolean state, EnumHand hand)
	{
		super(hand);
		this.id = entity.getEntityId();
		this.state = state;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.id = buffer.readInt();
		this.state = buffer.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		buffer.writeInt(this.id);
		buffer.writeBoolean(this.state);
	}

	public int getEntityID()
	{
		return this.id;
	}

	public Entity getEntity(World world)
	{
		return world.getEntityByID(this.id);
	}

	public boolean getState()
	{
		return this.state;
	}



	public static class Handler implements IMessageHandler<ClientMessageReload, IMessage>
	{
		@Override
		public IMessage onMessage(ClientMessageReload message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = message.getEntity(mc.world);
					if(entity != null)
					{
						ICapabilityReloading reloading = LambdaUtilities.getReloading(entity);
						if(message.getState()) reloading.startReloading(message.getHand());
						else reloading.stopReloading(message.getHand());
					}
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}
}