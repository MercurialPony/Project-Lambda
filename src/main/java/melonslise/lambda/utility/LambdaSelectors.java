package melonslise.lambda.utility;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import melonslise.lambda.common.entity.EntitySentry;
import melonslise.lambda.common.entity.alien.EntityHoundeye;
import melonslise.lambda.common.entity.alien.EntitySnark;
import melonslise.lambda.common.entity.projectile.EntityHornet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;

// TODO Null checks
/**
 * Contains a lot of different filters mainly used for targets.
 */
public class LambdaSelectors
{
	// TODO Predicates and
	public static final Predicate<Entity> NOT_SPECTATING_LIVING_PLAYER = new Predicate<Entity>()
	{
		public boolean apply(@Nullable Entity entity)
		{
			return entity instanceof EntityPlayer && entity.isEntityAlive() && !((EntityPlayer) entity).isSpectator();
		}
	};

	public static final Predicate<Entity> PROJECTILE_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
	{
		public boolean apply(@Nullable Entity entity)
		{
			return entity.canBeCollidedWith() && !entity.noClip;
		}
	});

	public static final Predicate<Entity> HOUNDEYE_TARGETS = Predicates.and(PROJECTILE_TARGETS, new Predicate<Entity>()
	{
		public boolean apply(@Nullable Entity entity)
		{
			return !(entity instanceof EntityHoundeye);
		}
	});

	public static final Predicate<Entity> SNARK_TARGETS = Predicates.and(PROJECTILE_TARGETS, new Predicate<Entity>()
	{
		public boolean apply(@Nullable Entity entity)
		{
			return !(entity instanceof EntitySnark); //!(entity instanceof EntityPlayer) && 
		}
	});

	public static final Predicate<Entity> SENTRY_TARGETS = Predicates.and(PROJECTILE_TARGETS, new Predicate<Entity>()
	{
		public boolean apply(@Nullable Entity entity)
		{
			return !(entity instanceof EntitySentry);
		}
	});

	public static final Predicate<Entity> HORNET_TARGETS = Predicates.and(PROJECTILE_TARGETS, new Predicate<Entity>()
	{
		public boolean apply(@Nullable Entity entity)
		{
			return !(entity instanceof EntityHornet) && entity instanceof EntityLiving;
		}
	});

	// TODO Packed ice shatter?
	public static final Predicate<IBlockState> SHATTERABLES = new Predicate<IBlockState>()
	{
		public boolean apply(@Nullable IBlockState state)
		{
			Material material = state.getMaterial();
			return material == Material.GLASS || material == Material.ICE || material == Material.PACKED_ICE;
		}
	};

	public static final Predicate<IBlockState> SOLIDS = new Predicate<IBlockState>()
	{
		public boolean apply(@Nullable IBlockState state)
		{
			return state.getMaterial().blocksMovement();
		}
	};

	private LambdaSelectors() {};
}