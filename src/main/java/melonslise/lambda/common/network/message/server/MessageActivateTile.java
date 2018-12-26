package melonslise.lambda.common.network.message.server;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageActivateTile implements IMessage
{
	private BlockPos position;
	private EnumFacing side;
	private Vec3d hit;

	public MessageActivateTile() {}

	public MessageActivateTile(BlockPos position, EnumFacing side, Vec3d hit)
	{
		this.position = position;
		this.side = side;
		this.hit = hit;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.position = new BlockPos(LambdaUtilities.readIntegerVector(buffer));
		this.side = LambdaUtilities.readEnum(buffer, EnumFacing.class);
		this.hit = LambdaUtilities.readFloatVector(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
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



	public static class Handler implements IMessageHandler<MessageActivateTile, IMessage>
	{
		@Override
		public IMessage onMessage(MessageActivateTile message, MessageContext context)
		{
			/* TODO
			this.player.connection.sendPacket(new SPacketBlockChange(worldserver, blockpos));
			this.player.connection.sendPacket(new SPacketBlockChange(worldserver, blockpos.offset(enumfacing)));
			*/
			EntityPlayerMP player = context.getServerHandler().player;
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					if(player.world.isBlockLoaded(message.position))
					{
						IBlockState state = player.world.getBlockState(message.position);
						player.world.getBlockState(message.position).getBlock().onBlockActivated(player.world, message.position, state, player, EnumHand.MAIN_HAND, message.side, (float) message.hit.x, (float) message.hit.y, (float) message.hit.z);
					}
				}
			};
			player.getServerWorld().addScheduledTask(action);
			return null;
		}
	}
}