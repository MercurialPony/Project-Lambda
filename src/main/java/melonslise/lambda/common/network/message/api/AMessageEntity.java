package melonslise.lambda.common.network.message.api;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class AMessageEntity implements IMessage
{
	private int id;

	public AMessageEntity() {}

	public AMessageEntity(Entity entity)
	{
		this.id = entity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.id);
	}

	public int getEntityID()
	{
		return this.id;
	}

	public Entity getEntity(World world)
	{
		return world.getEntityByID(this.id);
	}
}