package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.item.api.IItemReloadable;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.ClientMessageReload;
import melonslise.lambda.common.network.message.server.ServerMessageReload;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class CapabilityReloading implements ICapabilityReloading
{
	private static final ResourceLocation id = LambdaUtilities.createLambdaDomain("reloading");

	private EntityPlayer player;
	private boolean state;
	private EnumHand hand = EnumHand.MAIN_HAND;
	private ItemStack stack = ItemStack.EMPTY;
	private int ticks;

	public CapabilityReloading(EntityPlayer player)
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
	public EnumHand getHand()
	{
		return this.hand;
	}

	@Override
	public ItemStack getStack()
	{
		return this.stack;
	}

	@Override
	public int getTicks()
	{
		return this.ticks;
	}

	@Override
	public void startReloading(EnumHand hand)
	{
		ItemStack stack = this.player.getHeldItem(hand);
		if(!this.state && stack.getItem() instanceof IItemReloadable)
		{
			this.synchronize(true, hand);
			IItemReloadable reloadable = (IItemReloadable) stack.getItem();
			int ticks = reloadable.onStartReloading(this.player, hand, stack);
			if(ticks > 0)
			{
				this.state = true;
				this.hand = hand;
				this.stack = stack;
				this.ticks = ticks;
			}
		}
	}

	// TODO Find a way to compare stacks on client too?
	@Override
	public void updateReloading()
	{
		if(this.state)
		{
			ItemStack stack = this.player.getHeldItem(this.hand);
			if(this.ticks > 0 && !this.stack.isEmpty() && stack.getItem() instanceof IItemReloadable && ((IItemReloadable) this.stack.getItem()).continueReloading(this.player, this.stack, stack))
			{
				//this.stack = stack;
				--this.ticks;
				((IItemReloadable) this.stack.getItem()).onUpdateReloading(this.player, this.hand, this.stack, this.ticks);
			}
			else this.stopReloading(this.hand);
		}
	}

	@Override
	public void stopReloading(EnumHand hand)
	{
		if(this.state && this.hand == hand)
		{
			this.synchronize(false, hand);
			if(!this.stack.isEmpty()) ((IItemReloadable) this.stack.getItem()).onStopReloading(this.player, this.hand, this.stack, this.ticks);
			this.state = false;
			//this.stack = ItemStack.EMPTY;
			//this.ticks = 0;
		}
	}

	@Override
	public void synchronize()
	{
		this.synchronize(this.state, this.hand);
	}

	protected void synchronize(boolean state, EnumHand hand)
	{
		if(!this.player.world.isRemote) LambdaNetworks.network.sendToAllTracking(new ClientMessageReload(this.player, state, hand), this.player);
		else if(this.player == Minecraft.getMinecraft().player) LambdaNetworks.network.sendToServer(new ServerMessageReload(hand));
	}

	@Override
	public NBTTagCompound serialize(NBTTagCompound nbt, EnumFacing side)
	{
		return nbt;
	}

	@Override
	public void deserialize(NBTBase nbt) {}
}