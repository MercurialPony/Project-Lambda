package melonslise.lambda.common.item.armor;

import java.util.List;

import melonslise.lambda.common.capability.entity.ICapabilityPower;
import melonslise.lambda.common.item.api.armor.AItemSuit;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO Rename?
public class ItemSuitHazard extends AItemSuit
{
	public ItemSuitHazard(String name, EntityEquipmentSlot slot)
	{
		super(name, slot);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		if(slot == EntityEquipmentSlot.LEGS) return "lambda:textures/armor/hazard_2.png";
		return "lambda:textures/armor/hazard_1.png";
	}

	@Override
	public int getDisplayColor()
	{
		return 0x90ff8000; // Orange (R = 1, G = 0.5, B = 0, A = 0.5625)
	}

	@Override
	public void applyReduction(LivingHurtEvent event)
	{
		ICapabilityPower capabilityPower = LambdaUtilities.getPower(event.getEntityLiving());
		float damage = event.getAmount();
		float suitDamage =damage * 0.8F;
		float newDamage = damage * 0.2F;
		float power = (float) capabilityPower.get();
		if(suitDamage > power) newDamage += Math.abs(power - suitDamage);
		capabilityPower.consume(suitDamage);
		event.setAmount(newDamage);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(I18n.format("item.lambda.suit.hazard.flavor.name"));
	}
}