package melonslise.lambda.common.network.message.api;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class AMessageUse implements IMessage
{
	private boolean state;
	private int type;

	public AMessageUse() {}

	public AMessageUse(boolean state, int type)
	{
		this.state = state;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.state = buffer.readBoolean();
		this.type = (int) buffer.readShort();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(this.state);
		buffer.writeShort((short) this.type);
	}

	public boolean getState()
	{
		return this.state;
	}

	public int getType()
	{
		return this.type;
	}
}