package melonslise.lambda.common.tileentity;

import melonslise.lambda.common.block.BlockLaserTripmine;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;

// TODO Base class for the nbt shit
// TODO Stop reusing same read/write nbt code in  multiple methods
// TODO Get facing helper
// TODO Charge ticks static final
public class TileEntityLaserTripmine extends TileEntity implements ITickable
{
	protected EntityLivingBase placer;
	protected Vec3d end, start;
	protected int ticks = 60;

	// TODO Not every tick check
	// TODO Fix not triggering on entity items, snowballs, etc
	@Override
	public void update()
	{
		if(this.ticks == 0)
		{
			EnumFacing facing = this.world.getBlockState(this.pos).getValue(BlockLaserTripmine.FACING);
			Vec3d offset = new Vec3d(facing.getDirectionVec()).scale(0.5D);
			// TODO move start closer to tripmine
			this.start = new Vec3d(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D).subtract(offset);
			BlockPos end = LambdaUtilities.getFirstBlockInDirection(this.world, this.pos, facing, LambdaSelectors.SOLIDS, 24);
			// TODO Offset by -1D like with barnacle?
			this.end = new Vec3d(end.getX() + 0.5D, end.getY() + 0.5D, end.getZ() + 0.5D).subtract(offset);
			// TODO Grow?
			if(!this.world.getEntitiesWithinAABBExcludingEntity(null, LambdaUtilities.createAABB(this.start, this.end)).isEmpty()) this.world.setBlockToAir(this.pos);
		}
		else
		{
			// TODO Don't play sounds here?
			// TODO Methods for sounds
			if(this.ticks == 60) this.world.playSound(null, this.pos, LambdaSounds.weapon_tripmine_charge, SoundCategory.BLOCKS, 1F, 1F);
			else if(this.ticks == 1) this.world.playSound(null, this.pos, LambdaSounds.weapon_tripmine_activate, SoundCategory.BLOCKS, 1F, 1F);;
			--this.ticks;
			this.markDirty();
		}
	}

	public void setPlacer(EntityLivingBase placer)
	{
		EntityLivingBase old = this.placer;
		this.placer = placer;
		if(old != this.placer) this.markDirty();
	}

	public EntityLivingBase getPlacer()
	{
		return this.placer;
	}

	public int getChargeTicks()
	{
		return this.ticks;
	}

	// TODO Necessary?

	public Vec3d getLaserStart()
	{
		return this.start;
	}

	public Vec3d getLaserEnd()
	{
		return this.end;
	}

	// TODO Write ticks instead of bool?
	private static final String keyPlacer = "placer", keyActive = "active";

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey(keyPlacer)) this.placer = (EntityLivingBase) ((WorldServer) this.world).getEntityFromUuid(nbt.getUniqueId(keyPlacer));
		this.ticks = nbt.getBoolean(keyActive) ? 0 : 60;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(this.placer != null) nbt.setUniqueId(keyPlacer, this.placer.getUniqueID());
		nbt.setBoolean(keyActive, this.ticks == 0 ? true : false);
		return nbt;
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbt = super.getUpdateTag();
		nbt.setBoolean(keyActive, this.ticks == 0 ? true : false);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound nbt)
	{
		super.handleUpdateTag(nbt);
		this.ticks = nbt.getBoolean(keyActive) ? 0 : 60;
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
	public AxisAlignedBB getRenderBoundingBox()
	{
		return this.ticks == 0 && this.start != null && this.end != null ? new AxisAlignedBB(this.start.x, this.start.y, this.start.z, this.end.x, this.end.y, this.end.z) : super.getRenderBoundingBox();
	}
}