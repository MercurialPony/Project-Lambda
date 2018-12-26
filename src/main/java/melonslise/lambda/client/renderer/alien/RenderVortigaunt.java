package melonslise.lambda.client.renderer.alien;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import melonslise.lambda.client.model.alien.ModelVortigaunt;
import melonslise.lambda.common.entity.alien.EntityVortigaunt;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderVortigaunt extends RenderLiving
{
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/vortigaunt.png");
	protected static final ResourceLocation[] arcTextures = {LambdaUtilities.createLambdaDomain("textures/entities/arc0.png"), LambdaUtilities.createLambdaDomain("textures/entities/arc1.png"), LambdaUtilities.createLambdaDomain("textures/entities/arc2.png")};

	public RenderVortigaunt(RenderManager manager, float shadow)
	{
		super(manager, new ModelVortigaunt(), shadow);
	}

	// TODO charge ticks 2
	// TODO Radius, amount of pos, do surfaces, optimize
	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		if(entity instanceof EntityVortigaunt)
		{
			EntityVortigaunt vortigaunt = (EntityVortigaunt) entity;
			Vec3d target = vortigaunt.target;
			if(vortigaunt.arc > 0 && target != null)
			{
				double endX = target.x - entity.posX, endY = target.y - entity.posY, endZ = target.z - entity.posZ;
				renderArc(this.renderManager, this.getArcTexture(), 128, 255, 128, 192, x, y, z, 0D, (double) entity.getEyeHeight(), 0D, endX, endY, endZ, 1D);
			}
			if(vortigaunt.charge > 0)
			{
				if(vortigaunt.charge == 2)
				{
					// TODO Better random positions
					ThreadLocalRandom random = ThreadLocalRandom.current();
					vortigaunt.positions.clear();
					ArrayList<BlockPos> positions1 = Lists.newArrayList(BlockPos.getAllInBox((int) entity.posX - 2, (int) entity.posY - 1, (int) entity.posZ - 2, (int) entity.posX + 2, (int) entity.posY + (int) entity.height + 2, (int) entity.posZ + 2));
					while(!positions1.isEmpty() && vortigaunt.positions.size() < random.nextInt(4, 7))
					{
						int index = random.nextInt(positions1.size());
						BlockPos position = positions1.get(index);
						if(entity.world.getBlockState(position).getMaterial().isSolid())
						{
							vortigaunt.positions.add(position);
							positions1.remove(index);
						}
					}
				}
				if(vortigaunt.charge > 2)
				{
					Iterator<BlockPos> iterator = vortigaunt.positions.iterator();
					while(iterator.hasNext())
					{
						BlockPos position = iterator.next();
						double endX = (double) position.getX() + Math.random() - entity.posX, endY = (double) position.getY() + 1D - entity.posY, endZ = (double) position.getZ() + Math.random() - entity.posZ;
						renderArc(this.renderManager, this.getArcTexture(), 128, 255, 128, 192, x, y, z, 0D, (double) entity.height / 2D, 0D, endX, endY, endZ, 0.5D);
					}
				}
			}
		}
	}

	// TODO Glow
	// TODO Improve end
	// TODO Shorten with helper
	// TODO NOT AFFECTED BY LIGHTING;
	// TODO Improve
	// TODO less args
	// TODO color
	// TODO Use yaw?
	public static void renderArc(RenderManager manager, ResourceLocation texture, int red, int green, int blue, int alpha, double x, double y, double z, double offsetX, double offsetY, double offsetZ, double endX, double endY, double endZ, double width)
	{
		double deltaX = endX - offsetX, deltaY = endY - offsetY, deltaZ = endZ - offsetZ;

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
		buffer.pos(0D, -width / 2D, 0D).tex(0D, 0D).color(red, green, blue, alpha).endVertex();
		buffer.pos(deltaX, deltaY - width / 2D, deltaZ).tex(0D, 1D).color(red, green, blue, alpha).endVertex();
		buffer.pos(deltaX, deltaY + width / 2D, deltaZ).tex(1D, 1D).color(red, green, blue, alpha).endVertex();
		buffer.pos(0D, width / 2D, 0D).tex(1D, 0D).color(red, green, blue, alpha).endVertex();

		buffer.pos(-width / 2D, 0D, -width / 2D).tex(0D, 0D).color(red, green, blue, alpha).endVertex();
		buffer.pos(deltaX - width / 2D, deltaY, deltaZ - width / 2D).tex(0D, 1D).color(red, green, blue, alpha).endVertex();
		buffer.pos(deltaX + width / 2D, deltaY, deltaZ + width / 2D).tex(1D, 1D).color(red, green, blue, alpha).endVertex();
		buffer.pos(width / 2D, 0D, width / 2D).tex(1D, 0D).color(red, green, blue, alpha).endVertex();
		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldRender(EntityLiving entity, ICamera camera, double camX, double camY, double camZ)
	{
		if(super.shouldRender(entity, camera, camX, camY, camZ)) return true;
		else
		{
			if(entity instanceof EntityVortigaunt)
			{
				EntityVortigaunt vortigaunt = (EntityVortigaunt) entity;
				Vec3d target = vortigaunt.target;
				if(target != null && camera.isBoundingBoxInFrustum(new AxisAlignedBB(entity.posX, entity.posY + (double) entity.height / 2D, entity.posZ, target.x, target.y, target.z)))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}

	protected ResourceLocation getArcTexture()
	{
		return arcTextures[ThreadLocalRandom.current().nextInt(3)];
	}
}