package melonslise.lambda.common.entity.alien;

import java.util.Iterator;

import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

// TODO Own DamageSource
// TODO Sound methods
// TODO No fall damage?
public class EntitySnark extends EntityMob
{
	public EntityLivingBase owner;
	public static final int life = 400;

	public EntitySnark(World world)
	{
		super(world);
		this.setSize(0.35F, 0.35F);
	}

	// TODO Offset in front to prevent collision pushing
	public void fire(EntityLivingBase entity, double speed)
	{
		this.owner = entity;
		this.fire(entity.posX, entity.posY + (double) entity.getEyeHeight() - 0.2D, entity.posZ, entity.getLookVec(), speed);
	}

	public void fire(double x, double y, double z, Vec3d direction, double speed)
	{
		this.setPosition(x, y, z);
		Vec3d motion = direction.normalize().scale(speed);
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;
	}

	// TODO Bigger attack range
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntitySnark.AILeapAtTarget(this, 0.5F));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1D, false));
		// TODO Avoid water?
		this.tasks.addTask(2, new EntityAIWander(this, 0.6D, 4));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, LambdaSelectors.SNARK_TARGETS));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(!this.world.isRemote && this.getAttackTarget() != null && this.ticksExisted % (this.rand.nextInt(10) + 15) == 0) this.playSound(LambdaSounds.alien_snark_hunt, this.getSoundVolume(), this.getSoundPitch() + (float) this.ticksExisted / 800F);
		if(this.ticksExisted >= life) this.explode();
	}

	protected void explode()
	{
		this.setDead();
		this.playSound(this.getDeathSound(), 1F, 1F);
		this.playSound(LambdaSounds.alien_snark_blast, 1F, 1F);
		// TODO Green tint
		if(this.world.isRemote) this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 0, 0, 0);
		if(!this.world.isRemote)
		{
			// TODO Helper
			// TODO Use excluding?
			// TODO Range
			// TODO Damage
			Vec3d position = this.getPositionVector();
			Iterator<EntityLivingBase> iterator = this.world.getEntitiesWithinAABB(EntityLivingBase.class, LambdaUtilities.createAABB(position.subtract(2D, 2D, 2D), position.addVector(2D, 2D, 2D)), LambdaSelectors.SNARK_TARGETS).iterator();
			while(iterator.hasNext()) iterator.next().attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner), 1F);
		}
	}

	public void onDeath(DamageSource source)
	{
		super.onDeath(source);
		this.explode();
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		this.playSound(LambdaSounds.alien_snark_deploy, 1F, 1F);
		float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		int knockback = 0;
		if (entity instanceof EntityLivingBase)
		{
			damage += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) entity).getCreatureAttribute());
			knockback += EnchantmentHelper.getKnockbackModifier(this);
		}
		if(!entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner), damage)) return false;
		if (knockback > 0 && entity instanceof EntityLivingBase)
		{
			((EntityLivingBase) entity).knockBack(this, (float) knockback * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
			this.motionX *= 0.6D;
			this.motionZ *= 0.6D;
		}
		int burn = EnchantmentHelper.getFireAspectModifier(this);
		if (burn > 0) entity.setFire(burn * 4);
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			ItemStack stack = this.getHeldItemMainhand();
			ItemStack stack1 = player.isHandActive() ? player.getActiveItemStack() : ItemStack.EMPTY;
			if (!stack.isEmpty() && !stack1.isEmpty() && stack.getItem().canDisableShield(stack, stack1, player, this) && stack1.getItem().isShield(stack1, player))
			{
				float efficiency = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
				if (this.rand.nextFloat() < efficiency)
				{
					player.getCooldownTracker().setCooldown(stack1.getItem(), 100);
					this.world.setEntityState(player, (byte) 30);
				}
			}
		}
		this.applyEnchantments(this, entity);
		return true;
	}

	@Override
	public SoundEvent getDeathSound()
	{
		return LambdaSounds.alien_snark_die;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return null;
	}

	public static final String keyLife = "life", keyOwner = "owner";

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger(keyLife, this.ticksExisted);
		if(this.owner != null) nbt.setUniqueId(keyOwner, this.owner.getUniqueID());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.ticksExisted = nbt.getInteger(keyLife);
		if(nbt.hasKey(keyOwner)) this.owner = (EntityLivingBase) ((WorldServer) this.world).getEntityFromUuid(nbt.getUniqueId(keyOwner));
	}



	public static class AILeapAtTarget extends EntityAIBase
	{
		/** The entity that is leaping. */
		EntityLiving leaper;
		/** The entity that the leaper is leaping towards. */
		EntityLivingBase leapTarget;
		/** The entity's motionY after leaping. */
		float motion;

		// TODO Min distance, max distance
		public AILeapAtTarget(EntityLiving leaper, float motion)
		{
			this.leaper = leaper;
			this.motion = motion;
			this.setMutexBits(5);
		}

		@Override
		public boolean shouldExecute()
		{
			this.leapTarget = this.leaper.getAttackTarget();
			if (this.leapTarget == null) return false;
			else
			{
				double distance = this.leaper.getDistanceSq(this.leapTarget);
				if (distance >= 0D && distance <= 9D)
				{
					if (!this.leaper.onGround) return false;
					else return this.leaper.getRNG().nextInt(4) == 0;
				}
				else return false;
			}
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			return !this.leaper.onGround;
		}

		public void startExecuting()
		{
			double deltaX = this.leapTarget.posX - this.leaper.posX;
			double deltaZ = this.leapTarget.posZ - this.leaper.posZ;
			float length = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
			if ((double) length >= 1E-4D)
			{
				this.leaper.motionX += deltaX / (double) length * 0.6D * 0.8D + this.leaper.motionX * 0.2D;
				this.leaper.motionZ += deltaZ / (double) length * 0.6D * 0.8D + this.leaper.motionZ * 0.2D;
			}
			this.leaper.motionY = (double) this.motion;
		}
	}
}