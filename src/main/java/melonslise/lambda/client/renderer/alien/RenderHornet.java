package melonslise.lambda.client.renderer.alien;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

// TODO OVerride should render?
public class RenderHornet extends Render
{
	public RenderHornet(RenderManager manager)
	{
		super(manager);
	}

	// TODO Static renderer
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick)
	{
		super.doRender(entity, x, y, z, yaw, partialTick);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		Vec3d v1 = new Vec3d(-0.1D, -0.1D, 0D), v2 = new Vec3d(0.1D, -0.1D, 0D), v3 = new Vec3d(0D, 0.1D, 0D), v4 = new Vec3d(0D, 0D, 0.4D);

		GlStateManager.pushMatrix();

		GlStateManager.disableTexture2D();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate(x, y, z);

		GlStateManager.rotate(entity.rotationYaw, 0F, 1F, 0F);
		GlStateManager.rotate(entity.rotationPitch, -1F, 0F, 0F);

		buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);

		buffer.pos(v1.x, v1.y, v1.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v2.x, v2.y, v2.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).color(115, 11, 11, 255).endVertex();

		buffer.pos(v2.x, v2.y, v2.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v1.x, v1.y, v1.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).color(115, 11, 11, 255).endVertex();

		buffer.pos(v1.x, v1.y, v1.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v3.x, v3.y, v3.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).color(115, 11, 11, 255).endVertex();

		buffer.pos(v3.x, v3.y, v3.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v2.x, v2.y, v2.z).color(115, 11, 11, 255).endVertex();
		buffer.pos(v4.x, v4.y, v4.z).color(115, 11, 11, 255).endVertex();

		tessellator.draw();

		GlStateManager.disableRescaleNormal();
		GlStateManager.enableTexture2D();

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}