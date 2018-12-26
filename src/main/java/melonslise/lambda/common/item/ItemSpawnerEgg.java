package melonslise.lambda.common.item;

import melonslise.lambda.common.item.api.ItemSpawner;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

// Interface for the color instead?
@Deprecated
public class ItemSpawnerEgg extends ItemSpawner
{
	protected final int color1, color2;

	public ItemSpawnerEgg(String name, ResourceLocation entityID, int colorPrimary, int colorSecondary)
	{
		super(name, entityID);
		this.color1 = colorPrimary;
		this.color2 = colorSecondary;
		this.setUnlocalizedName("monsterPlacer");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String s = ("" + I18n.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = EntityList.getTranslationName(this.entityID);
		if (s1 != null) s = s + " " + I18n.translateToLocal("entity." + s1 + ".name");
		return s;
	}

	public int getColor(ItemStack stack, int index)
	{
		return index == 0 ? color1 : color2;
	}
}