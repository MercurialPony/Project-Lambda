package melonslise.lambda.client.key;

import javax.annotation.Nullable;

import melonslise.lambda.common.item.api.IItemUsable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.settings.IKeyConflictContext;

/*
 * Credit to Gigaherz
 */
public class VanillaResolverContext implements IKeyConflictContext
{
	public final IKeyConflictContext context;

	VanillaResolverContext(@Nullable IKeyConflictContext context)
	{
		this.context = context;
	}

	@Override
	public boolean isActive()
	{
		for(EnumHand hand : EnumHand.values()) if(Minecraft.getMinecraft().player.getHeldItem(hand).getItem() instanceof IItemUsable) return false;
		return context == null || context.isActive();
	}

	@Override
	public boolean conflicts(IKeyConflictContext other)
	{
		return other != null && !(other instanceof OnUseContext) && (context == null || context.conflicts(other));
	}
}