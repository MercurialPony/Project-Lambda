package melonslise.lambda.common.block.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface ITileUsable
{
	public boolean onStartUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int type);

	public void onUpdateUsing(EntityPlayer player, BlockPos position, IBlockState state, int ticks, int type);

	public void onStopUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int ticks, int type);

	public default boolean continueUsing(EntityPlayer player, BlockPos position, IBlockState oldState, IBlockState newState)
	{
		return !player.world.isRemote || oldState == newState;
	}
}