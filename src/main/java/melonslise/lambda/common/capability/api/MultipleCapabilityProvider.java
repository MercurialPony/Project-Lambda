package melonslise.lambda.common.capability.api;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

// TODO Rename?
//TODO Rework? + rework nbt
// TODO Finals?
public class MultipleCapabilityProvider implements ICapabilityProvider
{
	protected ICapabilityProvider[] providers;

	public MultipleCapabilityProvider(ICapabilityProvider... providers)
	{
		this.providers = providers;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing side)
	{
		for(ICapabilityProvider provider : this.providers)
		{
			return provider.hasCapability(capability, side);
		}
		return false;
	}

	// TODO Trinary continue?
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing side)
	{
		for(ICapabilityProvider provider : this.providers)
		{
			if(provider.hasCapability(capability, side))
			{
				return provider.getCapability(capability, side);
			}
		}
		return null;
	}
}