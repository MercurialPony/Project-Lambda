package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.network.message.server.ServerMessageUseItem;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientMessageUseItem extends ServerMessageUseItem
{
	private int id;

	public ClientMessageUseItem() {}

	public ClientMessageUseItem(Entity entity, boolean state, int type, EnumHand hand)
	{
		super(state, type, hand);
		this.id = entity.getEntityId();
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

	public int getEntityID()
	{
		return this.id;
	}

	public Entity getEntity(World world)
	{
		return world.getEntityByID(this.id);
	}



	public static class Handler implements IMessageHandler<ClientMessageUseItem, IMessage>
	{
		@Override
		public IMessage onMessage(ClientMessageUseItem message, MessageContext context)
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
						ICapabilityUsingItem using = LambdaUtilities.getUsingItem(entity);
						if(message.getState()) using.startUsing(message.getHand(), message.getType());
						else using.stopUsing(message.getHand(), message.getType());
					}
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}
}