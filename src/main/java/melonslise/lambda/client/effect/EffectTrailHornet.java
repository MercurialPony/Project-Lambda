package melonslise.lambda.client.effect;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.client.effect.api.EffectTrail;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EffectTrailHornet extends EffectTrail
{
	protected static final ResourceLocation texture = new ResourceLocation(LambdaCore.ID, "textures/entities/trail_hornet.png");

	public EffectTrailHornet(Entity hornet)
	{
		super(hornet, 0.04F, 0.1D);
	}

	@Override
	protected ResourceLocation getTexture()
	{
		return texture;
	}
}