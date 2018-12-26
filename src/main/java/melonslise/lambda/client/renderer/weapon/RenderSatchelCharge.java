package melonslise.lambda.client.renderer.weapon;

import org.lwjgl.opengl.GL11;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

// TODO Use an actual model
public class RenderSatchelCharge extends Render
{
	ResourceLocation
	top= LambdaUtilities.createLambdaDomain("textures/entities/satchel_top.png"),
	bottom= LambdaUtilities.createLambdaDomain("textures/entities/satchel_bottom.png"),
	side1= LambdaUtilities.createLambdaDomain("textures/entities/satchel_side1.png"),
	side2= LambdaUtilities.createLambdaDomain("textures/entities/satchel_side2.png");

	public RenderSatchelCharge(RenderManager manager)
	{
		super(manager);
	}

	@Override
	public void doRender(Entity entity, double x, double y,  double z, float yaw, float partialTick)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		double h = 0.1D, w1 = 0.2D, w2 = 0.15D;
		Vec3d
		v1 = new Vec3d(-w1, h, -w2),
		v2 = new Vec3d(-w1, h, w2),
		v3 = new Vec3d(w1, h, w2),
		v4 = new Vec3d(w1, h, -w2),
		v5 = new Vec3d(-w1, -h, -w2),
		v6 = new Vec3d(-w1, -h, w2),
		v7 = new Vec3d(w1, -h, w2),
		v8 = new Vec3d(w1, -h, -w2);

		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);

		GlStateManager.rotate(entity.rotationYaw, 0F, 1F, 0F);
		GlStateManager.rotate(entity.rotationPitch, -1F, 0F, 0F);

		this.renderManager.renderEngine.bindTexture(top);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(v1.x, v1.y, v1.z).tex(0D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v2.x, v2.y, v2.z).tex(0D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).tex(1D, 0D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();

		this.renderManager.renderEngine.bindTexture(bottom);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(v7.x, v7.y, v7.z).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v6.x, v6.y, v6.z).tex(0D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(0D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v8.x, v8.y, v8.z).tex(1D, 0D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();

		this.renderManager.renderEngine.bindTexture(side1);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(v2.x, v2.y, v2.z).tex(0D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v6.x, v6.y, v6.z).tex(1D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v7.x, v7.y, v7.z).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).tex(0D, 1D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(v8.x, v8.y, v8.z).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(1D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v1.x, v1.y, v1.z).tex(0D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).tex(0D, 1D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();

		this.renderManager.renderEngine.bindTexture(side2);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(v1.x, v1.y, v1.z).tex(0D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v5.x, v5.y, v5.z).tex(1D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v6.x, v6.y, v6.z).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v2.x, v2.y, v2.z).tex(0D, 1D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(v7.x, v7.y, v7.z).tex(1D, 1D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v8.x, v8.y, v8.z).tex(1D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).tex(0D, 0D).color(255, 255, 255, 255).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).tex(0D, 1D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();

		GL11.glPopMatrix();
		super.doRender(entity, x, y, z, yaw, partialTick);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}