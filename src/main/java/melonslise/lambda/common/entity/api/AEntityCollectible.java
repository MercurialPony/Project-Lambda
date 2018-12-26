package melonslise.lambda.common.entity.api;

import java.util.List;

import melonslise.lambda.utility.LambdaSelectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class AEntityCollectible extends AEntityCollidable
{
	public AEntityCollectible(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit() {}

	// TODO Try not to override this to allow ray traces to pass through this (esp tau cannon)
	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	public boolean canBeAttackedWithItem()
	{
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.world.isRemote || this.isDead || this.isEntityInvulnerable(source)) return false;
		if(source.isFireDamage()) this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2F + this.rand.nextFloat() * 0.4F);
		this.setDead();
		return true;
	}

	// TODO Instead get closest player?
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(this.world.isRemote) return;
		List<Entity> players = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), LambdaSelectors.NOT_SPECTATING_LIVING_PLAYER);
		if(players.isEmpty() || !this.collect((EntityPlayer) players.get(0))) return; this.setDead();
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
	{
		if(!this.retrieve(player, hand)) return false;
		if(!this.world.isRemote) this.setDead();
		return true;
	}

	// TODO Work for other entities too?
	public abstract boolean collect(EntityPlayer player);

	public abstract boolean retrieve(EntityPlayer player, EnumHand hand);

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {}
}