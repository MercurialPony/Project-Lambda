package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.client.model.weapon.ModelGrenade;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderImpactGrenade extends Render
{
	private ModelGrenade model = new ModelGrenade();
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/grenade.png");

	public RenderImpactGrenade(RenderManager manager)
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
		GlStateManager.rotate(180F - (entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTick), 0F, -1F, 0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTick, 1F, 0F, 0F);
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