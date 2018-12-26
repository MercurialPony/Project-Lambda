package melonslise.lambda.client.renderer.weapon;

import melonslise.lambda.common.item.LambdaItems;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

// TODO Remove?
public class RenderGrenade extends RenderSnowball
{
	public RenderGrenade(RenderManager manager, RenderItem renderer)
	{
		super(manager, LambdaItems.weapon_grenade, renderer);
	}
}