package melonslise.lambda.common.entity.alien;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import melonslise.lambda.common.loot.LambdaLoot;
import melonslise.lambda.common.network.LambdaNetworks;
import melonslise.lambda.common.network.message.client.MessageBarnacle;
import melonslise.lambda.common.sound.LambdaSounds;
import melonslise.lambda.utility.LambdaSelectors;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBarnacle extends EntityMob // TODO Disable collision
{
	// TODO Change to actual length instead of offset
	public static final double tongueSpeed = 0.06D;
	public double tongueLength;
	protected int biteDelay;

	public EntityBarnacle(World world)
	{
		super(world);
		this.setSize(0.8F, 1F);
		this.setNoGravity(true);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7D);
	}

	public Entity getTarget()
	{
		List<Entity> passengers = this.getPassengers();
		return passengers.isEmpty() ? null : passengers.get(0);
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}

	@Override
	public void onUpdate()
	{
		BlockPos position = this.getPosition();
		if(!this.world.isRemote && !world.getBlockState(position.up()).getMaterial().isSolid()) this.setDead();
		this.motionX = this.motionY = this.motionZ = 0D;
		super.onUpdate();
		if(this.isEntityAlive())
		{
			Entity target = this.getTarget();
			if(target == null)
			{
				BlockPos ground = LambdaUtilities.getFirstBlockInDirection(world, position, EnumFacing.DOWN, LambdaSelectors.SOLIDS, 24).up();
				if(this.posY - (double) ground.getY() > -this.tongueLength + tongueSpeed) this.tongueLength -= tongueSpeed;
				else this.tongueLength = (double) ground.getY() - this.posY;
				if(this.world.isRemote) return;
				Iterator<Entity> iterator = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB((double) position.getX(), (double) position.getY(), (double) position.getZ(), (double) position.getX() + 1D, (double) position.getY() + this.tongueLength, (double) position.getZ() + 1D)).iterator();
				while(this.getPassengers().isEmpty() && iterator.hasNext())
				{
					Entity entity = iterator.next();
					if(this.isLiftable(entity)) this.startPulling(entity);
				}
			}
			else
			{
				if(this.canChew(target))
				{
					if(this.world.isRemote) return;
					if(this.biteDelay == 0) this.playSound(this.getBiteSound(), this.getSoundVolume(), this.getSoundPitch());
					++this.biteDelay;
					if(this.biteDelay % 30 == 0 && !this.bite(target)) target.dismountRidingEntity(); // TODO
				}
				else this.pull(target);
			}
		}
	}

	@Override
	public boolean shouldRiderSit()
	{
		return false;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	public boolean isLiftable(Entity entity)
	{
		return entity.isEntityAlive() && entity.height <= 2F && entity.width <= 2F; // entity.canBeCollidedWith() && !entity.noClip
	}

	// TODO Can chew here
	public boolean bite(Entity entity)
	{
		this.playSound(this.getChewSound(), this.getSoundVolume(), this.getSoundPitch());
		if(!this.attackEntityAsMob(entity)) return false;
		if(entity instanceof EntityLivingBase) ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80, 10));
		return true;
	}

	public boolean canChew(Entity entity)
	{
		double y = entity.posY + (double) entity.height;
		return y >= this.posY + (double) this.height / 2D;
	}

	@Override
	public float getSoundPitch()
	{
		return 1F;
	}

	@Override
	public double getMountedYOffset()
	{
		return this.tongueLength - (double) this.getTarget().height;
	}

	public boolean startPulling(Entity entity)
	{
		if(!entity.startRiding(this, true)) return false;
		this.tongueLength = entity.posY + (double) entity.height - this.posY;
		LambdaNetworks.network.sendToAllTracking(new MessageBarnacle(this, this.tongueLength), this);
		this.biteDelay = 0;
		this.playSound(this.getAlertSound(), this.getSoundVolume(), this.getSoundPitch());
		return true;
	}

	public void pull(Entity entity)
	{
		this.tongueLength += tongueSpeed;
		//if(entity instanceof EntityLivingBase) ((EntityLivingBase) entity).limbSwingAmount += 0.1F;
	}

	// TODO Why?
	@Override
	@Nullable
	public EntityItem entityDropItem(ItemStack stack, float offsetY)
	{
		if (stack.isEmpty()) return null;
		EntityItem item = new EntityItem(this.world, this.posX, this.posY, this.posZ, stack);
		item.setDefaultPickupDelay();
		if (captureDrops) this.capturedDrops.add(item);
		else this.world.spawnEntity(item);
		return item;
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LambdaLoot.ENTITIES_BARNACLE;
	}

	protected SoundEvent getAlertSound()
	{
		return LambdaSounds.alien_barnacle_alert;
	}

	protected SoundEvent getBiteSound()
	{
		return LambdaSounds.alien_barnacle_bite;
	}

	protected SoundEvent getChewSound()
	{
		return LambdaSounds.alien_barnacle_chew;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return LambdaSounds.alien_barnacle_die;
	}

	protected SoundEvent getTongueSound()
	{
		return LambdaSounds.alien_barnacle_tongue;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		AxisAlignedBB box = super.getRenderBoundingBox();
		return new AxisAlignedBB(box.minX, box.minY + this.tongueLength, box.minZ, box.maxX, box.maxY, box.maxZ);
	}
}