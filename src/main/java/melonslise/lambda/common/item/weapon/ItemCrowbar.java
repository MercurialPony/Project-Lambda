package melonslise.lambda.common.item.weapon;

import com.google.common.collect.Multimap;

import melonslise.lambda.common.item.api.LambdaItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCrowbar extends LambdaItem
{
	protected float damage, speed;

	// TODO Use material?
	public ItemCrowbar(String name, float damage, float speed)
	{
		super(name);
		this.maxStackSize = 1;
		this.damage = damage;
		this.speed = speed;
	}

	public float getDamage()
	{
		return this.damage;
	}

	public float getSpeed()
	{
		return this.speed;
	}

	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos position, ItemStack stack, EntityPlayer player)
	{
		return false;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		if (state.getBlock() == Blocks.WEB) return 15F;
		else
		{
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1F : 1.5F;
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockState state)
	{
		return state.getBlock() == Blocks.WEB;
	}

	@Override
	public int getItemEnchantability()
	{
		return 9;
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot)
	{
		Multimap<String, AttributeModifier> map = super.getItemAttributeModifiers(slot);
		if(slot == EntityEquipmentSlot.MAINHAND)
		{
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.damage, 0));
			map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) this.speed, 0));
		}
		return map;
	}
}