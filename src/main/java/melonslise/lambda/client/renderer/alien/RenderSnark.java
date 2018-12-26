package melonslise.lambda.client.renderer.alien;

import melonslise.lambda.client.model.alien.ModelSnark;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSnark extends RenderLiving
{
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/snark.png");

	public RenderSnark(RenderManager manager, float shadow)
	{
		super(manager, new ModelSnark(), shadow);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}