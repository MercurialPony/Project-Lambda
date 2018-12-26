package melonslise.lambda.common.tileentity.api.charger;

import melonslise.lambda.common.block.api.ITileUsable;
import melonslise.lambda.common.block.api.charger.ABlockCharger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// TODO Base class for the nbt shit
public abstract class ATileEntityCharger extends TileEntity implements ITileUsable
{
	private float charge;

	public ATileEntityCharger()
	{
		this(14F);
	}

	public ATileEntityCharger(float charge)
	{
		this.charge = charge;
	}

	public float getCharge()
	{
		return this.charge;
	}

	public void setCharge(float charge)
	{
		float old = this.charge;
		if(charge < 0) this.charge = 0;
		else this.charge = charge;
		if(old != this.charge)
		{
			if(old > 0 && this.charge <= 0 || old <= 0 && this.charge > 0) this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(ABlockCharger.EMPTY, this.charge <= 0));
			this.markDirty();
		}
	}

	@Override
	public boolean onStartUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int type)
	{
		return false;
	}

	@Override
	public void onUpdateUsing(EntityPlayer player, BlockPos position, IBlockState state, int ticks, int type) {}

	@Override
	public void onStopUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int ticks, int type) {}

	@Override
	public boolean continueUsing(EntityPlayer player, BlockPos position, IBlockState oldState, IBlockState newState)
	{
		return !player.world.isRemote || oldState.getBlock() == newState.getBlock();
	}

	private static final String keyCharge = "charge";

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.charge = nbt.getFloat(keyCharge);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat(keyCharge, this.charge);
		return nbt;
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbt = super.getUpdateTag();
		nbt.setFloat(keyCharge, this.charge);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound nbt)
	{
		super.handleUpdateTag(nbt);
		this.charge = nbt.getFloat(keyCharge);
	}

	// TODO Update packets required?

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return super.getUpdatePacket();
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos position, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}