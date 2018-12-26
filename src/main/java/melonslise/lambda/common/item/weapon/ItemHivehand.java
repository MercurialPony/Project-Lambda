package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.projectile.EntityHornet;
import melonslise.lambda.common.item.api.AItemLoadable;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

// TODO Don't shoot under water?
public class ItemHivehand extends AItemLoadable
{
	public ItemHivehand(String name, int size)
	{
		super(name, size);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		super.onUpdate(stack, world, entity, slot, selected);
		if(!world.isRemote && entity.ticksExisted % 10 == 0) this.restoreMagazine(stack, 1); // TODO
	}

	// TODO Sound category
	// TODO Inaccuracy?
	protected boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown, boolean homing)
	{
		if(LambdaUtilities.getReloading(player).get() || this.getMagazine(stack) <= 0 || this.getCooldown(stack) > 0) return false;
		this.consumeMagazine(stack, ammo);
		this.setCooldown(stack, cooldown);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_hivehand_shot, SoundCategory.PLAYERS, 1F, 1F);
		EntityHornet hornet = new EntityHornet(player.world, homing);
		hornet.fire(player, hand, 0.8D, 0F);
		player.world.spawnEntity(hornet);
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
		if(!player.world.isRemote) this.fire(player, hand, stack, 1, 5, true);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return true;
	}

	@Override
	protected void secondaryUsingTick(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(!player.world.isRemote) this.fire(player, hand, stack, 1, 2, false);
	}

	@Override
	protected SoundEvent getDryFireSound()
	{
		return null;
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		super.renderDisplay(event, color, stack, hand);
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 0, 48);
		}
	}
}