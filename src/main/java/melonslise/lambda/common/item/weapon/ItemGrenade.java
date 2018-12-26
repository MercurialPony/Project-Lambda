package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.EntityGrenade;
import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.common.item.api.LambdaItem;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

// TODO usable
public class ItemGrenade extends LambdaItem implements ISuitDisplayProvider
{
	public ItemGrenade(String name)
	{
		super(name);
	}

	// TODO set proper duration
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int ticks)
	{
		if(entity.world.isRemote) return;
		if(entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode) stack.shrink(1);
		EntityGrenade grenade = new EntityGrenade(entity.world , 100 - MathHelper.clamp(this.getMaxItemUseDuration(stack) - ticks, 0, 100));
		grenade.fire(entity, 1D);
		entity.world.spawnEntity(grenade);
	}

	@Override
	public void renderDisplay(Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 0);
		}
	}
}