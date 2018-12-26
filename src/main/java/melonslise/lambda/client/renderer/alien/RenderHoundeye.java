package melonslise.lambda.client.renderer.alien;

import org.lwjgl.opengl.GL11;

import melonslise.lambda.client.model.alien.ModelHoundeye;
import melonslise.lambda.common.entity.alien.EntityHoundeye;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class RenderHoundeye extends RenderLiving
{
	protected static final ResourceLocation
	texture = LambdaUtilities.createLambdaDomain("textures/entities/houndeye.png"),
	blastTexture = LambdaUtilities.createLambdaDomain("textures/entities/shockwave.png");

	public RenderHoundeye(RenderManager manager, float shadow)
	{
		super(manager, new ModelHoundeye(), shadow);
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, yaw, partialTicks);
		if(entity instanceof EntityHoundeye)
		{
			EntityHoundeye houndeye = (EntityHoundeye) entity;
			AxisAlignedBB radius = new AxisAlignedBB(entity.posX - 5D, entity.posY - 2D, entity.posZ - 5D, entity.posX + 5D, entity.posY + 2D, entity.posZ + 5D);
			int allies = entity.world.getEntitiesWithinAABB(EntityHoundeye.class, radius).size() - 1;
			if(houndeye.blast > 0) renderBlast(this.renderManager, this.getBlastTexture(), 255, 255 - MathHelper.clamp(allies, 0, 3) * 64, 255, 192, x, y, z, 0D, (double) houndeye.height / 2D, 0D, houndeye.oldBlastRadius + (houndeye.blastRadius - houndeye.oldBlastRadius) * partialTicks);
		}
	}

	// TODO Glow
	public static void renderBlast(RenderManager manager, ResourceLocation texture, int red, int green, int blue, int alpha, double x, double y, double z, double offsetX, double offsetY, double offsetZ, double radius)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		manager.renderEngine.bindTexture(texture);

		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		//GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.translate(x + offsetX, y + offsetY, z + offsetZ);

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(-radius, 0D, -radius).tex(0D, 0D).color(red, green, blue, alpha).endVertex();
		buffer.pos(-radius, 0D, radius).tex(0D, 1D).color(red, green, blue, alpha).endVertex();
		buffer.pos(radius, 0D, radius).tex(1D, 1D).color(red, green, blue, alpha).endVertex();
		buffer.pos(radius, 0D, -radius).tex(1D, 0D).color(red, green, blue, alpha).endVertex();
		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	// TODO Check if charging?
	@Override
	public boolean shouldRender(EntityLiving entity, ICamera camera, double camX, double camY, double camZ)
	{
		if(super.shouldRender(entity, camera, camX, camY, camZ)) return true;
		else return camera.isBoundingBoxInFrustum(new AxisAlignedBB(entity.posX - 5D, entity.posY + (double) entity.height / 2D - 2D, entity.posZ - 5D, entity.posX + 5D, entity.posY + (double) entity.height / 2D + 2D, entity.posZ + 5D));
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}

	protected ResourceLocation getBlastTexture()
	{
		return blastTexture;
	}
}