package melonslise.lambda.common.sound;

import net.minecraft.block.SoundType;
import net.minecraft.init.SoundEvents;

// TODO Move?
public class LambdaSoundTypes
{
	// Vanilla scales pitch by 0.8 for some reason
	public static final SoundType TRIPMINE = new SoundType(1F, 1.3F, SoundEvents.BLOCK_METAL_BREAK, SoundEvents.BLOCK_METAL_STEP, LambdaSounds.weapon_tripmine_deploy, SoundEvents.BLOCK_METAL_HIT, SoundEvents.BLOCK_METAL_FALL);

	private LambdaSoundTypes() {};
}