package melonslise.lambda.common.entity.alien;

import java.util.Iterator;
import java.util.List;

import melonslise.lambda.common.entity.api.AEntityAlien;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageHoundeye;
import melonslise.lambda.common.network.message.client.MessageHoundeye.EHoundeyeAction;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHoundeye extends AEntityAlien
{
	public int blast, charge, flee;

	public static final double range = 3D;
	public static final float blastDamage = 3F, bonusDamage = 2F;

	public float blastRadius, oldBlastRadius;

	public EntityHoundeye(World world)
	{
		super(world);
		this.setSize(0.8F, 0.8F);
	}

	// TODO Pack AI
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new AIFlee(this));
		this.tasks.addTask(2, new AIFollow(this));
		this.tasks.addTask(3, new AICharge(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, true, false, null));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.oldBlastRadius = this.blastRadius;
		if(!this.world.isRemote && this.getAttackTarget() != null && this.ticksExisted % (this.rand.nextInt(10) + 15) == 0) this.playSound(LambdaSounds.alien_houndeye_hunt, this.getSoundVolume(), this.getSoundPitch());
		if(this.charge > 0)
		{
			--this.charge;
			if(this.charge == 0) this.blast();
		}
		if(this.blast > 0)
		{
			this.blastRadius = (5F - (float) this.blast) * 2F;
			--this.blast;
		}
		else this.blastRadius = 0F;
		if(this.flee > 0) --this.flee;
	}

	// TODO Configurable blast range
	public void blast()
	{
		if(!this.world.isRemote)
		{
			{
				AxisAlignedBB radius = new AxisAlignedBB(this.posX - 5D, this.posY - 2D, this.posZ - 5D, this.posX + 5D, this.posY + 2D, this.posZ + 5D);
				int allies = this.world.getEntitiesWithinAABB(EntityHoundeye.class, radius).size() - 1;
				List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, radius, LambdaSelectors.HOUNDEYE_TARGETS);
				Iterator<Entity> iterator = entities.iterator();
				while(iterator.hasNext()) iterator.next().attackEntityFrom(DamageSource.causeMobDamage(this), blastDamage + bonusDamage * (float) MathHelper.clamp(allies, 0, 3));
			}
			if(ForgeEventFactory.getMobGriefingEvent(this.world, this))
			{
				Iterable<BlockPos> blocks = BlockPos.getAllInBox((int) this.posX - 5, (int) this.posY - 2, (int) this.posZ - 5, (int) this.posX + 5, (int) this.posY + 2, (int) this.posZ + 5);
				Iterator<BlockPos> iterator = blocks.iterator();
				while(iterator.hasNext())
				{
					BlockPos block = iterator.next();
					if(LambdaSelectors.SHATTERABLES.apply(this.world.getBlockState(block))) this.world.destroyBlock(block, true);
				}
			}
		}
		this.blast = 5;
		this.playSound(this.getBlastSound(), this.getSoundVolume(), this.getSoundPitch());
	}

	@Override
	protected SoundEvent getAlertSound()
	{
		return LambdaSounds.alien_houndeye_alert;
	}

	protected SoundEvent getAttackSound()
	{
		return LambdaSounds.alien_houndeye_attack;
	}

	protected SoundEvent getBlastSound()
	{
		return LambdaSounds.alien_houndeye_blast;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return LambdaSounds.alien_houndeye_die;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return this.getAttackTarget() == null ? LambdaSounds.alien_houndeye_idle : null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return LambdaSounds.alien_houndeye_pain;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		this.flee = 30;
		return super.attackEntityFrom(source, amount);
	}

	// TODO Blast range
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		AxisAlignedBB box = super.getRenderBoundingBox();
		return this.blast > 0 ? new AxisAlignedBB(box.minX - 5D, box.minY - 2D, box.minZ - 5D, box.maxX + 5D, box.maxY + 2D, box.maxZ + 5D) : box;
	}



	public static class AIFlee extends EntityAIBase
	{
		protected EntityHoundeye entity;

		public AIFlee(EntityHoundeye entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getRevengeTarget();
			return target != null && target.isEntityAlive() && this.entity.flee > 0;
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
			this.entity.flee = 0;
		}

		@Override
		public void updateTask()
		{
			if(this.entity.getNavigator().noPath())
			{
				EntityLivingBase target = this.entity.getRevengeTarget();
				Vec3d position = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 3, 2, new Vec3d(target.posX, target.posY, target.posZ));
				if(position != null)
				{
					this.entity.getNavigator().tryMoveToXYZ(position.x, position.y, position.z, 1D);
				}
			}
		}
	}



	public static class AIFollow extends EntityAIBase
	{
		protected EntityHoundeye entity;

		public AIFollow(EntityHoundeye entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(target != null && target.isEntityAlive() && this.entity.charge == 0 && this.entity.flee == 0)
			{
				return (double) this.entity.getDistance(target) > range || !this.entity.getEntitySenses().canSee(target);
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



	public static class AICharge extends EntityAIBase
	{
		protected EntityHoundeye entity;

		public AICharge(EntityHoundeye entity)
		{
			this.entity = entity;
		}

		@Override
		public boolean shouldExecute()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			if(target != null && target.isEntityAlive() && this.entity.charge == 0 && this.entity.getEntitySenses().canSee(target) && this.entity.flee == 0)
			{
				return (double) this.entity.getDistance(target) <= range;
			}
			return false;
		}

		@Override
		public boolean shouldContinueExecuting()
		{
			EntityLivingBase target = this.entity.getAttackTarget();
			return target != null && target.isEntityAlive() && this.entity.charge != 0 && this.entity.flee == 0;
		}

		@Override
		public void startExecuting()
		{
			this.entity.charge = 50;
			LambdaNetworks.network.sendToAllTracking(new MessageHoundeye(this.entity, EHoundeyeAction.START_CHARGE, 50), this.entity);
		}

		@Override
		public void resetTask()
		{
			this.entity.charge = 0;
			LambdaNetworks.network.sendToAllTracking(new MessageHoundeye(this.entity, EHoundeyeAction.CHARGE, 0), this.entity);
		}
	}
}