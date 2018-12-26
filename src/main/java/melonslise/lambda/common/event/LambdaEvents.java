
package melonslise.lambda.common.event;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.common.block.LambdaBlocks;
import melonslise.lambda.common.capability.LambdaCapabilities;
import melonslise.lambda.common.entity.LambdaEntities;
import melonslise.lambda.common.entity.alien.EntityBarnacle;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.item.api.armor.AItemSuit;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod.EventBusSubscriber(modid = LambdaCore.ID)
public class LambdaEvents
{
	private LambdaEvents() {};

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		LambdaBlocks.register(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		LambdaItems.register(event);
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
		LambdaEntities.register(event);
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
	{
		LambdaSounds.register(event);
	}

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent event)
	{
		LambdaCapabilities.attach(event);
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerChangedDimensionEvent event)
	{
		LambdaUtilities.getPower(event.player).synchronize();
	}

	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event)
	{
		LambdaUtilities.getPower(event.player).synchronize();
	}

	// TODO Allow dismount on certain conditions
	@SubscribeEvent
	public static void onMount(EntityMountEvent event)
	{
		Entity mount = event.getEntityBeingMounted();
		if(event.isDismounting() && mount instanceof EntityBarnacle && mount.isEntityAlive() && event.getEntityMounting().isEntityAlive()) event.setCanceled(true);;
	}

	//  TODO Change chest to any piece
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if(entity instanceof EntityPlayer && !event.getSource().isUnblockable() && LambdaUtilities.isWearingHazard(entity)) ((AItemSuit) entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem()).applyReduction(event);
	}

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == Phase.END)
		{
			LambdaUtilities.getUsingItem(event.player).updateUsing();
			LambdaUtilities.getUsingTile(event.player).updateUsing();
			LambdaUtilities.getReloading(event.player).updateReloading();
			LambdaUtilities.getZooming(event.player).updateZooming();
		}
	}
}