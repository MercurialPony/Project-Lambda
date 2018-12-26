package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelTauCannon;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderTau extends RenderItemModel
{
	private ModelTauCannon model = new ModelTauCannon();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/tau.png");

	@Override
	public void render(ItemStack stack, float partialTick, TransformType camera)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(camera == TransformType.GUI)
		{
			/*
			GlStateManager.translate(2F, 0F, 0.3F);
			GlStateManager.rotate(90F, 0F, 0F, 1F);
			GlStateManager.rotate(-60F, 0F, 1F, 0F);
			GlStateManager.scale(0.07F , 0.07F, 0.07F);
			this.model.render(partialTick, 1F);
			*/
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			mc.getTextureManager().bindTexture(texture);
			//GlStateManager.pushMatrix();
			//GlStateManager.translate(0.6F, 2.1F, 0.3F);
			//GlStateManager.scale(0.07F, 0.07F, 0.07F);
			//GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.disableCull();
			this.model.render(partialTick, 1F);
			this.model.setRotations(stack, partialTick);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}