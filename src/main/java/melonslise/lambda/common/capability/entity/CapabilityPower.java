package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessagePower;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

// TODO Cap
public class CapabilityPower implements ICapabilityPower
{
	private static final ResourceLocation id = LambdaUtilities.createLambdaDomain("power");

	protected EntityPlayer player;

	private float power;

	public CapabilityPower(EntityPlayer player)
	{
		this(player, 0);
	}

	public CapabilityPower(EntityPlayer player, int power)
	{
		this.player = player;
		this.power = power;
	}

	@Override
	public ResourceLocation getID()
	{
		return this.id;
	}

	@Override
	public float get()
	{
		return this.power;
	}

	// TODO CLamp and improev
	@Override
	public boolean set(float power)
	{
		if(power < 0F)
		{
			this.power = 0F;
		}
		else if(power > 20F)
		{
			this.power = 20;
		}
		else
		{
			this.power = power;
			this.synchronize();
			return true;
		}
		this.synchronize();
		return false;
	}

	@Override
	public boolean restore(float power)
	{
		if(this.power != 20F)
		{
			this.set(this.power + power);
			return true;
		}
		return false;
	}

	@Override
	public boolean consume(float power)
	{
		if(this.power != 0F && this.power - power >= 0)
		{
			this.set(this.power - power);
			return true;
		}
		return false;
	}

	private static final String keyPower = "power";

	@Override
	public NBTTagCompound serialize(NBTTagCompound nbt, EnumFacing side)
	{
		nbt.setFloat(keyPower, this.power);
		return nbt;
	}

	@Override
	public void deserialize(NBTBase nbt)
	{
		this.power = ((NBTTagCompound) nbt).getFloat(keyPower);
	}

	//TODO Is this check ok?
	@Override
	public void synchronize()
	{
		if(this.player instanceof EntityPlayerMP)
		{
			LambdaNetworks.network.sendTo(new MessagePower(this.power), (EntityPlayerMP) this.player);
		}
	}
}