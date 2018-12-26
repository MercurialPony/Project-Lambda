package melonslise.lambda.common.block.api;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockNamed extends Block
{
	public BlockNamed(String name, Material material)
	{
		super(material);
		this.setRegistryName(LambdaUtilities.createLambdaDomain(name));
		this.setUnlocalizedName(LambdaUtilities.prefixLambda(name));
	}
}