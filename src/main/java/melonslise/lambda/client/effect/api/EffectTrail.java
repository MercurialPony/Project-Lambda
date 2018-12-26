package melonslise.lambda.client.effect.api;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.TrailPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

// TODO Replace trail point with trail line?
public abstract class EffectTrail extends Effect
{
	protected Entity entity;
	// TODO linked list
	protected ArrayList<TrailPoint> points = new ArrayList<TrailPoint>();
	protected float decay;
	protected double width;

	public EffectTrail(Entity entity, float decay, double width)
	{
		this.entity = entity;
		this.decay = decay;
		this.width = width;
	}

	public EffectTrail(Entity entity)
	{
		this(entity, 0.02F, 0.3D);
	}

	// TODO Static renderer
	// TODO Don't draw if alpha 0
	// TODO Configurable color
	@Override
	public void render(float partialTick)
	{
		Minecraft mc = Minecraft.getMinecraft();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		Vec3d origin = LambdaUtilities.getRenderOrigin(partialTick);

		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());

		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		for(int a = 0; a < this.points.size() - 1; ++a)
		{
			TrailPoint
			point1 = this.points.get(a),
			point2 = this.points.get(a + 1);

			Vec3d
			offset1 = point1.getPosition().subtract(origin),
			offset2 = point2.getPosition().subtract(origin);

			float alpha = point1.getAlpha(partialTick);

			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			buffer.pos(offset1.x, offset1.y, offset1.z - this.width / 2D).tex(0D, 0D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset1.x, offset1.y, offset1.z + this.width / 2D).tex(0D, 1D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset2.x, offset2.y, offset2.z + this.width / 2D).tex(1D, 1D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset2.x, offset2.y, offset2.z - this.width / 2D).tex(1D, 0D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();

			buffer.pos(offset1.x - this.width / 2D, offset1.y, offset1.z).tex(0D, 0D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset1.x + this.width / 2D, offset1.y, offset1.z).tex(0D, 1D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset2.x + this.width / 2D, offset2.y, offset2.z).tex(1D, 1D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset2.x - this.width / 2D, offset2.y, offset2.z).tex(1D, 0D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();

			buffer.pos(offset1.x, offset1.y - this.width / 2D, offset1.z).tex(0D, 0D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset1.x, offset1.y + this.width / 2D, offset1.z).tex(0D, 1D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset2.x, offset2.y + this.width / 2D, offset2.z).tex(1D, 1D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			buffer.pos(offset2.x, offset2.y - this.width / 2D, offset2.z).tex(1D, 0D).color(0.8F, 0.8F, 0.8F, alpha).endVertex();
			tessellator.draw();
		}

		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
	}

	@Override
	public void update()
	{
		Iterator<TrailPoint> iterator = this.points.iterator();
		while(iterator.hasNext())
		{
			TrailPoint point = iterator.next();
			float alpha = point.getAlpha();
			if(alpha <= 0F) iterator.remove();
			else
			{
				if(alpha - this.decay > 0F) point.setAlpha(alpha - this.decay);
				else point.setAlpha(0F);
			}
		}
		if(this.entity.isDead && this.points.isEmpty()) this.setExpired();
		// TODO Use entity last position?
		if(!this.entity.isDead) this.points.add(new TrailPoint(this.entity.posX, this.entity.posY, this.entity.posZ, 1F));
	}

	// TODO Extra parameters?
	protected abstract ResourceLocation getTexture();
}