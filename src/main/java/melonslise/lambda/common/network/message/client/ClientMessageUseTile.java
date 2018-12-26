package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.block.api.ITileUsable;
import melonslise.lambda.common.capability.entity.ICapabilityUsingTile;
import melonslise.lambda.common.network.message.server.ServerMessageUseTile;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TODO Don't send hit?
public class ClientMessageUseTile extends ServerMessageUseTile
{
	private int id;

	public ClientMessageUseTile() {}

	public ClientMessageUseTile(Entity entity, boolean state, int type, BlockPos position, EnumFacing side, Vec3d hit)
	{
		super(state, type, position, side, hit);
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



	public static class Handler implements IMessageHandler<ClientMessageUseTile, IMessage>
	{
		@Override
		public IMessage onMessage(ClientMessageUseTile message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = message.getEntity(mc.world);
					if(entity != null && mc.world.isBlockLoaded(message.getPosition()) && mc.world.getBlockState(message.getPosition()).getBlock() instanceof ITileUsable)
					{
						ICapabilityUsingTile using = LambdaUtilities.getUsingTile(entity);
						if(message.getState()) using.startUsing(message.getPosition(), message.getSide(), message.getHit(), message.getType());
						else using.stopUsing(message.getSide(), message.getHit(), message.getType());
					}
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}
}