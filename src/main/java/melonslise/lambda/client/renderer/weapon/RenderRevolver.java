package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelPython;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderRevolver extends RenderItemModel
{
	private ModelPython model = new ModelPython();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/python.png");

	// TODO Pass camera to model?
	// TODO Fix scale
	@Override
	public void render(ItemStack stack, float partialTicks, TransformType camera)
	{
		if(camera == TransformType.GUI)
		{
			/*
			GlStateManager.translate(0.8F, 0F, 0.2F);
			GlStateManager.rotate(90F, 0F, 0F, 1F);
			GlStateManager.rotate(-55F, 0F, 1F, 0F);
			GlStateManager.scale(0.1F , 0.1F, 0.1F);
			this.model.render(partialTicks, 1F);
			*/
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			//GlStateManager.pushMatrix();
			//GlStateManager.translate(0.5F, 0.85F, -0.1F);
			//GlStateManager.scale(0.06F, 0.06F, 0.06F);
			//GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.disableCull();
			this.model.render(partialTicks, 1F);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}