package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.projectile.EntityBullet;
import melonslise.lambda.common.item.api.AItemReloadable;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ItemRevolver extends AItemReloadable
{
	public ItemRevolver(String name, int size, Item ammo)
	{
		super(name, size, ammo);
	}

	public boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown)
	{
		if(player.isInsideOfMaterial(Material.WATER) || LambdaUtilities.getReloading(player).get() || this.getMagazine(stack) <= 0 || this.getCooldown(stack) > 0) return false;
		this.consumeMagazine(stack, ammo);
		this.setCooldown(stack, cooldown);
		EntityBullet bullet = new EntityBullet(player.world, 11F, 1);
		bullet.fire(player, hand, 4D, (float) Math.PI / 180F);
		player.world.spawnEntity(bullet);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_python_shot, SoundCategory.PLAYERS, 1F, 1F);
		return true;
	}

	@Override
	protected boolean startPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		super.startSecondaryUsing(player, hand, stack);
		return true;
	}

	@Override
	protected void primaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote) this.fire(player, hand, stack, 1, 15);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return false;
	}

	@Override
	public void onStopReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		super.onStopReloading(player, hand, stack, ticks);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_python_load, SoundCategory.PLAYERS, 1F, 1F);
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
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 24, 0);
		}
	}
}