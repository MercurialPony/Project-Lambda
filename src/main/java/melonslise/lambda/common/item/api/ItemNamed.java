package melonslise.lambda.common.item.api;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.item.Item;

public class ItemNamed extends Item
{
	public ItemNamed(String name)
	{
		this.setRegistryName(name);
		this.setUnlocalizedName(LambdaUtilities.prefixLambda(name));
	}
}