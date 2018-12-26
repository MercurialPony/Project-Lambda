package melonslise.lambda.common.capability.api;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

// TODO Finals?
public class CapabilityProvider implements ICapabilitySerializable
{
	protected Capability capability;
	protected ICapability instance;
	protected EnumFacing side;

	public CapabilityProvider(Capability capability, ICapability instance, EnumFacing side)
	{
		this.capability = capability;
		this.instance = instance;
		this.side = side;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing side)
	{
		return this.capability == capability;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing side)
	{
		return this.hasCapability(capability, side) ? (T) this.instance : null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.capability.getStorage().writeNBT(this.capability, this.instance, this.side);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.capability.getStorage().readNBT(this.capability, this.instance, this.side, nbt);
	}
}