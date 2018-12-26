package melonslise.lambda.common.block.charger;

import melonslise.lambda.common.block.api.charger.ABlockCharger;
import melonslise.lambda.common.tileentity.charger.TileEntityChargerHealth;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChargerHealth extends ABlockCharger
{
	public BlockChargerHealth(String name, Material material)
	{
		super(name, material);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityChargerHealth();
	}
}