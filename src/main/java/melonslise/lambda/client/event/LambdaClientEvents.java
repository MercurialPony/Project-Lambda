package melonslise.lambda.client.event;

import com.google.common.collect.Lists;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.client.effect.EffectHandler;
import melonslise.lambda.client.effect.EffectTrailHornet;
import melonslise.lambda.client.effect.EffectTrailRocket;
import melonslise.lambda.client.key.LambdaKeyBindings;
import melonslise.lambda.client.renderer.color.LambdaColors;
import melonslise.lambda.client.renderer.model.ItemModelWrapper;
import melonslise.lambda.client.renderer.weapon.RenderItemModel;
import melonslise.lambda.common.block.api.ITileUsable;
import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.capability.entity.ICapabilityUsingTile;
import melonslise.lambda.common.entity.projectile.EntityHornet;
import melonslise.lambda.common.entity.projectile.EntityRocket;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.item.api.IItemReloadable;
import melonslise.lambda.common.item.api.IItemUsable;
import melonslise.lambda.common.item.api.armor.AItemSuit;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.server.MessageActivateTile;
import melonslise.lambda.common.network.message.server.ServerMessageUseEntity;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.common.sound.moving.api.LambdaMovingSound;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FOVModifier;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

// TODO Add remote checks
@Mod.EventBusSubscriber(modid = LambdaCore.ID, value = Side.CLIENT)
public class LambdaClientEvents
{
	private LambdaClientEvents() {};

	// TODO Configurable
	// TODO Dont zoom in third person
	@SubscribeEvent
	public static void onZoom(FOVModifier event)
	{
		if(LambdaUtilities.getZooming(event.getEntity()).get()) event.setFOV(event.getFOV() / 3F);
	}

	// TODO Move out
	// TODO Improve/Automate + renderer null check/check if wrapper is required
	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event)
	{
		registerRenderers
		(
				event,
				LambdaItems.weapon_pistol,
				LambdaItems.weapon_smg,
				LambdaItems.weapon_revolver,
				LambdaItems.weapon_crossbow,
				LambdaItems.weapon_tau,
				LambdaItems.weapon_gluon,
				LambdaItems.weapon_snark
				);
	}

	// TODO Rename
	// TODO Don't hardcode path
	private static void registerRenderer(ModelBakeEvent event, Item item)
	{
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		ModelResourceLocation location = LambdaItems.getModel(item, "weapon/", "inventory");
		registry.putObject(location, new ItemModelWrapper((RenderItemModel) item.getTileEntityItemStackRenderer(), registry.getObject(location)));
	}

	private static void registerRenderers(ModelBakeEvent event, Item... items)
	{
		for(Item item : items) registerRenderer(event, item);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		LambdaItems.registerModels();
	}

	@SubscribeEvent
	public static void registerColors(ColorHandlerEvent.Item event)
	{
		LambdaColors.registerColors(event);
	}

	// TODO Phase
	// TODO Make cancelable for other modders
	// TODO Change head to any part?
	// TODO Better way (Check pumpkin?)
	// TODO Phase
	@SubscribeEvent
	public static void renderOverlay(RenderGameOverlayEvent.Pre event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(LambdaUtilities.isWearingHazard(player)) ((AItemSuit) player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem()).renderDisplay(event);
	}

	// TODO All bipeds
	// TODO Linger for a bit after finishing
	@SubscribeEvent
	public static void renderLiving(RenderLivingEvent.Pre event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			ICapabilityUsingItem using = LambdaUtilities.getUsingItem(event.getEntity());
			if(using.get())
			{
				ModelBiped model = (ModelBiped) event.getRenderer().getMainModel();
				if(LambdaUtilities.getHandSide(event.getEntity(), using.getHand()) == EnumHandSide.RIGHT) model.rightArmPose = ArmPose.BOW_AND_ARROW;
				else model.leftArmPose = ArmPose.BOW_AND_ARROW;
			}
		}
	}

	// TODO Instead try to use a per entity class method.
	@SubscribeEvent
	public static void onSpawn(EntityJoinWorldEvent event)
	{
		if(!event.isCanceled() && event.getWorld().isRemote)
		{
			if(event.getEntity() instanceof EntityHornet)
			{
				EffectHandler.effects.add(new EffectTrailHornet(event.getEntity()));
			}
			else if(event.getEntity() instanceof EntityRocket)
			{
				Minecraft.getMinecraft().getSoundHandler().playSound(new LambdaMovingSound(event.getEntity(), LambdaSounds.weapon_rpg_rocket, SoundCategory.MASTER));
				EffectHandler.effects.add(new EffectTrailRocket(event.getEntity()));
			}
		}
	}

	// TODO Own event for use block and item? Or add right click event?
	// TODO Fix up code, make sure everything is properly done and allowed
	// TODO Sound volume
	// TODO Improve

	// TODO Move/remake
	private static boolean wasUse, wasAttack, wasInteract, wasReload;

	// TODO Phase?
	// TODO Move code to respective classes
	@SubscribeEvent
	public static void onTick(TickEvent.ClientTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.player != null && event.phase == Phase.START)
		{
			EntityPlayer player = mc.player;
			boolean pressed;

			pressed = LambdaKeyBindings.fire_primary.isKeyDown();
			for(EnumHand hand : EnumHand.values())
			{
				if(player.getHeldItem(hand).getItem() instanceof IItemUsable)
				{
					if(pressed && !wasAttack)
					{
						useItem(true, hand, 0);
						wasAttack = true;
						break;
					}
					else if(!pressed && wasAttack)
					{
						useItem(false, hand, 0);
						wasAttack = false;
						break;
					}
				}
			}

			pressed = LambdaKeyBindings.fire_secondary.isKeyDown();
			for(EnumHand hand : EnumHand.values())
			{
				if(player.getHeldItem(hand).getItem() instanceof IItemUsable)
				{
					if(pressed && !wasInteract)
					{
						useItem(true, hand, 1);
						wasInteract = true;
						break;
					}
					else if(!pressed && wasInteract)
					{
						useItem(false, hand, 1);
						wasInteract = false;
						break;
					}
				}
			}
			//else wasAttack = wasInteract = false;

			pressed = LambdaKeyBindings.reload.isKeyDown();
			for(EnumHand hand : EnumHand.values())
			{
				if(player.getHeldItem(hand).getItem() instanceof IItemReloadable)
				{
					if(pressed && !wasReload)
					{
						reload(hand);
						wasReload = true;
						break;
					}
					else if(!pressed && wasReload)
					{
						wasReload = false;
						break;
					}
				}
			}
			//else wasReload = false;

			pressed = LambdaKeyBindings.use.isKeyDown();
			if(pressed && !wasUse)
			{
				useFocus(true);
				wasUse = true;
			}
			else if(!pressed && wasUse)
			{
				useFocus(false);
				wasUse = false;
			}
		}
	}

	// see PlayerInteractionManager#processRightClickBlock, NetHandlerPlayServer#processTryUseItemOnBlock and PlayerControllerMP#processRightClickBlock
	// TODO Shorten
	private static void useFocus(boolean state)
	{
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		Vec3d start = player.getPositionEyes(1F);
		Vec3d end = player.getLookVec().scale(2D).add(start);
		RayTraceResult result = player.world.rayTraceBlocks(start, end, false, false, false);
		if(result != null) end = result.hitVec;
		boolean hasFocus = false;
		// TODO Predicate
		RayTraceResult result1 = LambdaUtilities.rayTraceClosestEntity(player.world, start, end, Lists.newArrayList(player), null);
		if(state && result1 != null && result1.entityHit.processInitialInteract(player, EnumHand.MAIN_HAND))
		{
			hasFocus = true;
			LambdaNetworks.network.sendToServer(new ServerMessageUseEntity(state, 0, result1.entityHit.getEntityId()));
		}
		else if(result != null && result.getBlockPos() != null)
		{
			BlockPos position = result.getBlockPos();
			IBlockState focus = player.world.getBlockState(position);
			Vec3d hit = result.hitVec.subtract((double) position.getX(), (double) position.getY(), (double) position.getZ());
			if(focus.getBlock() instanceof ITileUsable)
			{
				ICapabilityUsingTile using = LambdaUtilities.getUsingTile(player);
				if(state) using.startUsing(position, result.sideHit, hit, 0);
				else using.stopUsing(result.sideHit, hit, 0);
				hasFocus = true;
			}
			else if(state && focus.getBlock().onBlockActivated(player.world, position, focus, player, EnumHand.MAIN_HAND, result.sideHit, (float) hit.x, (float) hit.y, (float) hit.z))
			{
				hasFocus = true;
				LambdaNetworks.network.sendToServer(new MessageActivateTile(position, result.sideHit, hit));
			}
		}
		if(state && !hasFocus) player.playSound(LambdaSounds.denyselect, 1F, 1F);
	}

	private static void useItem(boolean state, EnumHand hand, int type)
	{
		ICapabilityUsingItem using = LambdaUtilities.getUsingItem(Minecraft.getMinecraft().player);
		if(state) using.startUsing(hand, type);
		else using.stopUsing(hand, type);
	}

	private static void reload(EnumHand hand)
	{
		LambdaUtilities.getReloading(Minecraft.getMinecraft().player).startReloading(hand);
	}
}