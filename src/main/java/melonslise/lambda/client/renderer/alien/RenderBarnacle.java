package melonslise.lambda.client.renderer.alien;

import melonslise.lambda.client.model.alien.ModelBarnacle;
import melonslise.lambda.common.entity.alien.EntityBarnacle;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderBarnacle extends RenderLiving
{
	protected static final ResourceLocation
	texture = LambdaUtilities.createLambdaDomain("textures/entities/barnacle.png"),
	tongueTexture =LambdaUtilities.createLambdaDomain("textures/entities/barnacle_tongue.png");

	public RenderBarnacle(RenderManager manager, float shadow)
	{
		super(manager, new ModelBarnacle(), shadow);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTick)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTick);
		if(entity.isEntityAlive() && entity instanceof EntityBarnacle)
		{
			EntityBarnacle barnacle = (EntityBarnacle) entity;
			Vec3d start = new Vec3d(barnacle.posX, barnacle.posY + 0.2D, barnacle.posZ);
			Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTongueTexture());
			LambdaUtilities.drawFlatQuad(start, start.addVector(0D, barnacle.tongueLength - 0.2D, 0D), 0.05D, 0xffffffff, false, partialTick);
		}
	}

	protected ResourceLocation getTongueTexture()
	{
		return tongueTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}