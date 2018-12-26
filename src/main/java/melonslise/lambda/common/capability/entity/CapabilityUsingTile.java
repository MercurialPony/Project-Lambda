package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.block.api.ITileUsable;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.ClientMessageUseTile;
import melonslise.lambda.common.network.message.server.ServerMessageUseTile;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class CapabilityUsingTile implements ICapabilityUsingTile
{
	private static final ResourceLocation id = LambdaUtilities.createLambdaDomain("using.tile");

	private EntityPlayer player;
	private boolean state;
	private BlockPos position;
	private IBlockState focus;
	private int ticks, type;
	// Isn't updated every tick on the server
	private EnumFacing side;
	private Vec3d hit;

	public CapabilityUsingTile(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public ResourceLocation getID()
	{
		return id;
	}

	@Override
	public boolean get()
	{
		return this.state;
	}

	@Override
	public BlockPos getPosition()
	{
		return this.position;
	}

	@Override
	public IBlockState getFocus()
	{
		return this.focus;
	}

	@Override
	public int getTicks()
	{
		return this.ticks;
	}

	@Override
	public int getType()
	{
		return this.type;
	}

	@Override
	public void startUsing(BlockPos position, EnumFacing side, Vec3d hit, int type)
	{
		IBlockState focus = this.player.world.getBlockState(position);
		if(!this.state && focus.getBlock() instanceof ITileUsable)
		{
			this.synchronize(true, position, side, hit, type);
			if(((ITileUsable) focus.getBlock()).onStartUsing(this.player, position, focus, side, hit, type))
			{
				this.state = true;
				this.position = position;
				this.focus = focus;
				this.side = side;
				this.hit = hit;
				this.ticks = 0;
				this.type = type;
			}
		}
	}

	// TODO Fix ray tracing
	// TODO Ray trace entities too
	@Override
	public void updateUsing()
	{
		if(this.state)
		{
			if(this.player.world.isRemote)
			{
				RayTraceResult result = LambdaUtilities.rayTraceBlocks(this.player, 2D, false, true);
				BlockPos position = result.getBlockPos();
				// TODO Check client behavior
				if(result != null && position.equals(this.position) && ((ITileUsable) this.focus.getBlock()).continueUsing(this.player, this.position, this.focus, this.player.world.getBlockState(this.position)))
				{
					// TODO this.focus = this.player.world.getBlockState(this.position); ?
					++this.ticks;
					((ITileUsable) this.focus.getBlock()).onUpdateUsing(this.player, this.position, this.focus, this.ticks, this.type);
					this.side = result.sideHit;
					this.hit = result.hitVec.subtract((double) position.getX(), (double) position.getY(), (double) position.getZ());
				}
				else this.stopUsing(this.side, this.hit, this.type);
			}
			else // if(((IUsableTile) this.focus.getBlock()).continueUsing(this.player, this.position, this.focus, this.player.world.getBlockState(this.position)))
			{
				++this.ticks;
				((ITileUsable) this.focus.getBlock()).onUpdateUsing(this.player, this.position, this.focus, this.ticks, this.type);
			}
		}
	}

	@Override
	public void stopUsing(EnumFacing side, Vec3d hit, int type)
	{
		if(this.state && this.type == type)
		{
			this.synchronize(false, this.position, side, hit, type);
			((ITileUsable) this.focus.getBlock()).onStopUsing(this.player, this.position, this.focus, side, hit, this.ticks, this.type);
			this.state = false;
			this.side = side;
			this.hit = hit;
			//this.position = null;
			//this.focus = null;
			//this.ticks = 0;
		}
	}

	@Override
	public void synchronize()
	{
		this.synchronize(this.state, this.position, this.side, this.hit, this.type);
	}

	protected void synchronize(boolean state, BlockPos position, EnumFacing side, Vec3d hit, int type)
	{
		if(position != null && side != null && hit != null)
		{
			if(!this.player.world.isRemote) LambdaNetworks.network.sendToAllTracking(new ClientMessageUseTile(this.player, state, type, position, side, hit), this.player);
			else if(this.player == Minecraft.getMinecraft().player) LambdaNetworks.network.sendToServer(new ServerMessageUseTile(state, type, position, side, hit));
		}
	}

	@Override
	public NBTTagCompound serialize(NBTTagCompound nbt, EnumFacing side)
	{
		return nbt;
	}

	@Override
	public void deserialize(NBTBase nbt) {}
}