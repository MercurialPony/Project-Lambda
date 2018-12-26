package melonslise.lambda.common.loot;

import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LambdaLoot
{
	public static final ResourceLocation ENTITIES_BARNACLE = LambdaUtilities.createLambdaDomain("entities/barnacle");

	private LambdaLoot() {};

	public static void register()
	{
		LootTableList.register(ENTITIES_BARNACLE);
	}
}