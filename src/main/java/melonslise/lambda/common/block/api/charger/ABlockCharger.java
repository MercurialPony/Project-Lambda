package melonslise.lambda.common.block.api.charger;

import javax.annotation.Nullable;

import melonslise.lambda.common.block.api.ITileUsable;
import melonslise.lambda.common.block.api.LambdaBlock;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// TODO BlockUsable base
public abstract class ABlockCharger extends LambdaBlock implements ITileUsable
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool EMPTY = PropertyBool.create("empty");
	protected static final AxisAlignedBB BOUNDS_NORTH = new AxisAlignedBB(0.125D, 0D, 0.875D, 0.875D, 1D, 1D);
	protected static final AxisAlignedBB BOUNDS_EAST = new AxisAlignedBB(0D, 0D, 0.125D, 0.125D, 1D, 0.875D);
	protected static final AxisAlignedBB BOUNDS_SOUTH = new AxisAlignedBB(0.125D, 0D, 0D, 0.875D, 1D, 0.125D);
	protected static final AxisAlignedBB BOUNDS_WEST = new AxisAlignedBB(0.875D, 0D, 0.125D, 1D, 1D, 0.875D);

	public ABlockCharger(String name, Material material)
	{
		super(name, material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.FACING, EnumFacing.NORTH).withProperty(EMPTY, false));
	}

	@Override
	public boolean onStartUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int type)
	{
		TileEntity tile = player.world.getTileEntity(position);
		if(tile instanceof ITileUsable) return ((ITileUsable) tile).onStartUsing(player, position, state, side, hit, type);
		return false;
	}

	@Override
	public void onUpdateUsing(EntityPlayer player, BlockPos position, IBlockState state, int ticks, int type)
	{
		TileEntity tile = player.world.getTileEntity(position);
		if(tile instanceof ITileUsable) ((ITileUsable) tile).onUpdateUsing(player, position, state, ticks, type);
	}

	@Override
	public void onStopUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int ticks, int type)
	{
		TileEntity tile = player.world.getTileEntity(position);
		if(tile instanceof ITileUsable) ((ITileUsable) tile).onStopUsing(player, position, state, side, hit, ticks, type);
	}

	@Override
	public boolean continueUsing(EntityPlayer player, BlockPos position, IBlockState oldState, IBlockState newState)
	{
		TileEntity tile = player.world.getTileEntity(position);
		if(tile instanceof ITileUsable) return ((ITileUsable) tile).continueUsing(player, position, oldState, newState);
		return ITileUsable.super.continueUsing(player, position, oldState, newState);
	}

	// TODO Fix the bbs
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos position)
	{
		switch(state.getValue(FACING))
		{
		case NORTH: return BOUNDS_SOUTH;
		case EAST: return BOUNDS_WEST;
		case SOUTH: return BOUNDS_NORTH;
		case WEST: return BOUNDS_EAST;
		default: return FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos position, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING, EMPTY});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011)).withProperty(EMPTY, (meta & 0b0100) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		i = i | (state.getValue(EMPTY) ? 1 : 0) << 2;
		return i;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos position, EnumFacing side)
	{
		return this.canAttachTo(world, position.west(), side) || this.canAttachTo(world, position.east(), side) || this.canAttachTo(world, position.north(), side) || this.canAttachTo(world, position.south(), side);
	}

	private boolean canAttachTo(World world, BlockPos position, EnumFacing facing)
	{
		IBlockState state = world.getBlockState(position);
		return !isExceptBlockForAttachWithPiston(state.getBlock()) && state.getBlockFaceShape(world, position, facing) == BlockFaceShape.SOLID && !state.canProvidePower();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if(facing.getAxis().isHorizontal() && this.canAttachTo(world, position.offset(facing.getOpposite()), facing)) return this.getDefaultState().withProperty(FACING, facing.getOpposite());
		else for(EnumFacing facing1 : EnumFacing.Plane.HORIZONTAL) if(this.canAttachTo(world, position.offset(facing1.getOpposite()), facing1)) return this.getDefaultState().withProperty(FACING, facing1.getOpposite());
		return this.getDefaultState();
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);
}