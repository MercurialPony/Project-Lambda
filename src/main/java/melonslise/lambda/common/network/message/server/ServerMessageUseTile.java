package melonslise.lambda.common.network.message.server;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.common.block.api.ITileUsable;
import melonslise.lambda.common.capability.entity.ICapabilityUsingTile;
import melonslise.lambda.common.network.message.api.AMessageUse;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerMessageUseTile extends AMessageUse
{
	private BlockPos position;
	private EnumFacing side;
	private Vec3d hit;

	public ServerMessageUseTile() {}

	public ServerMessageUseTile(boolean state, int type, BlockPos position, EnumFacing side, Vec3d hit)
	{
		super(state, type);
		this.position = position;
		this.side = side;
		this.hit = hit;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		super.fromBytes(buffer);
		this.position = new BlockPos(LambdaUtilities.readIntegerVector(buffer));
		this.side = LambdaUtilities.readEnum(buffer, EnumFacing.class);
		this.hit = LambdaUtilities.readFloatVector(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		super.toBytes(buffer);
		LambdaUtilities.writeIntegerVector(buffer, this.position);
		LambdaUtilities.writeEnum(buffer, this.side);
		LambdaUtilities.writeFloatVector(buffer, this.hit);
	}

	public BlockPos getPosition()
	{
		return this.position;
	}

	public EnumFacing getSide()
	{
		return this.side;
	}

	public Vec3d getHit()
	{
		return this.hit;
	}



	public static class Handler implements IMessageHandler<ServerMessageUseTile, IMessage>
	{
		@Override
		public IMessage onMessage(ServerMessageUseTile message, MessageContext context)
		{
			EntityPlayerMP player = context.getServerHandler().player;
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					if(player.world.isBlockLoaded(message.position) && player.world.getBlockState(message.position).getBlock() instanceof ITileUsable)
					{
						ICapabilityUsingTile using = LambdaUtilities.getUsingTile(player);
						if(message.getState()) using.startUsing(message.position, message.side, message.hit, message.getType());
						else using.stopUsing(message.side, message.hit, message.getType());
					}
				}
			};
			player.getServerWorld().addScheduledTask(action);
			return null;
		}
	}
}