package melonslise.lambda.common.item.weapon;

import com.google.common.collect.Lists;

import melonslise.lambda.common.item.api.AItemUsable;
import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageGluonBeam;
import melonslise.lambda.common.network.message.client.MessageSound;
import melonslise.lambda.common.network.message.client.MessageSound.ESound;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ItemGluon extends AItemUsable implements ISuitDisplayProvider
{
	protected Item ammo;

	public ItemGluon(String name, Item ammo)
	{
		super(name);
		this.ammo = ammo;
	}

	// TODO Configurable range
	protected boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo)
	{
		if(player.isInsideOfMaterial(Material.WATER) || this.getCooldown(stack) > 0 || !LambdaUtilities.consumeItem(player, this.ammo, ammo)) return false;
		//Vec3d start = LambdaUtilities.getHeldOffset(player, hand, new Vec3d(-0.4F, -0.25F, 1F)).addVector(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
		Vec3d start = new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
		Vec3d end = player.getLookVec().scale(64D).add(start);
		RayTraceResult result = player.world.rayTraceBlocks(start, end, false, true, false);
		if(result != null) end = result.hitVec;
		RayTraceResult result1 = LambdaUtilities.rayTraceClosestEntity(player.world, start, end, Lists.newArrayList(player), LambdaSelectors.PROJECTILE_TARGETS);
		if(result1 != null && result1.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), 4F)) result1.entityHit.hurtResistantTime = 0;
		return true;
	}

	@Override
	public boolean continueUsing(EntityPlayer player, EnumHand hand, ItemStack oldStack, ItemStack newStack, int ticks, int type)
	{
		return super.continueUsing(player, hand, oldStack, newStack, ticks, type) && !player.isInsideOfMaterial(Material.WATER) && this.getCooldown(oldStack) <= 0 && LambdaUtilities.hasItem(player, this.ammo);
	}

	@Override
	protected boolean startPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if(player.isInsideOfMaterial(Material.WATER) || this.getCooldown(stack) > 0 || !LambdaUtilities.hasItem(player, this.ammo)) return false;
		if(player.world.isRemote) return true;
		// TODO Merge into one packet
		LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageSound(player.getEntityId(), ESound.GLUON_CHARGE));
		LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageGluonBeam(player.getEntityId()));
		return true;
	}

	@Override
	protected void primaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote && ticks % 2 == 0 && this.fire(player, hand, stack, 1) && ticks == 78) LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageSound(player.getEntityId(), ESound.GLUON_BEAM));
	}

	@Override
	protected void stopPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(player.world.isRemote) return;
		this.setCooldown(stack, 10);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_gluon_discharge, SoundCategory.PLAYERS, 1F, 1F);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return false;
	}

	public Item getAmmo()
	{
		return this.ammo;
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 72, 24);
		}
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this.ammo));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 0);
		}
	}
}