package melonslise.lambda.common.entity.alien;

import melonslise.lambda.common.entity.api.AEntityAlien;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageHeadcrabZombie;
import melonslise.lambda.common.network.message.client.MessageHeadcrabZombie.EHeadcrabZombieAction;
import melonslise.lambda.common.sound.LambdaSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityHeadcrabZombie extends AEntityAlien
{
	public int swing;

	public static final double reach = 3D;

	public float rotationHand, oldRotationHand;

	public EntityHeadcrabZombie(World world)
	{
		super(world);
		this.setSize(0.7F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6D);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new AIFollow(this));
		this.tasks.addTask(2, new AIAttack(this));
		// TODO Predicates?
		// TODO Attack golem?
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, true, false, null));
	}

	// TODO Sync only which hand is swinging?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.oldRotationHand = this.rotationHand;
		if(this.swing > 0)
		{
			if(this.swing % 15 == 0) this.swing(this.getAttackTarget());
			--this.swing;
		}
		if(this.world.isRemote)
		{
			int ticks = this.swing;
			if(ticks > 0)
			{
				if(ticks > 15) ticks -= 15;
				this.rotationHand = -2F + MathHelper.sin((float) ticks * 0.5F);
			}
		}
	}

	public void swing(EntityLivingBase entity)
	{
		if(entity != null && this.getDistanceSq(entity) <= reach * reach)
		{
			this.attackEntityAsMob(entity);
			this.playSound(this.getStrikeSound(), this.getSoundVolume(), this.getSoundPitch());
		}
		else this.playSound(this.getMissSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	@Override
	public void onDeath(DamageSource source)
	{
		if(!source.isFireDamage())
		{
			EntityHeadcrab headcrab = new EntityHeadcrab(this.world);
			headcrab.setLocationAndAngles(this.posX, this.posY + this.getEyeHeight(), this.posZ, this.rotationYaw, this.rotationPitch);
			this.world.spawnEntity(headcrab);
		}
	}

	protected SoundEvent getMissSound()
	{
		return LambdaSounds.alien_zombie_miss;
	}

	protected SoundEvent getStrikeSound()
	{
		return LambdaSounds.alien_zombie_strike;
	}

	@Override
	protected SoundEvent getAlertSound()
	{
		return LambdaSounds.alien_zombie_alert;
	}

	protected SoundEvent getAttackSound()
	{
		return LambdaSounds.alien_zombie_attack;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		if(this.getAttackTarget() == null)
		{
			return LambdaSounds.alien_zombie_idle;
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return LambdaSounds.alien_zombie_pain;
	}



	public static class AIFollow extends EntityAIBase
	{
		protected EntityHeadcrabZombie entity;

		public AIFollow(EntityHeadcrabZombie entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(target != null && target.isEntityAlive() && this.entity.swing == 0)
			{
				return this.entity.getDistanceSq(target) > reach * reach / 4D || !this.entity.getEntitySenses().canSee(target);
			}
			return false;
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

		@Override
		public void updateTask()
		{
			this.entity.getNavigator().tryMoveToEntityLiving(this.entity.getAttackTarget(), 1D);
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30F, 30F);
		}
	}



	public static class AIAttack extends EntityAIBase
	{
		protected EntityHeadcrabZombie entity;

		public AIAttack(EntityHeadcrabZombie entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.swing == 0 && this.entity.getDistanceSq(target) <= reach * reach / 4D && this.entity.getEntitySenses().canSee(target);
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.swing != 0;
		}

		@Override
		public void startExecuting()
		{
			this.entity.swing = 30;
			LambdaNetworks.network.sendToAllTracking(new MessageHeadcrabZombie(this.entity, EHeadcrabZombieAction.START_SWING, 30), this.entity);
		}

		@Override
		public void resetTask()
		{
			this.entity.swing = 0;
			LambdaNetworks.network.sendToAllTracking(new MessageHeadcrabZombie(this.entity, EHeadcrabZombieAction.SWING, 0), this.entity);
		}
	}
}