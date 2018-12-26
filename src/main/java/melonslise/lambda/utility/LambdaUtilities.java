package melonslise.lambda.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import melonslise.lambda.LambdaCore;
import melonslise.lambda.common.capability.LambdaCapabilities;
import melonslise.lambda.common.capability.entity.ICapabilityPower;
import melonslise.lambda.common.capability.entity.ICapabilityReloading;
import melonslise.lambda.common.capability.entity.ICapabilityRemoteCharges;
import melonslise.lambda.common.capability.entity.ICapabilityUsingItem;
import melonslise.lambda.common.capability.entity.ICapabilityUsingTile;
import melonslise.lambda.common.capability.entity.ICapabilityZooming;
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.item.api.AItemReloadable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

// TODO Better categories
// TODO Split generic utils and mod specific stuff
/**
 * Contains a lot of useful reusable code.
 */
public class LambdaUtilities
{
	private LambdaUtilities() {}



	/*
	 * 
	 * Math
	 * 
	 */

	// TODO Why negative angles?
	/**
	 * Rotates the given point by the given pitch and yaw angles. Rotates over the pitch axis first, then the yaw axis.
	 */
	public static Vec3d rotateVector(Vec3d point, float yaw, float pitch)
	{
		return point.rotatePitch(-pitch / 180F * (float) Math.PI).rotateYaw(-yaw / 180F * (float) Math.PI);
	}

	/**
	 * Returns a random unit vector inside a sphere cap defined by a given axis vector and angle.
	 * @see <a href="https://math.stackexchange.com/questions/56784/generate-a-random-direction-within-a-cone">source</a>
	 */
	public static Vec3d sampleSphereCap(Vec3d coneAxis, float angle)
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		// TODO Helper
		// Choose an axis
		Vec3d axis;
		if(Math.abs(coneAxis.x) < Math.abs(coneAxis.y))
		{
			if(Math.abs(coneAxis.x) < Math.abs(coneAxis.z)) axis = new Vec3d(1D, 0D, 0D);
			else axis = new Vec3d(0D, 0D, 1D);
		}
		else
		{
			if(Math.abs(coneAxis.y) < Math.abs(coneAxis.z)) axis = new Vec3d(0D, 1D, 0D);
			else axis = new Vec3d(0D, 0D, 1D);
		}
		// Construct two mutually orthogonal vectors, both of which are orthogonal to the cone's axis vector
		Vec3d u = coneAxis.crossProduct(axis);
		Vec3d v = coneAxis.crossProduct(u);
		// Uniformly  sample an angle of rotation around the cone's axis vector
		float phi = random.nextFloat() * 2F * (float) Math.PI;
		// Uniformly sample an angle of rotation around one of the orthogonal vectors
		float theta = (float) Math.acos(random.nextDouble((double) MathHelper.cos(angle), 1D));
		// Construct a unit vector uniformly distributed on the spherical cap
		return  coneAxis.scale(MathHelper.cos(theta)).add(u.scale(MathHelper.cos(phi) * MathHelper.sin(theta))).add(v.scale(MathHelper.sin(phi) * MathHelper.sin(theta)));
	}



	/*
	 * 
	 * Domains
	 * 
	 */

	/**
	 * Prefixes the given path string with the mod's ID.
	 */
	public static String prefixLambda(String path)
	{
		return String.join(".", LambdaCore.ID, path);
	}

	/**
	 * Creates a resource location with the mod's ID as the domain and the given path.
	 */
	public static ResourceLocation createLambdaDomain(String path)
	{
		return new ResourceLocation(LambdaCore.ID, path);
	}



	/*
	 * 
	 * Java
	 * 
	 */

	// TODO Generics?
	/**
	 * Removes all elements from the given minuend collection that are contained in the given subtrahend collection and returns the new minuend collection.
	 */
	public static  Collection subtract(Collection minuend, Collection subtrahend)
	{
		minuend.removeAll(subtrahend);
		return minuend;
	}

	// System.out.println(sun.reflect.Reflection.getCallerClass(2).getName());



	/*
	 * 
	 * Colors
	 * 
	 */

	/**
	 * Converts the red value of the given RGBA color to a float.
	 */
	public static float getRed(int color)
	{
		return (float) (color >> 16 & 255) / 255F;
	}

	/**
	 * Converts the green value of the given RGBA color to a float.
	 */
	public static float getGreen(int color)
	{
		return (float) (color >> 8 & 255) / 255F;
	}

	/**
	 * Converts the blue value of the given RGBA color to a float.
	 */
	public static float getBlue(int color)
	{
		return (float) (color & 255) / 255F;
	}

	/**
	 * Converts the alpha value of the given RGBA color to a float.
	 */
	public static float getAlpha(int color)
	{
		return (float) (color >> 24 & 255) / 255F;
	}



	/*
	 * 
	 * Networking
	 * 
	 */

	/**
	 * Sends a message to all listeners tracking the given player as well as the player themself.
	 */
	public static void sendToAllTrackingAndPlayer(EntityPlayerMP player, SimpleNetworkWrapper network, IMessage message)
	{
		network.sendToAllTracking(message, player);
		network.sendTo(message, player);
	}

	/**
	 * Constructs a three-dimensional vector by reading three consecutive floats from the given byte buffer.
	 */
	public static Vec3d readFloatVector(ByteBuf buffer)
	{
		return new Vec3d((double) buffer.readFloat(), (double) buffer.readFloat(), (double) buffer.readFloat());
	}

	/*
	 * Writes the given three-dimensional vector as three consecutive floats to the given byte buffer.
	 */
	public static void writeFloatVector(ByteBuf buffer, Vec3d vector)
	{
		buffer.writeFloat((float) vector.x);
		buffer.writeFloat((float) vector.y);
		buffer.writeFloat((float) vector.z);
	}

	/**
	 * Constructs a three-dimensional vector by reading three consecutive integers from the given byte buffer.
	 */
	public static Vec3i readIntegerVector(ByteBuf buffer)
	{
		return new Vec3i(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}

	/*
	 * Writes the given three-dimensional vector as three consecutive integers to the given byte buffer.
	 */
	public static void writeIntegerVector(ByteBuf buffer, Vec3i vector)
	{
		buffer.writeInt(vector.getX());
		buffer.writeInt(vector.getY());
		buffer.writeInt(vector.getZ());
	}

	/**
	 * Constructs a three-dimensional vector by reading three consecutive shorts from the given byte buffer.
	 */
	public static Vec3i readShortVector(ByteBuf buffer)
	{
		return new Vec3i((int) buffer.readShort(), (int) buffer.readShort(), (int) buffer.readShort());
	}

	/*
	 * Writes the given three-dimensional vector as three consecutive shorts to the given byte buffer.
	 */
	public static void writeShortVector(ByteBuf buffer, Vec3i vector)
	{
		buffer.writeShort((short) vector.getX());
		buffer.writeShort((short) vector.getY());
		buffer.writeShort((short) vector.getZ());
	}

	/**
	 * Gets an enum of the given class by reading a short from the given buffer.
	 */
	public static <T extends Enum<T>> T readEnum(ByteBuf buffer, Class<T> c)
	{
		return (T) ((Enum[]) c.getEnumConstants())[(int) buffer.readShort()];
	}

	/**
	 * Writes the given enum's ordinal as a short to the given buffer.
	 */
	public static void writeEnum(ByteBuf buffer, Enum<?> value)
	{
		buffer.writeShort((short) value.ordinal());
	}



	/*
	 * 
	 * Capabilities
	 * 
	 */

	// TODO Move these?

	public static ICapabilityPower getPower(Entity entity)
	{
		return entity.getCapability(LambdaCapabilities.power, null);
	}

	public static ICapabilityUsingItem getUsingItem(Entity entity)
	{
		return entity.getCapability(LambdaCapabilities.usingItem, null);
	}

	public static ICapabilityUsingTile getUsingTile(Entity entity)
	{
		return entity.getCapability(LambdaCapabilities.usingTile, null);
	}

	public static ICapabilityReloading getReloading(Entity entity)
	{
		return entity.getCapability(LambdaCapabilities.reloading, null);
	}

	public static ICapabilityZooming getZooming(Entity entity)
	{
		return entity.getCapability(LambdaCapabilities.zooming, null);
	}

	public static ICapabilityRemoteCharges getRemoteCharges(Entity entity)
	{
		return entity.getCapability(LambdaCapabilities.remoteCharges, null);
	}



	/*
	 * 
	 * Item stacks
	 * 
	 */

	/**
	 * Returns the given stack's NBT tag. Assigns a new NBT tag if the given stack doesn't have one.
	 */
	public static NBTTagCompound getTag(ItemStack stack)
	{
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}

	/**
	 * Checks if the given stack has an NBT tag and if it contains the given key.
	 */
	public static boolean tagHasKey(ItemStack stack, String key)
	{
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(key);
	}

	/**
	 * Returns the total size of all the stacks in the given list.
	 */
	public static int getStackTotal(Collection<ItemStack> stacks)
	{
		int total = 0;
		Iterator<ItemStack> iterator = stacks.iterator();
		while(iterator.hasNext()) total += iterator.next().getCount();
		return total;
	}

	/**
	 * Returns a list of all the stacks of the given item in the given collection.
	 */
	public static ArrayList<ItemStack> findStacks(Collection<ItemStack> stacks, Item item)
	{
		ArrayList<ItemStack> matching = new ArrayList<ItemStack>();
		Iterator<ItemStack> iterator = stacks.iterator();
		while(iterator.hasNext())
		{
			ItemStack stack = iterator.next();
			if(stack.getItem() == item) matching.add(stack);
		}
		return matching;
	}

	// TODO Move to weapon?
	/**
	 * Returns the integer stored under the "magazine" tag used in {@link AItemReloadable} from the given stack. If the tag doesn't exist, returns -1.
	 */
	public static int getMagazine(ItemStack stack)
	{
		return tagHasKey(stack, AItemReloadable.keyMagazine) ? stack.getTagCompound().getInteger(AItemReloadable.keyMagazine) : -1;
	}

	/**
	 * Returns a new spawn egg stack with the given entity's ID.
	 */
	public static ItemStack createEgg(ResourceLocation entityID)
	{
		ItemStack egg = new ItemStack(Items.SPAWN_EGG);
		ItemMonsterPlacer.applyEntityIdToItemStack(egg, entityID);
		return egg;
	}



	/*
	 * 
	 * Entities
	 * 
	 */

	// TODO Rename?
	/**
	 * Checks if the given entity is affected by a negative potion effect.
	 */
	public static boolean hasBadPotionEffect(EntityLivingBase entity)
	{
		Iterator<PotionEffect> iterator = entity.getActivePotionEffects().iterator();
		while(iterator.hasNext()) if(iterator.next().getPotion().isBadEffect()) return true;
		return false;
	}

	/*
	 * Rotates the given entity towards its motion vector at the given speed.
	 */
	public static void rotateTowardsMotion(Entity entity, float speed)
	{
		float horizontalMotion = MathHelper.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
		entity.rotationYaw = (float) (MathHelper.atan2(entity.motionX, entity.motionZ) * (180D / Math.PI));
		for (entity.rotationPitch = (float) (MathHelper.atan2(entity.motionY, (double) horizontalMotion) * (180D / Math.PI)); entity.rotationPitch - entity.prevRotationPitch < -180.0F; entity.prevRotationPitch -= 360.0F);
		while (entity.rotationPitch - entity.prevRotationPitch >= 180F) entity.prevRotationPitch += 360F;
		while (entity.rotationYaw - entity.prevRotationYaw < -180F) entity.prevRotationYaw -= 360F;
		while (entity.rotationYaw - entity.prevRotationYaw >= 180F) entity.prevRotationYaw += 360F;
		entity.rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * speed;
		entity.rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * speed;
	}

	/**
	 * Returns the side of the given hand depending on the given entity's primary hand.
	 */
	public static EnumHandSide getHandSide(EntityLivingBase entity, EnumHand hand)
	{
		return getHandSide(entity.getPrimaryHand(), hand);
	}

	/**
	 * Returns the side of the given hand depending on the given primary hand.
	 */
	public static EnumHandSide getHandSide(EnumHandSide primarySide, EnumHand hand)
	{
		return hand == EnumHand.MAIN_HAND && primarySide == EnumHandSide.RIGHT || hand == EnumHand.OFF_HAND && primarySide == EnumHandSide.LEFT ? EnumHandSide.RIGHT : EnumHandSide.LEFT;
	}

	// TODO Rename?
	/**
	 * Rotates the given point the given entity's pitch and yaw angles. Rotates over the pitch axis, then the yaw axis. Mirrors the x coordinate depending on the given hand.
	 */
	public static Vec3d getHeldOffset(EntityLivingBase entity, EnumHand hand, Vec3d point)
	{
		if(getHandSide(entity, hand) == EnumHandSide.LEFT) point = new Vec3d(point.x * -1D, point.y, point.z);
		return rotateVector(point, entity.rotationYaw, entity.rotationPitch);
	}

	/**
	 * Checks if the given player is either in creative mode or has at least one stack of the given item.
	 */
	public static boolean hasItem(EntityPlayer player, Item item)
	{
		return player.isCreative() || !findStacks(player.inventoryContainer.getInventory(), item).isEmpty();
	}

	/**
	 * Checks if the given player is in creative or has enough of the given item and consumes that amount.
	 */
	public static boolean consumeItem(EntityPlayer player, Item item, int amount)
	{
		if(amount <= 0 || player.isCreative()) return true;
		ArrayList<ItemStack> stacks = findStacks(player.inventoryContainer.getInventory(), item);
		if(getStackTotal(stacks) < amount) return false;
		Iterator<ItemStack> iterator = stacks.iterator();
		while(amount > 0 && iterator.hasNext())
		{
			ItemStack stack = iterator.next();
			int size = stack.getCount();
			stack.shrink(amount);
			amount -= size;
		}
		return amount <= 0;
	}



	/**
	 * 
	 * Damage
	 * 
	 */

	// TODO Better way?
	/**
	 * Checks if the given entity is being hurt by a continuous damage source.
	 */
	public static boolean isContinuous(DamageSource source, EntityLivingBase entity)
	{
		return
				source == DamageSource.DROWN ||
				source.isFireDamage() && entity.isBurning() ||
				source == DamageSource.IN_WALL ||
				source == DamageSource.CRAMMING ||
				source == DamageSource.CACTUS ||
				source == DamageSource.WITHER ||
				source == DamageSource.DRAGON_BREATH ||
				source == DamageSource.MAGIC && hasBadPotionEffect(entity);
	}



	/*
	 * 
	 * Armor
	 * 
	 */

	/**
	 * Checks if the given slot is an armor slot.
	 */
	public static boolean isArmorSlot(EntityEquipmentSlot slot)
	{
		return
				slot == EntityEquipmentSlot.HEAD ||
				slot == EntityEquipmentSlot.CHEST ||
				slot == EntityEquipmentSlot.LEGS ||
				slot == EntityEquipmentSlot.FEET;
	}

	/**
	 * Checks if the given entity is wearing a full suit of hazard armor.
	 */
	public static boolean isWearingHazard(EntityLivingBase entity)
	{
		return
				entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == LambdaItems.suit_hazard_head &&
				entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == LambdaItems.suit_hazard_chest &&
				entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == LambdaItems.suit_hazard_legs &&
				entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == LambdaItems.suit_hazard_boots;
	}



	/*
	 * 
	 * Ray tracing
	 * 
	 */

	/**
	 * Returns a new axis aligned bounding box with the given start and end positions. This method is a replacement for the exclusively client constructor.
	 */
	public static AxisAlignedBB createAABB(Vec3d start, Vec3d end)
	{
		return new AxisAlignedBB(start.x, start.y, start.z, end.x, end.y, end.z);
	}

	/**
	 * Returns the center position of  the given axis aligned bounding box. This method is a replacement for the exclusively client method.
	 */
	public static Vec3d getCenter(AxisAlignedBB box)
	{
		return new Vec3d(box.minX * 0.5D + box.maxX * 0.5D, box.minY * 0.5D + box.maxY * 0.5D, box.minZ * 0.5D + box.maxZ * 0.5D);
	}

	// TODO Check for 0 division and if line vec is integers
	/**
	 * Returns all blocks intersecting a ray given its start and end points with the given parameters in the exact order they are intersected by the ray. Stops at the first collidable block that satisfies the given condition, if any.
	 * @see <a href="http://www.cse.yorku.ca/~amana/research/grid.pdf">source</a>
	 */
	public static ArrayList<RayTraceResult> rayTraceBlocks(World world, Vec3d start, Vec3d end, boolean checkLiquids, boolean checkBounds, Predicate<IBlockState> ignoredBlocks)
	{
		Vec3d line = end.subtract(start);
		int x = (int) Math.floor(start.x), y = (int) Math.floor(start.y), z = (int) Math.floor(start.z);
		int lastX = (int) Math.floor(end.x), lastY = (int) Math.floor(end.y), lastZ = (int) Math.floor(end.z);
		Vec3i step = new Vec3i((int) Math.signum(line.x), (int) Math.signum(line.y), (int) Math.signum(line.z));
		Vec3d delta = new Vec3d(1D / line.x * (double) step.getX(), 1D / line.y * (double) step.getY(), 1D / line.z * (double) step.getZ());
		double tMaxX = ((double) x + (step.getX() < 0 ? 0D : 1D) - start.x) / line.x, tMaxY = ((double) y + (step.getY() < 0 ? 0D : 1D) - start.y) / line.y, tMaxZ = ((double) z + (step.getZ() < 0 ? 0D : 1D) - start.z) / line.z;
		ArrayList<RayTraceResult> results = Lists.newArrayList();
		BlockPos position = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(position);
		if((!checkBounds || state.getCollisionBoundingBox(world, position) != Block.NULL_AABB) && state.getBlock().canCollideCheck(state, checkLiquids))
		{
			RayTraceResult result = state.collisionRayTrace(world, position, start, end);
			if(result != null)
			{
				results.add(result);
				if(ignoredBlocks == null || !ignoredBlocks.apply(state)) return results;
			}
		}
		while(x != lastX || y != lastY || z != lastZ)
		{
			if(tMaxX < tMaxY)
			{
				if(tMaxX < tMaxZ)
				{
					x += step.getX();
					tMaxX += delta.x;
				}
				else
				{
					z += step.getZ();
					tMaxZ += delta.z;
				}
			}
			else
			{
				if(tMaxY < tMaxZ)
				{
					y += step.getY();
					tMaxY += delta.y;
				}
				else
				{
					z += step.getZ();
					tMaxZ += delta.z;
				}
			}
			position = new BlockPos(x, y, z);
			state = world.getBlockState(position);
			if((!checkBounds || state.getCollisionBoundingBox(world, position) != Block.NULL_AABB) && state.getBlock().canCollideCheck(state, checkLiquids))
			{
				RayTraceResult result = state.collisionRayTrace(world, position, start, end);
				if(result != null)
				{
					results.add(result);
					if(ignoredBlocks == null || !ignoredBlocks.apply(state)) return results;
				}
			}
		}
		return results;
	}

	/**
	 * Ray traces all blocks between the given entity's eyes and the position it is looking at with the given parameters and length restriction.
	 */
	public static RayTraceResult rayTraceBlocks(Entity entity, double length, boolean checkLiquids, boolean checkBounds)
	{
		return entity.world.rayTraceBlocks(
				new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ),
				entity.getLookVec().scale(length).addVector(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ),
				checkLiquids,
				checkBounds,
				false);
	}

	// TODO Predicate
	/**
	 * Returns all matching entities intersecting a ray given its start and end points.
	 */
	public static ArrayList<RayTraceResult> rayTraceEntities(World world, Vec3d start, Vec3d end, Collection<Entity> ignore, Predicate<Entity> filter)
	{
		ArrayList<RayTraceResult> results = Lists.newArrayList();
		Iterator<Entity> iterator = LambdaUtilities.subtract(world.getEntitiesWithinAABBExcludingEntity(null, LambdaUtilities.createAABB(start, end).grow(1D)), ignore).iterator();
		while(iterator.hasNext())
		{
			Entity entity = iterator.next();
			if(entity.canBeCollidedWith() && !entity.noClip && (filter == null || filter.apply(entity)))
			{
				AxisAlignedBB box = entity.getEntityBoundingBox().grow(0.3D); // TODO Change?
				RayTraceResult result1 = box.calculateIntercept(start, end);
				if(result1 != null) results.add(new RayTraceResult(entity, result1.hitVec));
			}
		}
		return results;
	}

	/**
	 * Returns the closest matching entity intersecting a ray given its start and end points.
	 */
	public static RayTraceResult rayTraceClosestEntity(World world, Vec3d start, Vec3d end, Collection<Entity> ignore, Predicate<Entity> filter)
	{
		RayTraceResult result = null;
		Iterator<Entity> iterator = LambdaUtilities.subtract(world.getEntitiesWithinAABBExcludingEntity(null, LambdaUtilities.createAABB(start, end).grow(1D)), ignore).iterator();
		double distanceMinimum = 0D;
		while(iterator.hasNext())
		{
			Entity entity = iterator.next();
			if(entity.canBeCollidedWith() && !entity.noClip && (filter == null || filter.apply(entity)))
			{
				AxisAlignedBB box = entity.getEntityBoundingBox().grow(0.3D); // TODO Change?
				RayTraceResult result1 = box.calculateIntercept(start, end);
				if(result1 != null)
				{
					double distance = start.squareDistanceTo(result1.hitVec);
					if(distanceMinimum == 0D || distance < distanceMinimum)
					{
						result = new RayTraceResult(entity, result1.hitVec);
						distanceMinimum = distance;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returns the closest matching entity to a given point from the given collection of intersected entities.
	 */
	public static RayTraceResult getClosestIntersectedEntity(Vec3d start, Collection<RayTraceResult> entities, Predicate<Entity> filter)
	{
		RayTraceResult result = null;
		Iterator<RayTraceResult> iterator = entities.iterator();
		double distanceMinimum = 0D;
		while(iterator.hasNext())
		{
			RayTraceResult result1 = iterator.next();
			if(filter == null || filter.apply(result1.entityHit))
			{
				double distance = start.squareDistanceTo(result1.hitVec);
				if(distanceMinimum == 0D || distance < distanceMinimum)
				{
					result = result1;
					distanceMinimum = distance;
				}
			}
		}
		return result;
	}

	/**
	 * Returns the closest matching entity to the given position from the given collection.
	 */
	public static Entity getClosestEntity(Vec3d start, Collection<Entity> entities, Predicate<Entity> filter)
	{
		Entity target = null;
		double distanceMinimum = 0D;
		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext())
		{
			Entity entity = iterator.next();
			if(filter == null || filter.apply(entity))
			{
				double distance = entity.getDistanceSq(start.x, start.y, start.z);
				if (distanceMinimum == 0D || distance < distanceMinimum)
				{
					target = entity;
					distanceMinimum = distance;
				}
			}
		}
		return target;
	}

	/*

	// Ray traces all the blocks and matching entities between the given start and end positions with the given parameters.

	public static ArrayList<Entity> rayTraceBlocksAndEntities(World world, Vec3d start, Vec3d end, boolean checkLiquids, boolean ignoreBounds, Collection<Entity> ignore)
	{
		RayTraceResult result = world.rayTraceBlocks(start, end, checkLiquids, ignoreBounds, false);
		if(result != null) end = result.hitVec;
		return rayTraceEntities(world, start, end, checkLiquids, ignoreBounds, ignore);
	}

	// Ray traces all the blocks and matching entities between the given entity's  eyes and the position it is looking at with the given parameters and length restriction.

	public static ArrayList<Entity> rayTraceEntities(Entity entity, double length, boolean checkLiquids, boolean ignoreBounds)
	{
		Vec3d start = new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
		return rayTraceBlocksAndEntities(
				entity.world,
				start,
				entity.getLookVec().scale(length).add(start),
				checkLiquids,
				ignoreBounds,
				Lists.newArrayList(entity));
	}

	// Returns the closest matching entity from all the entities ray traced between the given entity's eyes to the position it is looking at with the given parameters and length restriction.

	public static Entity rayTraceClosestEntity(Entity entity, double length, boolean checkLiquids, boolean ignoreBounds, Predicate<Entity> filter)
	{
		return getClosestEntity(entity, rayTraceEntities(entity, length, checkLiquids, ignoreBounds), filter);
	}

	 // Returns the closest matching entity to the given entity's eyes from the given collection.

	public static Entity getClosestEntity(Entity entity, Collection<Entity> entities, Predicate<Entity> filter)
	{
		return getClosestEntity(new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ), entities, filter);
	}

	 // Pretty much copied from {@link ProjectileHelper}.

	public static RayTraceResult forwardsRaycast(Entity projectile, boolean checkLiquids, boolean checkBounds, Collection<Entity> ignore, Predicate<Entity> filter)
	{
		Vec3d start = projectile.getPositionVector();
		Vec3d end = start.addVector(projectile.motionX, projectile.motionY, projectile.motionZ);
		RayTraceResult result = projectile.world.rayTraceBlocks(start, end, false, true, false);
		if (result != null) end = new Vec3d(result.hitVec.x, result.hitVec.y, result.hitVec.z);
		Entity target = null;
		// Grow 1?
		Iterator<Entity> iterator = LambdaUtilities.subtract(projectile.world.getEntitiesWithinAABBExcludingEntity(projectile, projectile.getEntityBoundingBox().expand(projectile.motionX, projectile.motionY, projectile.motionZ).grow(1D)), ignore).iterator();
		double distanceMinimum = 0D;
		while(iterator.hasNext())
		{
			Entity entity = iterator.next();
			if (entity.canBeCollidedWith() && !entity.noClip && (filter == null || filter.apply(entity)))
			{
				AxisAlignedBB box = entity.getEntityBoundingBox().grow(0.3D);
				RayTraceResult result1 = box.calculateIntercept(start, end);
				if (result1 != null)
				{
					double distance = start.squareDistanceTo(result1.hitVec);
					if (distance < distanceMinimum || distanceMinimum == 0.0D)
					{
						target = entity;
						distanceMinimum = distance;
					}
				}
			}
		}
		if (target != null) result = new RayTraceResult(target);
		return result;
	}
	 */



	/*
	 * 
	 * Tiles
	 * 
	 */

	// TODO Fix offset issues
	// TODO Use rayTraceBlocks instead?
	/**
	 * Returns the first matching block in the given direction starting from the given position with a given range restriction.
	 */
	public static BlockPos getFirstBlockInDirection(World world, BlockPos start, EnumFacing direction, Predicate<IBlockState> condition, int range)
	{
		BlockPos end = start;
		for(int a = 0; a < range; ++a)
		{
			end = end.offset(direction);
			if(condition.apply(world.getBlockState(end))) return end;
		}
		return end;
	}



	/*
	 * 
	 * Rendering
	 * 
	 */

	/**
	 * Returns the client player's interpolated position.
	 */
	public static Vec3d getRenderOrigin(float partialTick)
	{
		Minecraft mc = Minecraft.getMinecraft();
		return new Vec3d(
				mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double) partialTick,
				mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double) partialTick,
				mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double) partialTick);
	}

	// TODO Make sure quad is centered at given points
	/**
	 * Draws a 'flat' quad that always faces the camera's look vector with given start and end points and parameters.
	 */
	public static void drawFlatQuad(Vec3d start, Vec3d end, double width, int color, boolean stretch, float partialTick)
	{
		Vec3d origin = getRenderOrigin(partialTick);
		Vec3d camera = origin.addVector(0D, (double) Minecraft.getMinecraft().player.getEyeHeight(), 0D);
		Vec3d delta = end.subtract(start);
		Vec3d direction = delta.normalize();
		Vec3d startUp = direction.crossProduct(start.subtract(camera)).normalize().scale(width);
		Vec3d endUp = direction.crossProduct(end.subtract(camera)).normalize().scale(width);
		float red = getRed(color), green = getGreen(color), blue = getBlue(color), alpha = getAlpha(color);
		double length = stretch? 1D : delta.lengthVector();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(start.x + startUp.x - origin.x, start.y + startUp.y - origin.y, start.z + startUp.z - origin.z).tex(0D, 0D).color(red, green, blue, alpha).endVertex();
		buffer.pos(start.x - startUp.x - origin.x, start.y - startUp.y - origin.y, start.z - startUp.z - origin.z).tex(1D, 0D).color(red, green, blue, alpha).endVertex();
		buffer.pos(end.x - endUp.x - origin.x, end.y - endUp.y - origin.y, end.z - endUp.z - origin.z).tex(1D, length).color(red, green, blue, alpha).endVertex();
		buffer.pos(end.x + endUp.x - origin.x, end.y + endUp.y - origin.y, end.z + endUp.z - origin.z).tex(0D, length).color(red, green, blue, alpha).endVertex();
		tessellator.draw();
	}

	// TODO Push/pop attrib, others
	// TODO Translate always?
	// TODO Transform type
	/**
	 * Renders the the given model of the given item stack.
	 */
	public static void RenderItem(ModelResourceLocation resource, ItemStack stack)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(resource);
		model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GUI, false);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
		GlStateManager.popMatrix();
	}
}