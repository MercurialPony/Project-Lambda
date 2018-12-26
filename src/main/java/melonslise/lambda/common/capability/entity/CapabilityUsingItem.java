package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.item.api.IItemUsable;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.ClientMessageUseItem;
import melonslise.lambda.common.network.message.server.ServerMessageUseItem;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class CapabilityUsingItem implements ICapabilityUsingItem
{
	private static final ResourceLocation id = LambdaUtilities.createLambdaDomain("using.item");

	private EntityPlayer player;
	private boolean state;
	private EnumHand hand = EnumHand.MAIN_HAND;
	private ItemStack stack = ItemStack.EMPTY;
	private int ticks, type;

	public CapabilityUsingItem(EntityPlayer player)
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
	public int getType()
	{
		return this.type;
	}

	@Override
	public void startUsing(EnumHand hand, int type)
	{
		ItemStack stack = this.player.getHeldItem(hand);
		if(!this.state && stack.getItem() instanceof IItemUsable)
		{
			this.synchronize(true, hand, type);
			if(((IItemUsable) stack.getItem()).onStartUsing(this.player, hand, stack, type))
			{
				this.state = true;
				this.hand = hand;
				this.stack = stack;
				this.ticks = 0;
				this.type = type;
			}
		}
	}

	// TODO Find a way to compare stacks on client too
	@Override
	public void updateUsing()
	{
		if(this.state)
		{
			ItemStack stack = this.player.getHeldItem(this.hand);
			if(!this.stack.isEmpty() && stack.getItem() instanceof IItemUsable && ((IItemUsable) this.stack.getItem()).continueUsing(this.player, this.hand, this.stack, stack, this.ticks, this.type))
			{
				//this.stack = stack;
				++this.ticks;
				((IItemUsable) this.stack.getItem()).onUpdateUsing(this.player, this.hand, this.stack, this.ticks, this.type);
			}
			else this.stopUsing(this.hand, this.type);
		}
	}

	@Override
	public void stopUsing(EnumHand hand, int type)
	{
		if(this.state && this.hand == hand && this.type == type)
		{
			this.synchronize(false, hand, type);
			if(!this.stack.isEmpty()) ((IItemUsable) this.stack.getItem()).onStopUsing(this.player, this.hand, this.stack, this.ticks, this.type);
			this.state = false;
			//this.stack = ItemStack.EMPTY;
			//this.ticks = 0;
		}
	}

	@Override
	public void synchronize()
	{
		this.synchronize(this.state, this.hand, this.type);
	}

	// TODO
	protected void synchronize(boolean state, EnumHand hand, int type)
	{
		if(!this.player.world.isRemote) LambdaNetworks.network.sendToAllTracking(new ClientMessageUseItem(this.player, state, type, hand), this.player);
		else if(this.player == Minecraft.getMinecraft().player) LambdaNetworks.network.sendToServer(new ServerMessageUseItem(state, type, hand));
	}

	@Override
	public NBTTagCompound serialize(NBTTagCompound nbt, EnumFacing side) { return nbt; }

	@Override
	public void deserialize(NBTBase nbt) {}
}