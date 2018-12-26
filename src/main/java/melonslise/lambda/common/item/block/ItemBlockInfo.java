package melonslise.lambda.common.item.block;

import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.common.item.api.block.ItemBlockNamed;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemBlockInfo extends ItemBlockNamed implements ISuitDisplayProvider
{
	public ItemBlockInfo(Block block)
	{
		super(block);
	}

	@Override
	public void renderDisplay(Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 0);
		}
	}
}