package melonslise.lambda.client.renderer.layer;

import melonslise.lambda.client.model.weapon.ModelGluonBackpack;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

// TODO More params + generics
// TODO Copy code from layerarmorbase
public class LayerGluonBackpack implements LayerRenderer<EntityLivingBase>
{
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/gluon_backpack.png");
	protected ModelGluonBackpack model = new ModelGluonBackpack();
	private final RenderLivingBase renderer;

	public LayerGluonBackpack(RenderLivingBase renderer)
	{
		this.renderer = renderer;
	}

	// TODO Generic condition
	// TODO Check how armor layer does this
	@Override
	public void doRenderLayer(EntityLivingBase entity, float swing, float swingAmount, float partialTick, float age, float yaw, float pitch, float scale)
	{
		for(EnumHand hand : EnumHand.values())
		{
			if(entity.getHeldItem(hand).getItem() == LambdaItems.weapon_gluon)
			{
				GlStateManager.pushMatrix();

				GlStateManager.translate(-0.05F, -0.2F, 0.3F);
				GlStateManager.scale(2.2F, 2.2F, 2.2F);
				this.renderer.bindTexture(texture);
				this.model.render(entity, swing, swingAmount, age, yaw, pitch, scale);

				GlStateManager.popMatrix();
				break;
			}
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}