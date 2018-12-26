package melonslise.lambda.common.tileentity;

import melonslise.lambda.common.tileentity.charger.TileEntityChargerHealth;
import melonslise.lambda.common.tileentity.charger.TileEntityChargerPower;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LambdaTileEntities
{
	private LambdaTileEntities() {};

	public static void register()
	{
		GameRegistry.registerTileEntity(TileEntityChargerHealth.class, LambdaUtilities.createLambdaDomain("charger.health"));
		GameRegistry.registerTileEntity(TileEntityChargerPower.class, LambdaUtilities.createLambdaDomain("charger.power"));
		GameRegistry.registerTileEntity(TileEntityLaserTripmine.class, LambdaUtilities.createLambdaDomain("laser_tripmine"));
	}
}