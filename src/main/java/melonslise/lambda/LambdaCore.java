package melonslise.lambda;

import melonslise.lambda.client.key.LambdaKeyBindings;
import melonslise.lambda.common.capability.LambdaCapabilities;
import melonslise.lambda.common.loot.LambdaLoot;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.proxy.ACommonProxy;
import melonslise.lambda.common.tileentity.LambdaTileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LambdaCore.ID, name = LambdaCore.NAME, version = LambdaCore.VERSION, acceptedMinecraftVersions = LambdaCore.GAMEVERSIONS)
public class LambdaCore
{
	public static final String ID = "lambda", NAME = "Project Lambda", VERSION = "0.0.2", GAMEVERSIONS = "1.12.2";

	@SidedProxy(modId = ID, clientSide = "melonslise.lambda.client.proxy.ClientProxy", serverSide = "melonslise.lambda.server.proxy.ServerProxy")
	public static ACommonProxy proxy;

	@Mod.EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		LambdaLoot.register();
		LambdaCapabilities.register();
		LambdaTileEntities.register();
		LambdaNetworks.registerMessages();
		this.proxy.registerRenderers();
		this.proxy.registerKeyBindings();
	}

	@Mod.EventHandler
	public void postInitialization(FMLPostInitializationEvent event)
	{
		this.proxy.attachLayers();
	}
}