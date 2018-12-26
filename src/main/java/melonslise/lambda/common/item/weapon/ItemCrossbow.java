package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.projectile.EntityBolt;
import melonslise.lambda.common.item.api.AItemReloadable;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ItemCrossbow extends AItemReloadable
{
	public ItemCrossbow(String name, int size, Item ammo)
	{
		super(name, size, ammo);
	}

	// TODO Inaccuracy?
	public boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown)
	{
		if(LambdaUtilities.getReloading(player).get() || this.getMagazine(stack) <= 0 || this.getCooldown(stack) > 0) return false;
		this.consumeMagazine(stack, ammo);
		this.setCooldown(stack, cooldown);
		EntityBolt bolt = new EntityBolt(player.world, 14F, 1);
		bolt.fire(player, hand, 2.5D, 0F);
		player.world.spawnEntity(bolt);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_crossbow_shot, SoundCategory.PLAYERS, 1F, 1F);
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		super.onUpdate(stack, world, entity, slot, selected);
		if(world.isRemote || !(entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) entity;
		if(!selected && player.getHeldItemOffhand() != stack || LambdaUtilities.getReloading(player).get() || this.getCooldown(stack) != 10) return;
		entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, LambdaSounds.weapon_crossbow_load, SoundCategory.PLAYERS, 1F, 1F);
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
		if(!player.world.isRemote) this.fire(player, hand, stack, 1, 15);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		LambdaUtilities.getZooming(player).toggleZooming(hand);
		return false;
	}

	@Override
	public int getReloadTime(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return 15;
	}

	@Override
	public int onStartReloading(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		LambdaUtilities.getZooming(player).stopZooming(hand);
		return super.onStartReloading(player, hand, stack);
	}

	@Override
	public void onUpdateReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote && ticks == 10) player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_crossbow_load, SoundCategory.PLAYERS, 1F, 1F);
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		super.renderDisplay(event, color, stack, hand);
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 0, 24);
		}
	}
}