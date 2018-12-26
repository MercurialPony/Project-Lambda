package melonslise.lambda.common.item.api;

import java.util.List;

import javax.annotation.Nullable;

import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AItemLoadable extends AItemUsable implements ISuitDisplayProvider
{
	protected final int size;

	public AItemLoadable(String name, int size)
	{
		super(name);
		this.size = size;
	}

	@Override
	protected boolean startPrimaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if(this.getMagazine(stack) <= 0) player.world.playSound(null, player.posX, player.posY, player.posZ, this.getDryFireSound(), SoundCategory.PLAYERS, 1F, 1F);
		return false;
	}

	@Override
	protected boolean startSecondaryUsing(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if(this.getMagazine(stack) <= 0) player.world.playSound(null, player.posX, player.posY, player.posZ, this.getDryFireSound(), SoundCategory.PLAYERS, 1F, 1F);
		return false;
	}

	public static final String keyMagazine = "magazine";

	protected int getMagazine(ItemStack stack)
	{
		return LambdaUtilities.getTag(stack).getInteger(keyMagazine);
	}

	protected void setMagazine(ItemStack stack, int amount)
	{
		LambdaUtilities.getTag(stack).setInteger(keyMagazine, MathHelper.clamp(amount, 0, this.size));
	}

	protected boolean consumeMagazine(ItemStack stack, int amount)
	{
		int magazine = this.getMagazine(stack);
		this.setMagazine(stack, magazine - amount);
		return magazine >= amount;
	}

	protected void restoreMagazine(ItemStack stack, int amount)
	{
		this.setMagazine(stack, this.getMagazine(stack) + amount);
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.HOTBAR) SuitRenderUtilities.renderAmmo(event.getResolution(), color, Integer.toString(this.getMagazine(stack)), 0);
	}

	// TODO More info
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(this.getMagazine(stack) + " / " + this.size);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> stacks)
	{
		if(this.isInCreativeTab(tab))
		{
			ItemStack stack = new ItemStack(this);
			this.setMagazine(stack, this.size);
			stacks.add(stack);
		}
	}

	protected SoundEvent getDryFireSound()
	{
		return LambdaSounds.weapon_dryfire;
	}
}