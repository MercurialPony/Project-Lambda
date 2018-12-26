package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.capability.api.ICapability;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

// TODO Stack cap base?
public interface ICapabilityUsingItem extends ICapability
{
	public boolean get();

	public EnumHand getHand();

	public ItemStack getStack();

	public int getTicks();

	public int getType();

	public void startUsing(EnumHand hand, int type);

	public void updateUsing();

	public void stopUsing(EnumHand hand, int type);
}