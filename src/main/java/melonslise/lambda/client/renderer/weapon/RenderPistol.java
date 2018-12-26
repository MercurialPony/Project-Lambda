package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelGlock17;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderPistol extends RenderItemModel
{
	private ModelGlock17 model = new ModelGlock17();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/glock17.png");

	// TODO Pass camera to model?
	// TODO Fix scale
	@Override
	public void render(ItemStack stack, float partialTicks, TransformType camera)
	{
		if(camera == TransformType.GUI)
		{
			/*
			GlStateManager.rotate(40F, 0F, 1F, 0F);
			GlStateManager.translate(-0.1F, 0.8F, 0F);
			GlStateManager.scale(0.2F , -0.2F, 0.2F);
			this.model.render(partialTicks, 1F);
			*/
			// TODO Don't hardcode gui variant
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			//GlStateManager.pushMatrix();
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			//GlStateManager.translate(0.55F, 0.8F, 0F);
			//GlStateManager.scale(0.1F, 0.1F, 0.1F);
			//GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.disableCull();
			this.model.render(partialTicks, 1F);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}