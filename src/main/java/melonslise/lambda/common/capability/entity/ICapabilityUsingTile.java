package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.capability.api.ICapability;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface ICapabilityUsingTile extends ICapability
{
	public boolean get();

	public BlockPos getPosition();

	public IBlockState getFocus();

	public int getTicks();

	public int getType();

	public void startUsing(BlockPos position, EnumFacing side, Vec3d hit, int type);

	public void updateUsing();

	public void stopUsing(EnumFacing side, Vec3d hit, int type);
}