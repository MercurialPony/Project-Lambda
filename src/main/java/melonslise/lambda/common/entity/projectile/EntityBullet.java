package melonslise.lambda.common.entity.projectile;

import com.google.common.base.Predicate;

import io.netty.util.internal.ThreadLocalRandom;
import melonslise.lambda.common.entity.api.AEntityProjectile;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

// TODO Not affected by other stuff like explosions/etc/fire
// TODO Configurable thickness
// TODO Bullet type, info
public class EntityBullet extends AEntityProjectile
{
	protected float damage = 2F;
	protected int soundChance;

	public EntityBullet(World world)
	{
		super(world);
		this.setSize(0.1F, 0.1F);
	}

	public EntityBullet(World world, float damage, int soundChance)
	{
		this(world);
		this.damage = damage;
		this.soundChance = soundChance;
	}

	@Override
	protected void entityInit() {}

	// TODO Add penetration amount
	// TODO
	@Override
	protected void onHit(RayTraceResult result)
	{
		if(this.world.isRemote) return;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		if(result.entityHit != null)
		{
			if(result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner), this.damage)) result.entityHit.hurtResistantTime = 0;
			if(this.soundChance <= 1 || random.nextInt(this.soundChance) == 0) this.playSound(this.getHitBodySound(), 1F, 1F);
			this.setDead();
		}
		else if(result.getBlockPos() != null)
		{
			if(this.getPenetratedBlocks().apply(this.world.getBlockState(result.getBlockPos()))) this.world.destroyBlock(result.getBlockPos(), false);
			else
			{
				if(this.soundChance <= 1 || random.nextInt(this.soundChance) == 0)this.playSound(this.getHitSound(), 1F, 1F);
				this.setDead();
			}
		}
	}

	// TODO Configurable
	// TODO Try to pass position and state?
	@Override
	protected Predicate<IBlockState> getPenetratedBlocks()
	{
		return LambdaSelectors.SHATTERABLES;
	}

	@Override
	public double getWaterResistance()
	{
		return 0.95D;
	}

	// TODO Falloff?
	@Override
	public double getGravity()
	{
		return 0D;
	}

	public SoundEvent getHitBodySound()
	{
		return LambdaSounds.projectile_bullet_hitbody;
	}

	public SoundEvent getHitSound()
	{
		return LambdaSounds.projectile_bullet_ricochet;
	}

	public static final String keyDamage = "damage";

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setFloat(keyDamage, this.damage);
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.damage = nbt.getFloat(keyDamage);
	}
}