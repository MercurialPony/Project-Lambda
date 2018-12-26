package melonslise.lambda.client.effect;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.client.effect.api.EffectTrail;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EffectTrailRocket extends EffectTrail
{
	protected static final ResourceLocation texture = new ResourceLocation(LambdaCore.ID, "textures/entities/trail_rocket.png");

	public EffectTrailRocket(Entity rocket)
	{
		super(rocket, 0.007F, 1D);
	}

	@Override
	protected ResourceLocation getTexture()
	{
		return texture;
	}
}