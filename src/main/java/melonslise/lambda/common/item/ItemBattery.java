package melonslise.lambda.common.item;

import melonslise.lambda.common.entity.collectible.CollectibleBattery;
import melonslise.lambda.common.item.api.LambdaItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBattery extends LambdaItem
{
	public ItemBattery(String name)
	{
		super(name);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos position, EnumHand hand, EnumFacing facing, float x, float y, float z)
	{
		if(world.isRemote) return EnumActionResult.SUCCESS;
		BlockPos offset = position.offset(facing);
		ItemStack stack = player.getHeldItem(hand);
		if(!player.canPlayerEdit(offset, facing, stack)) return EnumActionResult.FAIL;
		if(!player.isCreative()) stack.shrink(1);
		CollectibleBattery battery = new CollectibleBattery(world);
		battery.setLocationAndAngles((double) offset.getX() + 0.5D, (double) offset.getY(), (double) offset.getZ() + 0.5D, player.rotationYaw + 180F, 0F);
		world.spawnEntity(battery);
		return EnumActionResult.SUCCESS;
	}
}