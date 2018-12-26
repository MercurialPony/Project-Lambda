package melonslise.lambda.common.tileentity.charger;

import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageSound;
import melonslise.lambda.common.network.message.client.MessageSound.ESound;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.common.tileentity.api.charger.ATileEntityCharger;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

// TODO Use moving sound
public class TileEntityChargerHealth extends ATileEntityCharger
{
	@Override
	public boolean onStartUsing(EntityPlayer player, BlockPos position, IBlockState state, EnumFacing side, Vec3d hit, int type)
	{
		if(this.getCharge() > 0F)
		{
			this.world.playSound(null, this.pos, LambdaSounds.item_medshot, SoundCategory.BLOCKS, 1F, 1F);
			if(!this.world.isRemote) LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageSound(player.getEntityId(), ESound.CHARGER_HEALTH_USE));
			return true;
		}
		else
		{
			this.world.playSound(null, this.pos, LambdaSounds.item_medshotno, SoundCategory.BLOCKS, 1F, 1F);
			return false;
		}
	}

	@Override
	public void onUpdateUsing(EntityPlayer player, BlockPos position, IBlockState state, int ticks, int type)
	{
		if(this.getCharge() > 0F && player.getHealth() < player.getMaxHealth())
		{
			player.heal(0.2F);
			this.setCharge(player, ((int) (this.getCharge() * 10) - 2) / 10F); // To prevent rounding errors
		}
	}

	public void setCharge(EntityPlayer player, float charge)
	{
		float old = this.getCharge();
		this.setCharge(charge);
		if(!this.world.isRemote && old > 0 && charge <= 0F) LambdaUtilities.sendToAllTrackingAndPlayer((EntityPlayerMP) player, LambdaNetworks.network, new MessageSound(player.getEntityId(), ESound.CHARGER_HEALTH_EMPTY));
	}
}