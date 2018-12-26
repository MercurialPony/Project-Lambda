package melonslise.lambda.common.block;

import melonslise.lambda.common.block.charger.BlockChargerHealth;
import melonslise.lambda.common.block.charger.BlockChargerPower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;

public class LambdaBlocks
{
	public static Block
	charger_health = new BlockChargerHealth("charger.health", Material.IRON),
	charger_power = new BlockChargerPower("charger.power", Material.IRON),
	weapon_tripmine = new BlockLaserTripmine("weapon.tripmine", Material.IRON);

	private LambdaBlocks() {};

	public static void register(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll
		(
			charger_health,
			charger_power,
			weapon_tripmine
		);
	}
}