package melonslise.lambda.common.item.api.armor;

import javax.annotation.Nullable;

import melonslise.lambda.common.creativetab.LambdaCreativeTabs;
import melonslise.lambda.common.item.api.ISuitDisplayProvider;
import melonslise.lambda.utility.SuitRenderUtilities;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO Try ISpecialArmor
// TODO Interface for reduction?
public abstract class AItemSuit extends ItemArmor
{
	// TODO Move?, Sound?
	public static final ArmorMaterial material_none = EnumHelper.addArmorMaterial("none", "", 0, new int[] {0, 0, 0, 0}, 0, null, 0);

	public AItemSuit(String name, EntityEquipmentSlot slot)
	{
		super(material_none, -1, slot);
		this.setRegistryName(LambdaUtilities.createLambdaDomain(name));
		this.setUnlocalizedName(LambdaUtilities.prefixLambda(name));
		this.setCreativeTab(LambdaCreativeTabs.tab);
	}

	@Override
	@Nullable
	public abstract String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type);

	// TODO Careful with SideOnly
	@SideOnly(Side.CLIENT)
	public void renderDisplay(RenderGameOverlayEvent.Pre event)
	{
		if(event.getType() == ElementType.HEALTH) event.setCanceled(true);
		else if(event.getType() == ElementType.HOTBAR) SuitRenderUtilities.renderDisplay(event.getResolution(), this.getDisplayColor());
		// TODO Less calls
		EntityPlayer player = Minecraft.getMinecraft().player;
		for(EnumHand hand : EnumHand.values())
		{
			ItemStack stack = player.getHeldItem(hand);
			if(stack.getItem() instanceof ISuitDisplayProvider)
			{
				((ISuitDisplayProvider) stack.getItem()).renderDisplay(event, this.getDisplayColor(), stack, hand);
				return;
			}
		}
	}

	public abstract int getDisplayColor();

	public abstract void applyReduction(LivingHurtEvent event);
}