package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.projectile.EntityBullet;
import melonslise.lambda.common.item.api.AItemReloadable;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ItemShotgun extends AItemReloadable
{
	public ItemShotgun(String name, int size, Item ammo)
	{
		super(name, size, ammo);
	}

	// TODO Configurable round multiplier
	public boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown)
	{
		if(player.isInsideOfMaterial(Material.WATER) || LambdaUtilities.getReloading(player).get() || this.getMagazine(stack) <= 0 || this.getCooldown(stack) > 0) return false;
		this.consumeMagazine(stack, ammo);
		this.setCooldown(stack, cooldown);
		for(int a = 0; a < ammo * 6; ++a)
		{
			EntityBullet bullet = new EntityBullet(player.world, 2.5F, ammo * 3);
			bullet.fire(player, hand, 4D, (float) Math.PI / 45F * ((float) ammo + 1F));
			player.world.spawnEntity(bullet);
		}
		return true;
	}

	// TODO Helper?
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		super.onUpdate(stack, world, entity, slot, selected);
		if(world.isRemote || !(entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) entity;
		if(!selected && player.getHeldItemOffhand() != stack || LambdaUtilities.getReloading(entity).get() || this.getCooldown(stack) != 8) return;
		entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, LambdaSounds.weapon_spas_cock, SoundCategory.PLAYERS, 1F, 1F);
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
		if(!player.world.isRemote && this.fire(player, hand, stack, 1, 15))player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_spas_shot_singlebarrel, SoundCategory.PLAYERS, 1F, 1F);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		super.startSecondaryUsing(player, hand, stack);
		return true;
	}

	@Override
	protected void secondaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		// 30 in half life
		if(!player.world.isRemote && this.fire(player, hand, stack, 2, 25)) player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_spas_shot_doublebarrel, SoundCategory.PLAYERS, 1F, 1F);
	}

	@Override
	public void onUpdateReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(player.world.isRemote || ticks % 10 != 9) return;
		if(this.getMagazine(stack) < this.size && LambdaUtilities.consumeItem(player, this.ammo, 1))
		{
			this.restoreMagazine(stack, 1);
			player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_spas_load, SoundCategory.PLAYERS, 1F, 1F);
		}
	}

	@Override
	public void onStopReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_spas_cock, SoundCategory.PLAYERS, 1F, 1F);
	}

	@Override
	public int getReloadTime(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return (this.size - this.getMagazine(stack)) * 10 + 5;
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		super.renderDisplay(event, color, stack, hand);
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 72, 0);
		}
	}
}