package melonslise.lambda.common.network.message.client;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.client.effect.EffectHandler;
import melonslise.lambda.client.effect.EffectTauBeam;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTauBeam implements IMessage
{
	private int source;
	private Vec3d start, end;
	private boolean charged;

	public MessageTauBeam() {};

	public MessageTauBeam(EntityPlayer player, Vec3d start, Vec3d end, boolean charged)
	{
		this.source = player != null ? player.getEntityId() : -1;
		this.start = start;
		this.end = end;
		this.charged = charged;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.source = buffer.readInt();
		this.start = LambdaUtilities.readFloatVector(buffer);
		this.end = LambdaUtilities.readFloatVector(buffer);
		this.charged = buffer.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(this.source);
		LambdaUtilities.writeFloatVector(buffer, this.start);
		LambdaUtilities.writeFloatVector(buffer, this.end);
		buffer.writeBoolean(this.charged);
	}



	// TODO Pass id instead of entity?
	public static class Handler implements IMessageHandler<MessageTauBeam, IMessage>
	{
		@Override
		public IMessage onMessage(MessageTauBeam message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					EffectHandler.effects.add(new EffectTauBeam(1, mc.world.getEntityByID(message.source), message.start, message.end, message.charged));
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}
}