package melonslise.lambda.common.item.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public interface IItemReloadable
{
	public int onStartReloading(EntityPlayer player, EnumHand hand, ItemStack stack);

	public void onUpdateReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks);

	public void onStopReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks);

	public default boolean continueReloading(EntityPlayer player, ItemStack oldStack, ItemStack newStack)
	{
		return player.world.isRemote && ItemStack.areItemsEqual(oldStack, newStack) || !player.world.isRemote && ItemStack.areItemStacksEqual(oldStack, newStack);
	}	
}