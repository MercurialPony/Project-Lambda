package melonslise.lambda.client.renderer.weapon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

// TODO Item model
// TODO Move
// TODO Rename
public abstract class RenderItemModel extends TileEntityItemStackRenderer
{
	public TransformType camera;

	// TODO Super?
	// TODO Partial tick
	@Override
	public void renderByItem(ItemStack stack)
	{
		this.renderByItem(stack, Minecraft.getMinecraft().getRenderPartialTicks());
	}

	// TODO Super?
	@Override
	public void renderByItem(ItemStack stack, float partialTick)
	{
		this.render(stack, partialTick, this.camera);
	}

	public abstract void render(ItemStack stack, float partialTick, TransformType camera);
}