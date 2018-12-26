package melonslise.lambda.client.renderer;

import melonslise.lambda.client.model.ModelSentry;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSentry extends RenderLiving
{
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/entities/sentry.png");

	public RenderSentry(RenderManager manager, float shadow)
	{
		super(manager, new ModelSentry(), shadow);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}