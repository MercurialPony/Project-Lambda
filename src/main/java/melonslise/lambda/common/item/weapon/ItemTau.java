package melonslise.lambda.common.item.weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import melonslise.lambda.common.item.api.AItemUsable;
import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageSound;
import melonslise.lambda.common.network.message.client.MessageSound.ESound;
import melonslise.lambda.common.network.message.client.MessageTauBeam;
import melonslise.lambda.common.network.message.client.MessageTauParticles;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO Fix flying
// TODO Wall bang
public class ItemTau extends AItemUsable implements ISuitDisplayProvider
{
	protected Item ammo;

	public ItemTau(String name, Item ammo)
	{
		super(name);
		this.ammo = ammo;
	}

	// TODO Range?
	protected boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int charge, int cooldown)
	{
		if(player.isInsideOfMaterial(Material.WATER) || this.getCooldown(stack) > 0 || !LambdaUtilities.consumeItem(player, this.ammo, ammo)) return false;
		this.setCooldown(stack, cooldown);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_tau_shot, SoundCategory.PLAYERS, 1F, 1F);
		//Vec3d start = LambdaUtilities.getHeldOffset(player, hand, new Vec3d(-0.3F, -0.2F, 1F)).add(position);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_tau_discharge, SoundCategory.PLAYERS, 1F, 1F);
		Vec3d look = player.getLookVec();
		Vec3d start = new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
		Vec3d end = LambdaUtilities.sampleSphereCap(look, (float) Math.PI / (180F - (float) (charge - 1) * 10F)).scale(64D).add(start);
		this.fireBeam(player, start, end, charge, true);
		if(charge <= 1) return true;
		Vec3d motion = look.scale((double) charge * -0.22D);
		player.addVelocity(motion.x, motion.y * 0.8D, motion.z);
		player.velocityChanged = true;
		return true;
	}

	// TODO Better first shot vectors/logic
	protected void fireBeam(EntityPlayer player, Vec3d start, Vec3d end, int charge, boolean first)
	{
		ArrayList<RayTraceResult> results = LambdaUtilities.rayTraceBlocks(player.world, start, end, false, true, LambdaSelectors.SHATTERABLES);
		RayTraceResult result = null;
		if(!results.isEmpty() && !LambdaSelectors.SHATTERABLES.apply(player.world.getBlockState(results.get(results.size() - 1).getBlockPos())))
		{
			result = results.get(results.size() - 1);
			results.remove(results.size() - 1);
			end = result.hitVec;
		}
		ArrayList<RayTraceResult> targets = LambdaUtilities.rayTraceEntities(player.world, start, end, Lists.newArrayList(player), LambdaSelectors.PROJECTILE_TARGETS);
		int hitsLeft = charge;
		boolean stopOnEntity = false;
		while(!targets.isEmpty() && hitsLeft > 0)
		{
			RayTraceResult result1 = LambdaUtilities.getClosestIntersectedEntity(start, targets, null);
			if(result1.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), 6F * (float) hitsLeft)) result1.entityHit.hurtResistantTime = 0;
			targets.remove(result1);
			if(--hitsLeft > 0) continue;
			end = result1.hitVec;
			stopOnEntity = true;
			// TODO Add target velocity?
		}
		LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageTauBeam(first ? player : null, start, end, charge == 1 ? false : true));
		double distance = start.squareDistanceTo(end);
		Iterator<RayTraceResult> iterator = results.iterator();
		while(iterator.hasNext())
		{
			RayTraceResult result1 = iterator.next();
			if(start.squareDistanceTo(result1.hitVec) < distance) player.world.destroyBlock(result1.getBlockPos(), false);
		}
		if(hitsLeft <= 0 || stopOnEntity || result == null) return;
		// TODO ? player.world.playSound(null, end.x, end.y, end.z, LambdaSounds.weapon_tau_discharge, SoundCategory.PLAYERS, 1F, 1F);
		Vec3i normal = result.sideHit.getDirectionVec();
		int chargeParticles = charge;
		if(this.reflectBeam(player, start, end, new Vec3d(normal), hitsLeft)) chargeParticles = 1;
		// TODO Only if solid?
		LambdaNetworks.network.sendToAllTracking(new MessageTauParticles(end, normal, chargeParticles), new TargetPoint(player.world.provider.getDimension(), end.x, end.y, end.z, 0D));
	}

	// TODO Only if solid?
	protected boolean reflectBeam(EntityPlayer player, Vec3d start, Vec3d end, Vec3d normal, int charge)
	{
		Vec3d direction = end.subtract(start).normalize();
		if(!this.canReflect(normal.dotProduct(direction))) return false;
		// TODO Try to avoid adding?
		start = end.add(normal.scale(0.01D));
		end = direction.subtract(normal.scale(2D * direction.dotProduct(normal))).scale(64D).add(start);
		this.fireBeam(player, start, end, charge, false);
		return true;
	}

	protected boolean canReflect(double angle)
	{
		// Looks like this is the cos between the normal and the vector, but we want the angle between the surface and vector  so we compare it with cos(90 - angle) = sin(angle)
		angle = Math.abs(angle);
		return angle > 0D && angle < 0.5; // from 0 to 30 degrees
	}

	@Override
	public boolean continueUsing(EntityPlayer player, EnumHand hand, ItemStack oldStack, ItemStack newStack, int ticks, int type)
	{
		if(!super.continueUsing(player, hand, oldStack, newStack, ticks, type)) return false;
		if(type == 1 && (player.isInsideOfMaterial(Material.WATER) || this.getCooldown(oldStack) > 0 || !LambdaUtilities.hasItem(player, this.getAmmo()))) return false;
		return true;
	}

	@Override
	protected boolean startPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return true;
	}

	@Override
	protected void primaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote) this.fire(player, hand, stack, 2, 1, 4);
		int charge = this.getCharge(stack);
		if(!player.isInsideOfMaterial(Material.WATER) && LambdaUtilities.hasItem(player, this.ammo)) { if(charge != 1) this.setCharge(stack, 1); }
		else if(charge != 0) this.setCharge(stack, 0);
	}

	@Override
	protected void stopPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(this.getCharge(stack) != 0) this.setCharge(stack, 0);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if(player.isInsideOfMaterial(Material.WATER) || this.getCooldown(stack) > 0 || !LambdaUtilities.hasItem(player, this.getAmmo())) return false;
		if(!player.world.isRemote) LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageSound(player.getEntityId(), ESound.TAU_CHARGE));
		this.setCharge(stack, 1);
		return true;
	}

	// TODO Fix 14
	@Override
	protected void secondaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote && ticks % 6 == 0 && ticks <= 78 && LambdaUtilities.consumeItem(player, this.getAmmo(), 1)) this.increaseCharge(stack);
	}

	@Override
	protected void stopSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote) this.fire(player, hand, stack, 0, this.getCharge(stack), 4);
		this.setCharge(stack, 0);
	}

	public Item getAmmo()
	{
		return this.ammo;
	}

	public static final String keyCharge = "charge";

	protected int getCharge(ItemStack stack)
	{
		return (int) LambdaUtilities.getTag(stack).getShort(keyCharge);
	}

	protected void setCharge(ItemStack stack, int charge)
	{
		LambdaUtilities.getTag(stack).setShort(keyCharge, charge >= 0 ? (short) charge : 0);
	}

	protected void increaseCharge(ItemStack stack)
	{
		this.setCharge(stack, this.getCharge(stack) + 1);
	}

	// Mmmm flavor
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(I18n.format("item.lambda.weapon.tau.flavor.name"));
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 48, 24);
		}
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this.ammo));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 0);
		}
	}
}