package melonslise.lambda.client.key;

import melonslise.lambda.common.item.api.IItemUsable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.settings.IKeyConflictContext;

/*
 * Credit to Gigaherz
 */
public class OnUseContext implements IKeyConflictContext
{
	@Override
	public boolean isActive()
	{
		for(EnumHand hand : EnumHand.values()) if(Minecraft.getMinecraft().player.getHeldItem(hand).getItem() instanceof IItemUsable) return true;
		return false;
	}

	@Override
	public boolean conflicts(IKeyConflictContext other)
	{
		return other != null && (other instanceof VanillaResolverContext);
	}
}