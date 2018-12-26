package melonslise.lambda.common.entity;

import javax.annotation.Nullable;

import melonslise.lambda.common.entity.projectile.EntityBullet;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// TODO Don't attack placer
// TODO Don't use mob?
public class EntitySentry extends EntityMob
{
	// TODO Best way to handle rotations?
	protected static final DataParameter<Integer> TARGET = EntityDataManager.<Integer>createKey(EntitySentry.class, DataSerializers.VARINT);
	public boolean active = true;
	// TODO Use head yaw (fix renderYawOffset)
	public float rotationYawTop, rotationYawTopOld;

	public EntitySentry(World world)
	{
		super(world);
		this.setSize(0.7F, 1.5F);
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(TARGET, -1);
	}

	protected void initEntityAI()
	{
		//this.tasks.addTask(4, new EntityShulker.AIAttack());
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, LambdaSelectors.SENTRY_TARGETS));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2F);
	}

	@Override
	public void setAttackTarget(@Nullable EntityLivingBase entity)
	{
		if(this.getAttackTarget() != entity) this.dataManager.set(TARGET, entity != null ? entity.getEntityId() : -1);
		super.setAttackTarget(entity);
	}

	// TODO AI
	// TODO Fix setting target to null and back every 2 ticks sometimes
	// TODO Update rotations on both sides?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.rotationYawTopOld = this.rotationYawTop;
		if(this.active && this.isEntityAlive())
		{
			if(!this.world.isRemote)
			{
				EntityLivingBase target = this.getAttackTarget();
				if(target == null) {if(this.ticksExisted % 30 == 0) this.playSound(LambdaSounds.sentry_ping, 1F, 1F); }
				else if(this.ticksExisted % 2 == 0) this.shoot(target);
			}
			else
			{
				// TODO Cache
				Entity target = this.world.getEntityByID(this.dataManager.get(TARGET));
				if(target == null)
				{
					this.rotationYawTop += 3F;
					this.rotationPitch = 0F;
				}
				else
				{
					// TODO Helper?
					// TODO Why -90F on yaw and *(-1) on pitch?
					double deltaX = target.posX - this.posX, deltaY = target.posY + (double) target.getEyeHeight() - this.posY - (double) this.getEyeHeight(), deltaZ = target.posZ - this.posZ;
					this.rotationYawTop = (float) (MathHelper.atan2(deltaZ, deltaX) * 180D / Math.PI) - 90F;
					this.rotationPitch = (float) -(MathHelper.atan2(deltaY, (double) MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ)) * 180D / Math.PI);
				}
			}
		}
	}

	// TODO Helper for this and rocket too and hornet
	// TODO Shoot eyes
	protected void shoot(Entity entity)
	{
		this.playSound(LambdaSounds.weapon_mp_shot, 1F, 1F);
		EntityBullet bullet = new EntityBullet(this.world, (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), 3);
		Vec3d direction = LambdaUtilities.getCenter(this.getAttackTarget().getEntityBoundingBox()).subtract(new Vec3d(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ));
		bullet.owner = this;
		bullet.fire(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ, direction, 4D, (float) Math.PI / 18F);
		this.world.spawnEntity(bullet);
	}

	@Override
	public float getEyeHeight()
	{
		return 1.3F;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return LambdaSounds.sentry_die;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return null;
	}
}