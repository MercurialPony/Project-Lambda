package melonslise.lambda.common.item.api.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockNamed extends ItemBlock
{
	public ItemBlockNamed(Block block)
	{
		super(block);
		this.setRegistryName(block.getRegistryName());
	}
}