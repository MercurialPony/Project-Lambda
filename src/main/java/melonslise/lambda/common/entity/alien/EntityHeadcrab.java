package melonslise.lambda.common.entity.alien;

import melonslise.lambda.common.entity.api.AEntityAlien;
import melonslise.lambda.common.sound.LambdaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHeadcrab extends AEntityAlien
{
	// TODO Allow change? / Move to special class?
	public static final double distance = 7D;
	// TODO Combine
	protected EntityAINearestAttackableTarget target1, target2;
	protected EntityAIHurtByTarget target3;

	public EntityHeadcrab(World world)
	{
		super(world);
		this.setSize(0.5F, 0.3F);
	}

	@Override
	protected void initEntityAI() 
	{
		this.tasks.addTask(0, new AIJump(this));
		this.tasks.addTask(1, new AILatch(this));
		this.tasks.addTask(2, new AIFollow(this));
		this.tasks.addTask(0, new AIBite(this));
		this.target3 = new EntityAIHurtByTarget(this, false, new Class[0]);
		this.target1 = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null);
		this.target2 = new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, true, false, null);
		this.toggleTargeting(true);
	}

	protected void toggleTargeting(boolean enable)
	{
		if(enable)
		{
			this.targetTasks.addTask(0, this.target3);
			this.targetTasks.addTask(1, this.target1);
			this.targetTasks.addTask(2, this.target2);
		}
		else
		{
			this.targetTasks.removeTask(this.target3);
			this.targetTasks.removeTask(this.target1);
			this.targetTasks.removeTask(this.target2);
		}
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
	}

	public void jump(Entity entity)
	{
		Vec3d motion = new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ).subtract(this.getPositionVector()).normalize().scale(2D);
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;
	}

	public boolean bite(Entity entity)
	{
		if(!this.canLatchToHead(entity)) return false;
		if(!this.attackEntityAsMob(entity)) return false;
		this.playSound(this.getBiteSound(), this.getSoundVolume(), this.getSoundPitch());
		if(entity instanceof EntityLivingBase && this.isRiding()) ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80, 10));
		return true;
	}

	@Override
	public void onKillEntity(EntityLivingBase entity)
	{
		Entity target = this.getRidingEntity();
		EntityHeadcrabZombie zombie = new EntityHeadcrabZombie(this.world);
		zombie.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
		this.world.spawnEntity(zombie);
		this.setDead();
	}

	public double getSquareDistanceToHead(Entity entity)
	{
		return this.getDistanceSq(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
	}

	// TODO Are coords centered?
	public boolean canLatchToHead(Entity entity)
	{
		//double x = entity.posX, y = entity.posY + entity.getEyeHeight(), z = entity.posZ;
		//return this.getEntityBoundingBox().intersects(x - 0.25D, y - 0.25D, z - 0.25D, x + 0.25D, y + 0.25D, z + 0.25D);
		return this.getSquareDistanceToHead(entity) <= 0.36F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(source == DamageSource.FALL) return false;
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public double getYOffset()
	{
		return 0.4D; // TODO Eye height
	}

	@Override
	protected SoundEvent getAlertSound()
	{
		return LambdaSounds.alien_headcrab_alert;
	}

	protected SoundEvent getAttackSound()
	{
		return LambdaSounds.alien_headcrab_attack;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return LambdaSounds.alien_headcrab_die;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		if(!this.isRiding() && this.getAttackTarget() == null) return LambdaSounds.alien_headcrab_idle;
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return LambdaSounds.alien_headcrab_pain;
	}

	protected SoundEvent getBiteSound()
	{
		return LambdaSounds.alien_headcrab_bite;
	}



	public static class AIJump extends EntityAIBase
	{
		protected EntityHeadcrab entity;
		private int ticks = 0;

		public AIJump(EntityHeadcrab entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return this.entity.onGround && !this.entity.isRiding() && target != null && target.isEntityAlive() && this.entity.getSquareDistanceToHead(target) <= distance * distance && this.entity.getEntitySenses().canSee(target);
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			return this.shouldExecute();
		}

		@Override
		public void updateTask()
		{
			++ticks;
			if(ticks % 30 == 0)
			{
				ticks = 0;
				this.entity.jump(this.entity.getAttackTarget());
				this.entity.playSound(this.entity.getAttackSound(), this.entity.getSoundVolume(), this.entity.getSoundPitch());
			}
		}
	}



	public static class AIFollow extends EntityAIBase
	{
		protected EntityHeadcrab entity;

		public AIFollow(EntityHeadcrab entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(!this.entity.isRiding() && target != null && target.isEntityAlive()) return this.entity.getSquareDistanceToHead(target) > distance * distance || !this.entity.getEntitySenses().canSee(target);
			return false;
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			return this.shouldExecute(); // this.entity.navigator.noPath()
		}

		@Override
		public void startExecuting()
		{
			this.entity.getNavigator().clearPath();
		}

		@Override
		public void resetTask()
		{
			this.entity.getNavigator().clearPath();
		}

		// TODO Maybe not every update tick?
		@Override
		public void updateTask()
		{
			this.entity.getNavigator().tryMoveToEntityLiving(this.entity.getAttackTarget(), 1D);
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30F, 30F);
		}
	}



	public static class AILatch extends EntityAIBase
	{
		protected EntityHeadcrab entity;

		public AILatch(EntityHeadcrab entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return !this.entity.isRiding() && !entity.onGround && target != null && target.isEntityAlive();
		}

		@Override
		public void updateTask()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(this.entity.bite(target) && target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && this.entity.startRiding(target, true) && target instanceof EntityPlayerMP) ((EntityPlayerMP) target).connection.sendPacket(new SPacketSetPassengers(target));
		}
	}



	public static class AIBite extends EntityAIBase
	{
		protected EntityHeadcrab entity;
		private int ticks = 0;

		public AIBite(EntityHeadcrab entity)
		{
			this.entity = entity;
		}

		// TODO Improve
		@Override
		public boolean shouldExecute()
		{
			Entity target = this.entity.getRidingEntity();
			if(this.entity.isRiding() && !entity.onGround && target != null && target.isEntityAlive())
			{
				if(target instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) target;
					return !player.isCreative() && !player.isSpectator();
				}
				return true;
			}
			return false;
		}

		@Override
		public void startExecuting()
		{
			this.entity.toggleTargeting(false);
		}

		@Override
		public void resetTask()
		{
			this.ticks = 0;
			Entity target =  this.entity.getRidingEntity();
			this.entity.dismountRidingEntity();
			if(target instanceof EntityPlayerMP) ((EntityPlayerMP) target).connection.sendPacket(new SPacketSetPassengers(target));
			this.entity.toggleTargeting(true);
		}

		@Override
		public void updateTask()
		{
			Entity target =  this.entity.getRidingEntity();
			++this.ticks;
			if(ticks % 20 == 0)
			{
				this.ticks = 0;
				if(!this.entity.bite(target))
				{
					this.entity.dismountRidingEntity();
					if(target instanceof EntityPlayerMP) ((EntityPlayerMP) target).connection.sendPacket(new SPacketSetPassengers(target));
				}
			}
		}
	}
}