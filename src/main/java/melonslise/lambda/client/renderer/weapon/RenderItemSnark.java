package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.alien.ModelSnark;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderItemSnark extends RenderItemModel
{
	protected ModelSnark model = new ModelSnark();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/snark.png");

	@Override
	public void render(ItemStack stack, float partialTick, TransformType camera)
	{
		if(camera == TransformType.GUI)
		{
			ResourceLocation name = stack.getItem().getRegistryName();
			LambdaUtilities.RenderItem(new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), "weapon/gui/" + name.getResourcePath()), "inventory"), stack);
		}
		else
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			//GlStateManager.translate(0.3F, 2.55F, 0.5F);
			//GlStateManager.scale(0.08F, 0.08F, 0.08F);
			//GlStateManager.rotate(180F, 1F, 0F, 0F);
			//GlStateManager.rotate(20F, 0F, 1F, 0F);
			GlStateManager.disableCull();
			this.model.render(1F);
			this.model.setRotationAngles(1F, ((float) Minecraft.getMinecraft().player.ticksExisted + partialTick) / 15F);
			GlStateManager.enableCull();
			//GlStateManager.popMatrix();
		}
	}
}