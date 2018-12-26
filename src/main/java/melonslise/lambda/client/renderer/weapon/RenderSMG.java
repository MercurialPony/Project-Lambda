package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelMP5;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSMG extends RenderItemModel
{
	private ModelMP5 model = new ModelMP5();
	// TODO Move texture?
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/mp5.png");

	// TODO Pass camera to model?
	// TODO Fix scale
	@Override
	public void render(ItemStack stack, float partialTicks, TransformType camera)
	{
		if(camera == TransformType.GUI)
		{
			/*
			GlStateManager.rotate(40F, 0F, 1F, 0F); // 40F
			GlStateManager.translate(0.05F, 0.8F, 0F);
			GlStateManager.scale(0.085F , -0.085F, 0.085F);
			this.model.render(partialTicks, 1F);
			*/
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			//GlStateManager.pushMatrix();
			//GlStateManager.translate(0.5F, 0.8F, -0.15F);
			//GlStateManager.scale(0.07F, 0.07F, 0.07F);
			//GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.disableCull();
			this.model.render(partialTicks, 1F);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}