package melonslise.lambda.common.item.api;

import melonslise.lambda.common.creativetab.LambdaCreativeTabs;

public class LambdaItem extends ItemNamed
{
	public LambdaItem(String name)
	{
		super(name);
		this.setCreativeTab(LambdaCreativeTabs.tab);
	}
}