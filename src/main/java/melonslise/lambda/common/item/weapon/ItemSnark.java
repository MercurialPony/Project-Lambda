package melonslise.lambda.common.item.weapon;

import melonslise.lambda.common.entity.alien.EntitySnark;
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

public class ItemSnark extends LambdaItem implements ISuitDisplayProvider
{
	public ItemSnark(String name)
	{
		super(name);
	}

	// TODO Deploy sound
	// TODO Fix stuck delay
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		if(!player.isCreative()) stack.shrink(1);
		EntitySnark snark = new EntitySnark(world);
		snark.fire(player, 1D);
		world.spawnEntity(snark);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
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