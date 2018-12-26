package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelGluonHandle;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.CullFace;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderGluon extends RenderItemModel
{
	private ModelGluonHandle model = new ModelGluonHandle();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/gluon_handle.png");

	@Override
	public void render(ItemStack stack, float partialTick, TransformType camera)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(camera == TransformType.GUI)
		{
			/*
			GlStateManager.translate(0.2F, 0.2F, 0F);
			GlStateManager.rotate(170F, 0F, 1F, 0F);
			GlStateManager.scale(0.25F , 0.25F, 0.25F);
			this.model.render(stack, partialTick, 1F);
			*/
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			//GlStateManager.pushMatrix();
			mc.getTextureManager().bindTexture(texture);
			//GlStateManager.translate(0.8F, 0.8F, -0.5F);
			//GlStateManager.rotate(180F, 0F, 0F, 1F);
			//GlStateManager.scale(0.2F, 0.2F, 0.2F);
			GlStateManager.disableCull();
			this.model.render(stack, partialTick, 1F);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}