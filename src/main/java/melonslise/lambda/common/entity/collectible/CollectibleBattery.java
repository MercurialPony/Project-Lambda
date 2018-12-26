package melonslise.lambda.common.entity.collectible;

import melonslise.lambda.common.capability.entity.ICapabilityPower;
import melonslise.lambda.common.entity.api.AEntityCollectible;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class CollectibleBattery extends AEntityCollectible
{
	public CollectibleBattery(World world)
	{
		super(world);
		this.setSize(0.3F, 0.5F);
	}

	@Override
	public boolean collect(EntityPlayer player)
	{
		ICapabilityPower capabilityPower = LambdaUtilities.getPower(player);
		if(!LambdaUtilities.isWearingHazard(player) || capabilityPower.get() >= 20F) return false;
		this.playSound(LambdaSounds.item_gunpickup, 1F, 1F);
		capabilityPower.restore(3F);
		return true;
	}

	// TODO Move item to constructor
	@Override
	public boolean retrieve(EntityPlayer player, EnumHand hand)
	{
		return player.world.isRemote || player.addItemStackToInventory(new ItemStack(LambdaItems.battery));
	}
}