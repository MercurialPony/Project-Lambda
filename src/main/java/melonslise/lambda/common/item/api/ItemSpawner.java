package melonslise.lambda.common.item.api;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@Deprecated
public class ItemSpawner extends LambdaItem
{
	protected final ResourceLocation entityID;

	public ItemSpawner(String name, ResourceLocation entityID)
	{
		super(name);
		this.entityID = entityID;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String s1 = EntityList.getTranslationName(this.entityID);
		if (s1 != null) s1 = I18n.translateToLocal("entity." + s1 + ".name");
		return s1;
	}

	public ResourceLocation getEntityID()
	{
		return this.entityID;
	}

	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos position, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote) return EnumActionResult.SUCCESS;
		if(!this.canPlace(player, position, hand, facing, hitX, hitY, hitZ)) return EnumActionResult.FAIL;
		IBlockState state = world.getBlockState(position);
		if(this.affectsSpawners(player, position, hand, facing, hitX, hitY, hitZ) && state.getBlock() == Blocks.MOB_SPAWNER)
		{
			TileEntity tile = world.getTileEntity(position);
			if(tile instanceof TileEntityMobSpawner)
			{
				MobSpawnerBaseLogic logic = ((TileEntityMobSpawner) tile).getSpawnerBaseLogic();
				logic.setEntityId(this.entityID);
				tile.markDirty();
				world.notifyBlockUpdate(position, state, state, 3);
				if(!player.isCreative()) stack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
		}
		// double d0 = this.getYOffset(world, offset);
		BlockPos offset = position.offset(facing);
		Entity entity = this.spawn(world, (double) offset.getX() + 0.5D, (double) offset.getY(), (double) offset.getZ() + 0.5D, this.getYaw(player, hand, position, facing, hitX, hitY, hitZ), this.getPitch(player, hand, position, facing, hitX, hitY, hitZ));
		if(entity != null)
		{
			if(entity instanceof EntityLivingBase && stack.hasDisplayName()) entity.setCustomNameTag(stack.getDisplayName());
			ItemMonsterPlacer.applyItemEntityDataToEntity(world, player, stack, entity);
			if(!player.capabilities.isCreativeMode) stack.shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}

	protected boolean canPlace(EntityPlayer player, BlockPos position, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return player.canPlayerEdit(position.offset(facing), facing, player.getHeldItem(hand));
	}

	protected boolean affectsSpawners(EntityPlayer player, BlockPos position, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return true;
	}

	protected float getYaw(EntityPlayer player, EnumHand hand, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return ThreadLocalRandom.current().nextFloat() * 360F;
	}

	protected float getPitch(EntityPlayer player, EnumHand hand, BlockPos position, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return 0F;
	}

	public Entity spawn(World world, double x, double y, double z, float yaw, float pitch)
	{
		return spawn(world, this.entityID, x, y, z, yaw, pitch);
	}

	public static Entity spawn(World world, ResourceLocation entityID, double x, double y, double z, float yaw, float pitch)
	{
		Entity entity = EntityList.createEntityByIDFromName(entityID, world);
		entity.setLocationAndAngles(x, y, z, yaw, pitch);
		if(entity instanceof EntityLiving)
		{
			EntityLiving living = (EntityLiving) entity;
			living.rotationYawHead = living.renderYawOffset  = living.rotationYaw;
			living.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(living)), null);
			living.playLivingSound();
		}
		world.spawnEntity(entity);
		return entity;
	}
}