package melonslise.lambda.client.renderer;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderBullet extends Render
{
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/bullet.png");

	public RenderBullet(RenderManager manager)
	{
		super(manager);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick)
	{
		Vec3d start = new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTick, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTick, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTick);
		Vec3d end = new Vec3d(entity.motionX, entity.motionY, entity.motionZ).add(start);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getEntityTexture(entity));
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		LambdaUtilities.drawFlatQuad(start, end, 0.02D, 0xffffff80, true, partialTick);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}