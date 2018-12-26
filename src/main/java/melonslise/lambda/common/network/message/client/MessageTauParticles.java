package melonslise.lambda.common.network.message.client;

import java.util.concurrent.ThreadLocalRandom;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.client.particle.ParticleTauGlow;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTauParticles implements IMessage
{
	private Vec3d point;
	private Vec3i normal;
	private int charge;

	public MessageTauParticles() {};

	public MessageTauParticles(Vec3d point, Vec3i normal, int charge)
	{
		this.point = point;
		this.normal = normal;
		this.charge = charge;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.point = LambdaUtilities.readFloatVector(buffer);
		this.normal = LambdaUtilities.readShortVector(buffer);
		this.charge = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		LambdaUtilities.writeFloatVector(buffer, this.point);
		LambdaUtilities.writeShortVector(buffer, this.normal);
		buffer.writeInt(this.charge);
	}



	// TODO More spread?
	// TODO params configurable?
	// TODO Move spread away from here
	public static class Handler implements IMessageHandler<MessageTauParticles, IMessage>
	{
		@Override
		public IMessage onMessage(MessageTauParticles message, MessageContext context)
		{
			Minecraft mc = Minecraft.getMinecraft();
			Runnable action = new Runnable()
			{
				@Override
				public void run()
				{
					ThreadLocalRandom random = ThreadLocalRandom.current();
					if(message.charge <= 1)
					{
						// TODO Amount
						for(int a = random.nextInt(3, 7); a > 0; --a)
						{
							Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauGlow(mc.world, message.point, new Vec3d(message.normal).scale(0.3D).addVector(random.nextDouble(-0.1D, 0.1D), random.nextDouble(-0.1D, 0.1D), random.nextDouble(-0.1D, 0.1D)), 0.2D, 0.01F + random.nextFloat() * 0.01F, 0.05F));
						}
					}
					else
					{
						float alpha = 0.09F + (float) message.charge * 0.07F;
						ParticleTauGlow particle = new ParticleTauGlow(mc.world, message.point, Vec3d.ZERO, 0.9D, alpha / 200F + random.nextFloat() * 0.002F, 0F);
						particle.setAlphaF(alpha);
						Minecraft.getMinecraft().effectRenderer.addEffect(particle);
					}
				}
			};
			mc.addScheduledTask(action);
			return null;
		}
	}
}