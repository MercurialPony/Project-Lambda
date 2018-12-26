package melonslise.lambda.common.capability.entity;

import java.util.ArrayList;
import java.util.Iterator;

import melonslise.lambda.common.entity.EntitySatchelCharge;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

public class CapabilityRemoteCharges implements ICapabilityRemoteCharges
{
	private static final ResourceLocation id = LambdaUtilities.createLambdaDomain("remote_charges");

	protected ArrayList<EntitySatchelCharge> charges = new ArrayList<EntitySatchelCharge>();
	protected EntityPlayer player;

	public CapabilityRemoteCharges(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public ArrayList<EntitySatchelCharge> get()
	{
		return this.charges;
	}

	@Override
	public ResourceLocation getID()
	{
		return id;
	}

	@Override
	public void synchronize() {}

	private static final String keyCharges = "charges", keyID = "id";

	@Override
	public NBTTagCompound serialize(NBTTagCompound nbt, EnumFacing side)
	{
		NBTTagList list = new NBTTagList();
		Iterator<EntitySatchelCharge> iterator = this.charges.iterator();
		while(iterator.hasNext())
		{
			NBTTagCompound nbt1 = new NBTTagCompound();
			nbt1.setUniqueId(keyID, iterator.next().getUniqueID());
			list.appendTag(nbt1);;
		}
		nbt.setTag(keyCharges, list);
		return nbt;
	}

	@Override
	public void deserialize(NBTBase nbt)
	{
		NBTTagList list = ((NBTTagCompound) nbt).getTagList(keyCharges, Constants.NBT.TAG_COMPOUND);
		for(int a = 0; a < list.tagCount(); ++a)
		{
			// TODO Null check?
			Entity entity = this.player.getServer().getEntityFromUuid(list.getCompoundTagAt(a).getUniqueId(keyID));
			if(entity instanceof EntitySatchelCharge) this.charges.add((EntitySatchelCharge) entity);
		}
	}
}