package melonslise.lambda.common.sound.api;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

// TODO Use string instead like with items and blocks?
public class SoundEventNamed extends SoundEvent
{
	public SoundEventNamed(ResourceLocation name)
	{
		super(name);
		this.setRegistryName(name);
	}
}