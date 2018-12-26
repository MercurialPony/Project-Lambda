package melonslise.lambda.common.block.api;

import melonslise.lambda.common.creativetab.LambdaCreativeTabs;
import net.minecraft.block.material.Material;

public class LambdaBlock extends BlockNamed
{
	public LambdaBlock(String name, Material material)
	{
		super(name, material);
		this.setCreativeTab(LambdaCreativeTabs.tab);
	}
}