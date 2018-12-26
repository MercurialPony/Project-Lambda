package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.item.api.IItemUsable;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class CapabilityZooming implements ICapabilityZooming
{
	private static final ResourceLocation id = LambdaUtilities.createLambdaDomain("zooming");

	private EntityPlayer player;
	private boolean state;
	private EnumHand hand = EnumHand.MAIN_HAND;
	private ItemStack stack = ItemStack.EMPTY;
	private int ticks;

	public CapabilityZooming(EntityPlayer player)
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
	public void startZooming(EnumHand hand)
	{
		ItemStack stack = this.player.getHeldItem(hand);
		if(!this.state && !stack.isEmpty())
		{
			this.synchronize(true, hand);
			this.state = true;
			this.hand = hand;
			this.stack = stack;
			this.ticks = 0;
		}
	}

	// TODO Find a way to compare stacks on client too?
	// TODO Refer to an interface to check if can continue using like in other caps
	@Override
	public void updateZooming()
	{
		if(this.state)
		{
			ItemStack stack = this.player.getHeldItem(this.hand);
			if(!this.stack.isEmpty() && !stack.isEmpty() && (this.player.world.isRemote && ItemStack.areItemsEqual(this.stack, stack) || !this.player.world.isRemote && ItemStack.areItemStacksEqual(this.stack, stack)))
			{
				//this.stack = stack;
				++this.ticks;
			}
			else this.stopZooming(this.hand);
		}
	}

	@Override
	public void stopZooming(EnumHand hand)
	{
		if(this.state && this.hand == hand)
		{
			this.synchronize(false, hand);
			this.state = false;
		}
	}

	@Override
	public void toggleZooming(EnumHand hand)
	{
			if(!this.state) this.startZooming(hand);
			else if(this.hand == hand) this.stopZooming(hand);
	}

	@Override
	public void synchronize()
	{
		this.synchronize(this.state, this.hand);
	}

	// TODO
	protected void synchronize(boolean state, EnumHand hand)
	{
	}

	@Override
	public NBTTagCompound serialize(NBTTagCompound nbt, EnumFacing side) { return nbt; }

	@Override
	public void deserialize(NBTBase nbt) {}
}