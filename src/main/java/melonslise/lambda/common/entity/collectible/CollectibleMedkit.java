package melonslise.lambda.common.entity.collectible;

import melonslise.lambda.common.entity.api.AEntityCollectible;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.sound.LambdaSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class CollectibleMedkit extends AEntityCollectible
{
	public CollectibleMedkit(World world)
	{
		super(world);
		this.setSize(0.4F, 0.2F);
	}

	@Override
	public boolean collect(EntityPlayer player)
	{
		if(player.getHealth() >= player.getMaxHealth()) return false;
		this.playSound(LambdaSounds.item_smallmedkit, 1F, 1F);
		player.heal(3F);
		return true;
	}

	// TODO Move item to constructor
	@Override
	public boolean retrieve(EntityPlayer player, EnumHand hand)
	{
		return player.world.isRemote || player.addItemStackToInventory(new ItemStack(LambdaItems.medkit));
	}
}