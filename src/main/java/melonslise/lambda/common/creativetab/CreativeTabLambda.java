package melonslise.lambda.common.creativetab;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.common.entity.LambdaEntities;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabLambda extends CreativeTabs
{
	public CreativeTabLambda()
	{
		super(LambdaCore.ID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(LambdaItems.weapon_crowbar);
	}

	@SideOnly(Side.CLIENT)
	public void displayAllRelevantItems(NonNullList<ItemStack> stacks)
	{
		super.displayAllRelevantItems(stacks);
		stacks.add(LambdaUtilities.createEgg(LambdaEntities.headcrab.getRegistryName()));
		stacks.add(LambdaUtilities.createEgg(LambdaEntities.zombie.getRegistryName()));
		stacks.add(LambdaUtilities.createEgg(LambdaEntities.vortigaunt.getRegistryName()));
		stacks.add(LambdaUtilities.createEgg(LambdaEntities.houndeye.getRegistryName()));
		stacks.add(LambdaUtilities.createEgg(LambdaEntities.barnacle.getRegistryName()));
	}
}