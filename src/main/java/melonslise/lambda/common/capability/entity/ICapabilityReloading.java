package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.capability.api.ICapability;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public interface ICapabilityReloading extends ICapability
{
	public boolean get();

	public EnumHand getHand();

	public ItemStack getStack();

	public int getTicks();

	public void startReloading(EnumHand hand);

	public void updateReloading();

	public void stopReloading(EnumHand hand);
}