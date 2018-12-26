package melonslise.lambda.common.capability;

import melonslise.lambda.common.capability.api.CapabilityProvider;
import melonslise.lambda.common.capability.api.CapabilityStorage;
import melonslise.lambda.common.capability.entity.CapabilityPower;
import melonslise.lambda.common.capability.entity.CapabilityReloading;
import melonslise.lambda.common.capability.entity.CapabilityRemoteCharges;
import melonslise.lambda.common.capability.entity.CapabilityUsingItem;
import melonslise.lambda.common.capability.entity.CapabilityUsingTile;
import melonslise.lambda.common.capability.entity.CapabilityZooming;
import melonslise.lambda.common.capability.entity.ICapabilityPower;
import melonslise.lambda.common.capability.entity.ICapabilityReloading;
import melonslise.lambda.common.capability.entity.ICapabilityRemoteCharges;
import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.capability.entity.ICapabilityUsingTile;
import melonslise.lambda.common.capability.entity.ICapabilityZooming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class LambdaCapabilities
{
	@CapabilityInject(ICapabilityPower.class)
	public static Capability<ICapabilityPower> power;

	@CapabilityInject(ICapabilityUsingItem.class)
	public static Capability<ICapabilityUsingItem> usingItem;

	@CapabilityInject(ICapabilityUsingTile.class)
	public static Capability<ICapabilityUsingTile> usingTile;

	@CapabilityInject(ICapabilityReloading.class)
	public static Capability<ICapabilityReloading> reloading;

	@CapabilityInject(ICapabilityZooming.class)
	public static Capability<ICapabilityZooming> zooming;

	@CapabilityInject(ICapabilityRemoteCharges.class)
	public static Capability<ICapabilityRemoteCharges> remoteCharges;

	private LambdaCapabilities() {};

	// TODO Factories
	public static void register()
	{
		CapabilityManager.INSTANCE.register(ICapabilityPower.class, new CapabilityStorage<ICapabilityPower>(), CapabilityPower.class);
		CapabilityManager.INSTANCE.register(ICapabilityUsingItem.class, new CapabilityStorage<ICapabilityUsingItem>(), CapabilityUsingItem.class);
		CapabilityManager.INSTANCE.register(ICapabilityUsingTile.class, new CapabilityStorage<ICapabilityUsingTile>(), CapabilityUsingTile.class);
		CapabilityManager.INSTANCE.register(ICapabilityReloading.class, new CapabilityStorage<ICapabilityReloading>(), CapabilityReloading.class);
		CapabilityManager.INSTANCE.register(ICapabilityZooming.class, new CapabilityStorage<ICapabilityZooming>(), CapabilityZooming.class);
		CapabilityManager.INSTANCE.register(ICapabilityRemoteCharges.class, new CapabilityStorage<ICapabilityRemoteCharges>(), CapabilityRemoteCharges.class);
	}

	public static void attach(AttachCapabilitiesEvent event)
	{
		if (event.getObject() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getObject();

			CapabilityPower capabilityPower = new CapabilityPower(player);
			event.addCapability(capabilityPower.getID(), new CapabilityProvider(LambdaCapabilities.power, capabilityPower, null));

			CapabilityUsingItem capabilityUsingItem = new CapabilityUsingItem(player);
			event.addCapability(capabilityUsingItem.getID(), new CapabilityProvider(LambdaCapabilities.usingItem, capabilityUsingItem, null));

			CapabilityUsingTile capabilityUsingTile = new CapabilityUsingTile(player);
			event.addCapability(capabilityUsingTile.getID(), new CapabilityProvider(LambdaCapabilities.usingTile, capabilityUsingTile, null));

			CapabilityReloading capabilityReloading = new CapabilityReloading(player);
			event.addCapability(capabilityReloading.getID(), new CapabilityProvider(LambdaCapabilities.reloading, capabilityReloading, null));

			CapabilityZooming capabilityZooming = new CapabilityZooming(player);
			event.addCapability(capabilityZooming.getID(), new CapabilityProvider(LambdaCapabilities.zooming, capabilityZooming, null));

			CapabilityRemoteCharges capabilityRemoteCharges = new CapabilityRemoteCharges((EntityPlayer) event.getObject());
			event.addCapability(capabilityRemoteCharges.getID(), new CapabilityProvider(LambdaCapabilities.remoteCharges, capabilityRemoteCharges, null));
		}
	}
}