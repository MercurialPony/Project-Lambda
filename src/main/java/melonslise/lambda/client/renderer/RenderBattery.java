package melonslise.lambda.client.renderer;

import melonslise.lambda.client.model.ModelBattery;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBattery extends Render
{
	protected final ModelBattery model = new ModelBattery();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/battery.png");

	public RenderBattery(RenderManager manager)
	{
		super(manager);
	}

	// TODO Pitch rotation? Move to model?
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float ticks)
	{
		this.bindEntityTexture(entity);
		GlStateManager.color(1F, 1F, 1F, 1F);

		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(-entity.rotationYaw, 0F, 1F, 0F);

		GlStateManager.translate(0D, 1.5D / 2, 0D);
		GlStateManager.rotate(180F, 1F, 0F, 0F);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);

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