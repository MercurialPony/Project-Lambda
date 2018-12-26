package melonslise.lambda.client.renderer;

import melonslise.lambda.client.model.ModelMedkit;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMedkit extends Render
{
	protected final ModelMedkit model = new ModelMedkit();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/medkit.png");

	public RenderMedkit(RenderManager renderManager)
	{
		super(renderManager);
	}

	// TODO Pitch rotation?
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float ticks)
	{
		this.bindEntityTexture(entity);
		GlStateManager.color(1F, 1F, 1F, 1F);

		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(-entity.rotationYaw, 0F, 1F, 0F);

		GlStateManager.translate(0D, 1.5D, 0D);
		GlStateManager.rotate(180F, 1F, 0F, 0F);

		this.model.render(entity, 0F, 0F, 0F, 0F, 0F, 0.0625F);

		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, yaw, ticks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.texture;
	}
}