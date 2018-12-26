package melonslise.lambda.client.renderer;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBolt extends Render
{
	public static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/bolt.png");

	public RenderBolt(RenderManager manager)
	{
		super(manager);
	}

	// TODO Fix texture
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		this.bindEntityTexture(entity);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90F, 0F, 1F, 0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0F, 0F, 1F);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		int i = 0;
		float f = 0F;
		float f1 = 0.5F;
		float f2 = 0F;
		float f3 = 0.15625F;
		float f4 = 0F;
		float f5 = 0.15625F;
		float f6 = 0.15625F;
		float f7 = 0.3125F;
		float f8 = 0.05625F;
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(45F, 1F, 0F, 0F);
		GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
		GlStateManager.translate(-4F, 0F, 0F);
		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}
		/*
		GlStateManager.glNormal3f(0.05625F, 0F, 0F);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(-7D, -2D, -2D).tex(0D, 0D).endVertex();
		bufferbuilder.pos(-7D, -2D, 2D).tex(0D, 1D).endVertex();
		bufferbuilder.pos(-7D, 2D, 2D).tex(1D, 1D).endVertex();
		bufferbuilder.pos(-7D, 2D, -2D).tex(1D, 0D).endVertex();
		tessellator.draw();
		GlStateManager.glNormal3f(-0.05625F, 0F, 0F);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(-7D, 2D, -2D).tex(0D, 0D).endVertex();
		bufferbuilder.pos(-7D, 2D, 2D).tex(0D, 1D).endVertex();
		bufferbuilder.pos(-7D, -2D, 2D).tex(1D, 1D).endVertex();
		bufferbuilder.pos(-7D, -2D, -2D).tex(1D, 0D).endVertex();
		tessellator.draw();
		*/
		for (int a = 0; a < 4; ++a)
		{
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			GlStateManager.glNormal3f(0F, 0F, 0.05625F);
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos(-8D, -2D, 0D).tex(0D, 0D).endVertex();
			bufferbuilder.pos(8D, -2D, 0D).tex(0D, 1D).endVertex();
			bufferbuilder.pos(8D, 2D, 0D).tex(1D, 1D).endVertex();
			bufferbuilder.pos(-8D, 2D, 0D).tex(1D, 0D).endVertex();
			tessellator.draw();
		}
		if (this.renderOutlines)
		{
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}