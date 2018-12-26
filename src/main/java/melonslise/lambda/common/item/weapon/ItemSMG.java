package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.projectile.EntityBullet;
import melonslise.lambda.common.entity.projectile.EntityImpactGrenade;
import melonslise.lambda.common.item.api.AItemReloadable;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

// TODO Improve grenade system
public class ItemSMG extends AItemReloadable
{
	protected final Item grenadeAmmo;

	public ItemSMG(String name, int size, Item ammo, Item grenadeAmmo)
	{
		super(name, size, ammo);
		this.grenadeAmmo = grenadeAmmo;
	}

	public boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown)
	{
		if(player.isInsideOfMaterial(Material.WATER) || LambdaUtilities.getReloading(player).get() || this.getMagazine(stack) <= 0 || this.getCooldown(stack) > 0) return false;
		this.consumeMagazine(stack, ammo);
		this.setCooldown(stack, cooldown);
		EntityBullet bullet = new EntityBullet(player.world, 2F, 2);
		bullet.fire(player, hand, 4D, (float) Math.PI / 60F);
		player.world.spawnEntity(bullet);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_mp_shot, SoundCategory.PLAYERS, 1F, 1F);
		return true;
	}

	public boolean fireGrenade(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown)
	{
		if(player.isInsideOfMaterial(Material.WATER) || LambdaUtilities.getReloading(player).get() || !LambdaUtilities.consumeItem(player, this.grenadeAmmo, ammo) || !this.resetCooldown(stack, cooldown)) return false;
		EntityImpactGrenade grenade = new EntityImpactGrenade(player.world);
		grenade.fire(player, hand, 1D, 0F);
		player.world.spawnEntity(grenade);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_mp_grenade, SoundCategory.PLAYERS, 1F, 1F);
		return true;
	}

	@Override
	protected boolean startPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		super.startPrimaryUsing(player, hand, stack);
		return true;
	}

	@Override
	protected void primaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote) this.fire(player, hand, stack, 1, 2);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if(!LambdaUtilities.hasItem(player, this.grenadeAmmo)) player.world.playSound(null, player.posX, player.posY, player.posZ, this.getDryFireSound(), SoundCategory.PLAYERS, 1F, 1F);
		return true;
	}

	@Override
	protected void secondaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote) this.fireGrenade(player, hand, stack, 1, 20);
	}

	public Item getGrenadeAmmo()
	{
		return this.ammo;
	}

	@Override
	public int onStartReloading(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		int time = super.onStartReloading(player, hand, stack);
		if(time > 0) player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_mp_unload, SoundCategory.PLAYERS, 1F, 1F);
		return time;
	}

	@Override
	public void onStopReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		super.onStopReloading(player, hand, stack, ticks);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_mp_load, SoundCategory.PLAYERS, 1F, 1F);
	}

	@Override
	public int getReloadTime(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return 15;
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		super.renderDisplay(event, color, stack, hand);
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 48, 0);
		}
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this.grenadeAmmo));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 1);
		}
	}
}