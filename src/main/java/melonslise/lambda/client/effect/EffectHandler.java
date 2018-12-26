package melonslise.lambda.client.effect;

import java.util.ArrayList;
import java.util.Iterator;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.client.effect.api.Effect;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = LambdaCore.ID, value = Side.CLIENT)
public class EffectHandler
{
	private EffectHandler() {};

	public static ArrayList<Effect> effects = new ArrayList<Effect>();

	@SubscribeEvent
	public static void render(RenderWorldLastEvent event)
	{
		Iterator<Effect> iterator = effects.iterator();
		while(iterator.hasNext())
		{
			Effect effect = iterator.next();
			effect.render(event.getPartialTicks());
		}
	}

	@SubscribeEvent
	public static void tick(ClientTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.world != null)
		{
			if(event.phase == Phase.END && !Minecraft.getMinecraft().isGamePaused())
			{
				Iterator<Effect> iterator = effects.iterator();
				while(iterator.hasNext())
				{
					Effect effect = iterator.next();
					if(effect.isExpired()) iterator.remove();
					else effect.update();
				}
			}
		}
		else if(!effects.isEmpty()) effects.clear();
	}
}