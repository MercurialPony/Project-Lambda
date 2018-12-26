package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelRocket;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRocket extends Render
{
	private ModelRocket model = new ModelRocket();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/rocket.png");

	public RenderRocket(RenderManager manager)
	{
		super(manager);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick)
	{
		this.bindEntityTexture(entity);
		GlStateManager.color(1F, 1F, 1F, 1F);

		GlStateManager.pushMatrix();

		GlStateManager.translate( x, y, z);
		GlStateManager.rotate(180F - entity.rotationYaw, 0F, -1F, 0F);
		GlStateManager.rotate(entity.rotationPitch, 1F, 0F, 0F);
		GlStateManager.scale(-1F, -1F, 1F);
		this.model.render(entity, 0F, 0F, 0F, 0F, 0F, 0.0625F);

		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, yaw, partialTick);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}