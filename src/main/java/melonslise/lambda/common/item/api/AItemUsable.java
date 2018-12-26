package melonslise.lambda.common.item.api;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

// TODO Find a way to sync the cap to client/copy client cap after stack is replaced
public abstract class AItemUsable extends LambdaItem implements IItemUsable
{
	public AItemUsable(String name)
	{
		super(name);
		this.maxStackSize = 1;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		return oldStack.getItem() != newStack.getItem();
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		if(!world.isRemote) this.decreaseCooldown(stack);
	}

	@Override
	public boolean onStartUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int type)
	{
		if((type != 0 || !this.startPrimaryUsing(player, hand, stack)) && (type != 1 || !this.startSecondaryUsing(player, hand, stack))) return false;
		this.setUser(stack, player);
		return true;
	}

	@Override
	public void onUpdateUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks, int type)
	{
		if (type == 0) this.primaryUsingTick(player, hand, stack, ticks);
		else if (type == 1) this.secondaryUsingTick(player, hand, stack, ticks);
	}

	@Override
	public void onStopUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks, int type)
	{
		this.setUser(stack, null);
		if (type == 0) this.stopPrimaryUsing(player, hand, stack, ticks);
		else if (type == 1) this.stopSecondaryUsing(player, hand, stack, ticks);
	}

	protected boolean startPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return false;
	}

	protected void primaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks) {}

	protected void stopPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks) {}

	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return false;
	}

	protected void secondaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks) {}

	protected void stopSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks) {}

	public static final String keyUser = "user", keyCooldown = "cooldown";

	protected int getUser(ItemStack stack)
	{
		return LambdaUtilities.getTag(stack).hasKey(keyUser) ? LambdaUtilities.getTag(stack).getInteger(keyUser) : -1;
	}

	protected Entity getUser(ItemStack stack, World world)
	{
		return world.getEntityByID(this.getUser(stack));
	}

	protected void setUser(ItemStack stack, int id)
	{
		LambdaUtilities.getTag(stack).setInteger(keyUser, id);
	}

	protected void setUser(ItemStack stack, Entity user)
	{
		this.setUser(stack, user == null ? -1 : user.getEntityId());
	}

	protected boolean resetCooldown(ItemStack stack, int cooldown)
	{
		if(this.getCooldown(stack) > 0) return false;
		this.setCooldown(stack, cooldown);
		return true;
	}

	protected int getCooldown(ItemStack stack)
	{
		return LambdaUtilities.getTag(stack).getInteger(keyCooldown);
	}

	protected void setCooldown(ItemStack stack, int amount)
	{
		LambdaUtilities.getTag(stack).setInteger(keyCooldown, amount > 0 ? amount : 0);
	}

	protected void decreaseCooldown(ItemStack stack)
	{
		this.setCooldown(stack, this.getCooldown(stack) - 1);
	}
}