package melonslise.lambda.common.item.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public interface IItemUsable
{
	public boolean onStartUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int type);

	public void onUpdateUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks, int type);

	public default boolean continueUsing(EntityPlayer player, EnumHand hand, ItemStack oldStack, ItemStack newStack, int ticks, int type)
	{
		return player.world.isRemote && ItemStack.areItemsEqual(oldStack, newStack) || !player.world.isRemote && ItemStack.areItemStacksEqual(oldStack, newStack);
	}

	public void onStopUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks, int type);
}