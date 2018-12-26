package melonslise.lambda.client.key;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class LambdaKeyBindings
{
	private static String category = LambdaUtilities.prefixLambda("key.category.name");
	public static KeyBinding
	use = new KeyBinding(LambdaUtilities.prefixLambda("key.use.name"), 33, category),
	reload = new KeyBinding(LambdaUtilities.prefixLambda("key.reload.name"), 19, category),
	fire_primary = new KeyBinding(LambdaUtilities.prefixLambda("key.fire.primary.name"), new OnUseContext(), -100, category),
	fire_secondary = new KeyBinding(LambdaUtilities.prefixLambda("key.fire.secondary.name"), new OnUseContext(), -99, category);

	private LambdaKeyBindings() {};

	public static void register()
	{
		registerKeys(use, reload, fire_primary, fire_secondary);
		GameSettings gs = Minecraft.getMinecraft().gameSettings;
		gs.keyBindAttack.setKeyConflictContext(new VanillaResolverContext(gs.keyBindAttack.getKeyConflictContext()));
		gs.keyBindUseItem.setKeyConflictContext(new VanillaResolverContext(gs.keyBindUseItem.getKeyConflictContext()));
	}

	private static void registerKeys(KeyBinding... keys)
	{
		for(KeyBinding key : keys) ClientRegistry.registerKeyBinding(key);
	}
}