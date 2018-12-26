package melonslise.lambda.common.entity.alien;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Lists;

import melonslise.lambda.common.entity.api.AEntityAlien;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageVortigaunt;
import melonslise.lambda.common.network.message.client.MessageVortigaunt.EVortigauntAction;
import melonslise.lambda.common.network.message.client.MessageVortigauntTarget;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVortigaunt extends AEntityAlien
{
	public static final float fleeHealth = 6F, arcDamage = 7F, arcHeal = 3F;
	public static final double reach = 2D, range = 10D;

	public int arc, charge, claw, cooldownArc, cooldownClaw;

	public Vec3d target;

	// CLIENT ONLY
	public Set<BlockPos> positions = new HashSet<BlockPos>();

	public float rotationHand, oldRotationHand;

	// TODO Proper eye height
	public EntityVortigaunt(World world)
	{
		super(world);
		this.setSize(0.9F, 1.8F);
	}

	// TODO More flee targets
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new AIFlee(this));
		this.tasks.addTask(2, new AIClaw(this));
		this.tasks.addTask(3, new AICharge(this));
		this.tasks.addTask(4, new AIFollow(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2D);
	}

	public void claw(EntityLivingBase entity)
	{
		if(entity != null && this.getDistanceSq(entity) <= reach * reach)
		{
			this.attackEntityAsMob(entity);
			this.playSound(this.getStrikeSound(), this.getSoundVolume(), this.getSoundPitch());
		}
		else this.playSound(this.getMissSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	public void shoot()
	{
		if(!this.world.isRemote)
		{
			Vec3d start = new Vec3d(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ);
			RayTraceResult result = this.world.rayTraceBlocks(start, target, false, true, false);
			if(result != null) this.target = result.hitVec;
			RayTraceResult result1 = LambdaUtilities.rayTraceClosestEntity(this.world, start, this.target, Lists.newArrayList(this), LambdaSelectors.PROJECTILE_TARGETS);
			if(result1 != null)
			{
				if(result1.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this), arcDamage)) this.heal(this.arcHeal); // Yeah, I'm just as surprised as you are that the original game had this
				this.target = result1.hitVec;
			}
			LambdaNetworks.network.sendToAllTracking(new MessageVortigauntTarget(this, this.target), this);
		}
		this.playSound(this.getArcSound(), this.getSoundVolume(), 1.6F);
		this.cooldownArc = 20;
		this.arc = 5;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.oldRotationHand = this.rotationHand;
		if(this.claw > 0)
		{
			if(this.claw % 6 == 0) this.claw(this.getAttackTarget());
			--this.claw;
			if(this.claw == 0) this.cooldownClaw = 6;
		}
		if(this.arc > 0) --this.arc;
		if(this.charge > 0)
		{
			--this.charge;
			if(this.charge == 0) this.shoot();
		}
		if(this.cooldownArc > 0) --this.cooldownArc;
		if(this.cooldownClaw > 0) -- this.cooldownClaw;
		if(this.world.isRemote)
		{
			if(this.charge > 0) this.rotationHand = MathHelper.sin((float) this.charge * 0.2F);
			else
			{
				int clawTicks = this.claw - 1; // TODO Why?
				if(clawTicks > 0)
				{
					if(clawTicks <= 6 || clawTicks > 12)
					{
						if(clawTicks > 12) clawTicks -= 12;
						this.rotationHand = MathHelper.cos((float) (clawTicks) * 1.1F);
					}
					else this.rotationHand = MathHelper.cos((float) (clawTicks - 6) * 1.1F);
				}
			}
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
		return LambdaSounds.alien_vortigaunt_alert;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return LambdaSounds.alien_vortigaunt_die;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return LambdaSounds.alien_vortigaunt_pain;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		if(this.getAttackTarget() == null) return LambdaSounds.alien_vortigaunt_word;
		return null;
	}

	protected SoundEvent getArcSound()
	{
		return LambdaSounds.alien_vortigaunt_shoot;
	}

	protected SoundEvent getChargeSound()
	{
		return LambdaSounds.zap;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return this.arc > 0 && this.target != null? new AxisAlignedBB(this.posX, this.posY, this.posZ, this.target.x, this.target.y, this.target.z) : super.getRenderBoundingBox();
	}



	// TODO Change
	public static class AIFlee extends EntityAIBase
	{
		protected EntityVortigaunt entity;

		public AIFlee(EntityVortigaunt entity)
		{
			this.entity = entity;
		}

		// TODO Change to any entity?
		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getRevengeTarget();
			return target != null && target.isEntityAlive() && this.entity.getHealth() <= fleeHealth;
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

		// TODO Range and long memory target
		@Override
		public void updateTask()
		{
			if(this.entity.getNavigator().noPath())
			{
				EntityLivingBase target = this.entity.getRevengeTarget();
				Vec3d position = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(target.posX, target.posY, target.posZ));
				if(position != null) this.entity.getNavigator().tryMoveToXYZ(position.x, position.y, position.z, 1D);
			}
		}
	}



	public static class AIClaw extends EntityAIBase
	{
		protected EntityVortigaunt entity;

		public AIClaw(EntityVortigaunt entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.claw == 0 && this.entity.cooldownClaw == 0 && this.entity.getDistanceSq(target) <= reach * reach && this.entity.getEntitySenses().canSee(target);
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.claw != 0 && this.entity.getHealth() > fleeHealth;
		}

		@Override
		public void startExecuting()
		{
			this.entity.claw = 18;
			LambdaNetworks.network.sendToAllTracking(new MessageVortigaunt(this.entity, EVortigauntAction.CLAW, 18), this.entity);
		}

		@Override
		public  void resetTask()
		{
			this.entity.claw = 0;
			LambdaNetworks.network.sendToAllTracking(new MessageVortigaunt(this.entity, EVortigauntAction.CLAW, 0), this.entity);
		}
	}



	public static class AICharge extends EntityAIBase
	{
		protected EntityVortigaunt entity;

		public AICharge(EntityVortigaunt entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(target != null && target.isEntityAlive() && this.entity.charge== 0 && this.entity.cooldownArc == 0 && this.entity.claw == 0 && this.entity.getHealth() > fleeHealth && this.entity.getEntitySenses().canSee(target))
			{
				double distance = this.entity.getDistanceSq(target);
				return distance > reach * reach && distance <= range * range;
			}
			return false;
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.charge != 0 && this.entity.claw == 0 && this.entity.getHealth() > fleeHealth;
		}

		@Override
		public void startExecuting()
		{
			this.entity.charge = 25;
			LambdaNetworks.network.sendToAllTracking(new MessageVortigaunt(this.entity, EVortigauntAction.START_CHARGE, 25), this.entity);
		}

		@Override
		public void resetTask()
		{
			this.entity.charge = 0;
			LambdaNetworks.network.sendToAllTracking(new MessageVortigaunt(this.entity, EVortigauntAction.CHARGE, 0), this.entity);
		}

		@Override
		public void updateTask()
		{
			Entity target = this.entity.getAttackTarget();
			if(target != null && this.entity.getEntitySenses().canSee(target))
			{
				Vec3d start = new Vec3d(this.entity.posX, this.entity.posY + (double) this.entity.height / 2D, (double) this.entity.posZ);
				this.entity.target = new Vec3d(target.posX, target.posY + (double) target.height / 2D, target.posZ).subtract(start).normalize().scale(64D).add(start);
			}
		}
	}



	public static class AIFollow extends EntityAIBase
	{
		protected EntityVortigaunt entity;

		public AIFollow(EntityVortigaunt entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(target != null && target.isEntityAlive() && this.entity.getHealth() > fleeHealth && this.entity.claw == 0 && this.entity.charge == 0) return this.entity.getDistanceSq(target) > range * range || !this.entity.getEntitySenses().canSee(target);
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



	public static class AIFace extends EntityAIBase
	{
		protected EntityVortigaunt entity;

		public AIFace(EntityVortigaunt entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.getHealth() > fleeHealth && this.entity.getEntitySenses().canSee(target);
		}

		@Override
		public void updateTask()
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30F, 30F);
		}
	}
}