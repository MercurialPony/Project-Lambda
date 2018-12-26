package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelCrossbow;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderCrossbow extends RenderItemModel
{
	private ModelCrossbow model = new ModelCrossbow();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/crossbow.png");

	// TODO Pass camera to model?
	// TODO Fix scale
	@Override
	public void render(ItemStack stack, float partialTick, TransformType camera)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if(camera == TransformType.GUI)
		{
			/*
			GlStateManager.rotate(30F, 0F, 1F, 0F);
			GlStateManager.translate(0.3F, 0.8F, 0F);
			GlStateManager.scale(0.1F , -0.1F, 0.1F);
			this.model.render(stack, partialTick, 1F);
			*/
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			//GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(texture);
			//GlStateManager.translate(0.5F, 0.8F, 0F);
			//GlStateManager.scale(0.08F, 0.08F, 0.08F);
			//GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.disableCull();
			this.model.render(stack, partialTick, 1F);
			// TODO Pass using entity instead of player
			this.model.setRotations(stack, partialTick);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}