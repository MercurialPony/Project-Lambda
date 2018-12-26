package melonslise.lambda.client.particle;

import org.lwjgl.opengl.GL11;

import melonslise.lambda.LambdaCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// TODO Rename
// TODO Use size instead of radius
// TODO Set gravity according to motion?
public class ParticleTauGlow extends Particle
{
	// TODO Texture path
	protected static final ResourceLocation texture = new ResourceLocation(LambdaCore.ID, "textures/entities/tau_glow.png");
	protected double radius;
	protected float decay;

	public ParticleTauGlow(World world, double x, double y, double z, double motionX, double motionY, double motionZ, double radius, float decay, float gravity)
	{
		super(world, x, y, z);
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.radius = radius;
		this.decay = decay;
		this.particleGravity = gravity;
	}

	public ParticleTauGlow(World world, Vec3d point, Vec3d motion, double radius, float decay, float gravity)
	{
		this(world, point.x, point.y, point.z, motion.x, motion.y, motion.z, radius, decay, gravity);
	}

	// TODO Fix going through walls
	// TODO Fix alpha counter?
	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if(this.onGround)
		{	
			this.motionY = Math.abs(this.motionY) * 0.6D;
			if(this.motionY < 2D * (double) this.particleGravity) this.motionY = 0D;
		}
		else this.motionY -= (double) this.particleGravity;
		this.motionX *= 0.9D;
		this.motionZ *= 0.9D;
		this.move(this.motionX, this.motionY, this.motionZ);
		this.particleAlpha -= this.decay;
		if(this.particleAlpha <= 0F) this.setExpired();
	}

	public int getFXLayer()
	{
		return 3;
	}

	// TODO Properly disable lighting
	// TODO Fix drawing behind water
	// TODO Shorten
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entity, float partialTick, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.disableLighting();
		//GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
		GlStateManager.depthMask(false);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

		Vec3d position = new Vec3d(this.prevPosX + (this.posX - this.prevPosX) * (double) partialTick, this.prevPosY + (this.posY - this.prevPosY) * (double)partialTick, this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTick);
		Vec3d offset[] = new Vec3d[] { new Vec3d(-(rotationX + rotationXY) * this.radius, -rotationZ * this.radius, -(rotationXZ + rotationYZ) * this.radius), new Vec3d((-rotationX + rotationXY) * this.radius, rotationZ * this.radius, (rotationXZ - rotationYZ) * this.radius), new Vec3d((rotationX + rotationXY) * this.radius, rotationZ * this.radius, (rotationXZ + rotationYZ) * this.radius), new Vec3d((rotationX - rotationXY) * this.radius, -rotationZ * this.radius, (-rotationXZ + rotationYZ) * this.radius) };
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(position.x  - interpPosX + offset[0].x, position.y - interpPosY + offset[0].y, position.z - interpPosZ + offset[0].z).tex(1F, 1F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
		buffer.pos(position.x  - interpPosX + offset[1].x, position.y - interpPosY + offset[1].y, position.z - interpPosZ + offset[1].z).tex(1F, 0F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
		buffer.pos(position.x  - interpPosX + offset[2].x, position.y - interpPosY + offset[2].y, position.z - interpPosZ + offset[2].z).tex(0F, 0F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
		buffer.pos(position.x  - interpPosX + offset[3].x, position.y - interpPosY + offset[3].y, position.z - interpPosZ + offset[3].z).tex(0F, 1F).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
		Tessellator.getInstance().draw();

		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
	}
}