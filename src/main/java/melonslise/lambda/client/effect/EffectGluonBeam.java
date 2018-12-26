package melonslise.lambda.client.effect;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.client.effect.api.Effect;
import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

// TODO Base class?
public class EffectGluonBeam extends Effect
{
	protected static final ResourceLocation
	texture1 = new ResourceLocation(LambdaCore.ID, "textures/entities/gluon_beam1.png"),
	texture2 = new ResourceLocation(LambdaCore.ID, "textures/entities/gluon_beam2.png");

	private EntityLivingBase source;
	private double range;
	private int ticks;

	public EffectGluonBeam(EntityLivingBase entity, double range)
	{
		this.source = entity;
		this.range = range;
	}

	// TODO Fix same errors as tau beam
	// TODO Ray trace entities too
	@Override
	public void render(float partialTick)
	{
		Vec3d start = new Vec3d(this.source.lastTickPosX + (this.source.posX - this.source.lastTickPosX) * (double) partialTick, this.source.lastTickPosY + (this.source.posY - this.source.lastTickPosY) * (double) partialTick + this.source.getEyeHeight(), this.source.lastTickPosZ + (this.source.posZ - this.source.lastTickPosZ) * (double) partialTick);
		Vec3d offset = start.add(LambdaUtilities.getHeldOffset((EntityLivingBase) this.source, LambdaUtilities.getUsingItem(this.source).getHand(), new Vec3d(-0.4F, -0.25F, 0.7F)));
		Vec3d end = this.source.getLookVec().scale(this.range).add(start);
		RayTraceResult result = Minecraft.getMinecraft().world.rayTraceBlocks(start, end, false, true, false);
		if(result != null) end = result.hitVec;
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture1);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		LambdaUtilities.drawFlatQuad(offset, end, 0.1D, 0xffffffff, true, partialTick);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	@Override
	public void update()
	{
		ICapabilityUsingItem capabilityUsing = LambdaUtilities.getUsingItem(this.source);
		if(source.isDead || !capabilityUsing.get() || capabilityUsing.getStack().getItem() != LambdaItems.weapon_gluon) this.setExpired();
		++this.ticks;
	}

	/*
	public static void drawCylinder(ResourceLocation texture, Vec3d start, Vec3d end, int red, int green, int blue, int alpha, double radius, int ticks, float partialTick)
	{
		Minecraft mc = Minecraft.getMinecraft();

		Vec3d origin = new Vec3d(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double) partialTick, mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double) partialTick, mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double) partialTick);

		int edges = 16;
		Vec3d[] offset = new Vec3d[2 * edges];
		for (int a = 0; a < 2 * edges; a++)
		{
			double b = 2 * Math.PI * a / edges;
			offset[a] = new Vec3d(0D , Math.sin(b) * radius, Math.cos(b) * radius);
		}

		mc.getTextureManager().bindTexture(texture);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		double du = - (double) ticks * 0.05D;
		double d = end.subtract(start).lengthVector();
		double dt = 0.5D;
		for (int a = 0; a < 2 * edges - 1; a++)
		{
			buffer.pos(start.x - origin.x + offset[a].x, start.y - origin.y + offset[a].y, start.z - origin.z + offset[a].z).tex(0.5D + (double) a / 2D / (double) edges, d * 1.8D - du * 0.8D).color(red, green, blue, alpha).endVertex();
			buffer.pos(start.x - origin.x + offset[a + 1].x, start.y - origin.y + offset[a + 1].y, start.z - origin.z + offset[a + 1].z).tex(0.5D + (double) (a + 1) / 2D / (double) edges, d * 1.8D - du * 0.8D).color(red, green, blue, alpha).endVertex();
			buffer.pos(end.x - origin.x + offset[a + 1].x, end.y - origin.y + offset[a + 1].y, end.z - origin.z + offset[a + 1].z).tex(0.5D + (double) (a + 1) / 2D / (double) edges, -du * 0.8D).color(red, green, blue, alpha).endVertex();
			buffer.pos(end.x - origin.x + offset[a].x, end.y - origin.y + offset[a].x, end.z - origin.z + offset[a].x).tex(0.5D + (double) a / 2D / (double) edges, -du * 0.8D).color(red, green, blue, alpha).endVertex();
		} 
		tessellator.draw();

		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();

		GlStateManager.popMatrix();
	}
	*/
}