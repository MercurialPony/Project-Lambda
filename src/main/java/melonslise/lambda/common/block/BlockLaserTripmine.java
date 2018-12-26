package melonslise.lambda.common.block;

import javax.annotation.Nullable;

import melonslise.lambda.common.block.api.LambdaBlock;
import melonslise.lambda.common.sound.LambdaSoundTypes;
import melonslise.lambda.common.tileentity.TileEntityLaserTripmine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// TODO Base class?
// TODO Shooting this expldoes it
// TODO More attachable blocks for tripmine (glass, etc)
public class BlockLaserTripmine extends LambdaBlock
{
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	protected static final AxisAlignedBB BOUNDS_NORTH = new AxisAlignedBB(0.21875D, 0.3125D, 1D, 0.78125D, 0.625D, 0.6875D);
	protected static final AxisAlignedBB BOUNDS_EAST = new AxisAlignedBB(0D, 0.3125D, 0.21875D, 0.3125D, 0.625D, 0.78125D);
	protected static final AxisAlignedBB BOUNDS_SOUTH = new AxisAlignedBB(0.21875D, 0.3125D, 0D, 0.78125D, 0.625D, 0.3125D);
	protected static final AxisAlignedBB BOUNDS_WEST = new AxisAlignedBB(1D, 0.3125D, 0.21875D, 0.6875D, 0.625D, 0.78125D);
	protected static final AxisAlignedBB BOUNDS_UP = new AxisAlignedBB(0.21875D, 0D, 0.3125D, 0.78125D, 0.3125D, 0.625D);
	protected static final AxisAlignedBB BOUNDS_DOWN = new AxisAlignedBB(0.21875D, 1D, 0.6875D, 0.78125D, 0.6875D, 0.375D);

	public BlockLaserTripmine(String name, Material material)
	{
		super(name, material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.FACING, EnumFacing.NORTH));
		this.setSoundType(LambdaSoundTypes.TRIPMINE);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos position, Block block, BlockPos position1)
	{
		if (this.checkForDrop(world, position, state) && !canPlaceBlock(world, position, (EnumFacing) state.getValue(FACING)))
		{
			world.setBlockToAir(position);
		}
	}

	private boolean checkForDrop(World world, BlockPos position, IBlockState state)
	{
		if (this.canPlaceBlockAt(world, position)) return true;
		else
		{
			world.setBlockToAir(position);
			return false;
		}
	}

	@Override
	public void breakBlock(World world, BlockPos position, IBlockState state)
	{
		this.explode(world, position);
	}

	// TODO Instance check?
	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		this.getTile(world, position).setPlacer(placer);
	}

	// TODO Strength
	// TODO Mob griefing
	public void explode(World world, BlockPos position)
	{
		if(!world.isRemote) world.createExplosion(this.getTile(world, position).getPlacer(), (double) position.getX(), position.getY(), position.getZ(), 4F, true);
	}

	// TODO Visibility, static
	protected TileEntityLaserTripmine getTile(World world, BlockPos position)
	{
		return ((TileEntityLaserTripmine) world.getTileEntity(position));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos position)
	{
		switch(state.getValue(FACING))
		{
		case NORTH: return BOUNDS_NORTH;
		case EAST: return BOUNDS_EAST;
		case SOUTH: return BOUNDS_SOUTH;
		case WEST: return BOUNDS_WEST;
		case UP: return BOUNDS_UP;
		case DOWN: return BOUNDS_DOWN;
		default: return FULL_BLOCK_AABB;
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos position, EnumFacing side)
	{
		return canPlaceBlock(world, position, side);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos position)
	{
		for (EnumFacing facing : EnumFacing.values()) if (canPlaceBlock(world, position, facing)) return true;
		return false;
	}

	protected static boolean canPlaceBlock(World world, BlockPos position, EnumFacing direction)
	{
		BlockPos position1 = position.offset(direction.getOpposite());
		IBlockState state = world.getBlockState(position1);
		boolean flag = state.getBlockFaceShape(world, position1, direction) == BlockFaceShape.SOLID;
		Block block = state.getBlock();
		if (direction == EnumFacing.UP) return state.isTopSolid() || !isExceptionBlockForAttaching(block) && flag;
		else return !isExceptBlockForAttachWithPiston(block) && flag;
	}

	// TODO Understand and rewrite?
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase entity)
	{
		return canPlaceBlock(world, position, facing) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	// TODO Correct?
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 0b111));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | state.getValue(FACING).getIndex();
		return i;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityLaserTripmine();
	}
}