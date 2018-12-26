package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.projectile.EntityRocket;
import melonslise.lambda.common.item.api.AItemUsable;
import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ItemRPG extends AItemUsable implements ISuitDisplayProvider
{
	protected Item ammo;

	public ItemRPG(String name, Item ammo)
	{
		super(name);
		this.ammo = ammo;
	}

	// TODO LOUDER ... yay
	public boolean fire(EntityPlayer player, EnumHand hand, ItemStack stack, int ammo, int cooldown)
	{
		if(player.isInsideOfMaterial(Material.WATER) || this.getCooldown(stack) > 0 || !LambdaUtilities.consumeItem(player, this.ammo, ammo)) return false;
		this.setCooldown(stack, cooldown);
		EntityRocket rocket = new EntityRocket(player.world, this.getMode(stack));
		rocket.fire(player, hand, 2D, 0F);
		player.world.spawnEntity(rocket);
		player.world.playSound(null, player.posX, player.posY, player.posZ, LambdaSounds.weapon_rpg_shot, SoundCategory.PLAYERS, 1F, 1F);
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
		if(!player.world.isRemote) this.fire(player, hand, stack, 1, 40);
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if(!player.world.isRemote) this.toggleMode(player.getHeldItem(hand));
		return false;
	}

	public Item getAmmo()
	{
		return this.ammo;
	}

	public static final String keyMode = "mode";

	protected boolean getMode(ItemStack stack)
	{
		return LambdaUtilities.getTag(stack).getBoolean(keyMode);
	}

	protected void setMode(ItemStack stack, boolean mode)
	{
		LambdaUtilities.getTag(stack).setBoolean(keyMode, mode);
	}

	protected void toggleMode(ItemStack stack)
	{
		NBTTagCompound nbt = LambdaUtilities.getTag(stack);
		nbt.setBoolean(keyMode, !nbt.getBoolean(keyMode));
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.CROSSHAIRS)
		{
			event.setCanceled(true);
			SuitRenderUtilities.renderCrosshair(event.getResolution(), color, 24, 24);
		}
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this.ammo));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 0);
		}
	}
}