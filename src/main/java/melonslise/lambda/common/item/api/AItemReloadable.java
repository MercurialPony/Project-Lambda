package melonslise.lambda.common.item.api;

import java.util.ArrayList;
import java.util.Iterator;

import melonslise.lambda.utility.LambdaUtilities;
import melonslise.lambda.utility.SuitRenderUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

// TODO Move ammo stuff to loadable?
public abstract class AItemReloadable extends AItemLoadable implements IItemReloadable
{
	protected final Item ammo;

	public AItemReloadable(String name, int size, Item ammo)
	{
		super(name, size);
		this.ammo = ammo;
	}

	public Item getAmmo()
	{
		return this.ammo;
	}

	@Override
	public int onStartReloading(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		int time = this.getReloadTime(player, hand, stack);
		if(time <= 0 || this.getMagazine(stack) >= this.size || !LambdaUtilities.hasItem(player, this.ammo)) return 0;
		// Visual effect
		player.getCooldownTracker().setCooldown(this, time);
		this.setUser(stack, player);
		return time;
	}

	@Override
	public void onUpdateReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks) {}

	// TODO
	@Override
	public void onStopReloading(EntityPlayer player, EnumHand hand, ItemStack stack, int ticks)
	{
		if(player.world.isRemote) return;
		// Visual effect
		player.getCooldownTracker().setCooldown(this, 0);
		this.setUser(stack, null);
		if(ticks == 0)
		{
			if(player.isCreative())
			{
				this.setMagazine(stack, this.size);
				return;
			}
			int magazine = this.getMagazine(stack);
			if(magazine < this.size)
			{
				ArrayList<ItemStack> ammos = LambdaUtilities.findStacks(player.inventoryContainer.getInventory(), this.ammo);
				if(!ammos.isEmpty())
				{
					Iterator<ItemStack> iterator = ammos.iterator();
					while(iterator.hasNext() && magazine < this.size)
					{
						ItemStack ammo = iterator.next();
						int refill = this.size - magazine;
						if(refill >= ammo.getCount()) refill = ammo.getCount();
						magazine += refill;
						ammo.setCount(ammo.getCount() - refill);
					}
					this.setMagazine(stack, magazine);
				}
			}
		}
	}

	public int getReloadTime(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		return 0;
	}

	@Override
	public void renderDisplay(RenderGameOverlayEvent.Pre event, int color, ItemStack stack, EnumHand hand)
	{
		if(event.getType() == ElementType.HOTBAR)
		{
			int total = LambdaUtilities.getStackTotal(LambdaUtilities.findStacks(Minecraft.getMinecraft().player.inventoryContainer.getInventory(), this.ammo));
			SuitRenderUtilities.renderAmmo(event.getResolution(), color, this.getMagazine(stack) + " | " + total, 0);
		}
	}
}