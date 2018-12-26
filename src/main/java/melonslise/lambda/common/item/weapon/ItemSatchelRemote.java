package melonslise.lambda.common.item.weapon;

import java.util.Iterator;

import melonslise.lambda.common.entity.EntitySatchelCharge;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.common.item.api.LambdaItem;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemSatchelRemote extends LambdaItem implements ISuitDisplayProvider
{
	public ItemSatchelRemote(String name)
	{
		super(name);
		this.maxStackSize = 1;
	}

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(player.world.isRemote) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		Iterator<EntitySatchelCharge> iterator = LambdaUtilities.getRemoteCharges(player).get().iterator();
		while(iterator.hasNext())
		{
			EntitySatchelCharge charge = iterator.next();
			if(charge.isEntityAlive()) charge.explode();
			iterator.remove();
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void renderDisplay(Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), LambdaItems.weapon_satchel_charge));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(total), 0);
		}
	}
}