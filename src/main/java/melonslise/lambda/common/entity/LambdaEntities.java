package melonslise.lambda.common.entity;

import melonslise.lambda.common.entity.alien.EntityBarnacle;
import melonslise.lambda.common.entity.alien.EntityHeadcrab;
import melonslise.lambda.common.entity.alien.EntityHeadcrabZombie;
import melonslise.lambda.common.entity.alien.EntityHoundeye;
import melonslise.lambda.common.entity.alien.EntitySnark;
import melonslise.lambda.common.entity.alien.EntityVortigaunt;
import melonslise.lambda.common.entity.collectible.CollectibleBattery;
import melonslise.lambda.common.entity.collectible.CollectibleMedkit;
import melonslise.lambda.common.entity.projectile.EntityBolt;
import melonslise.lambda.common.entity.projectile.EntityBullet;
import melonslise.lambda.common.entity.projectile.EntityHornet;
import melonslise.lambda.common.entity.projectile.EntityImpactGrenade;
import melonslise.lambda.common.entity.projectile.EntityRocket;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

// TODO tracker stats
// TODO Registerrenderers here
// TODO Which entities dont need motion updates?
public class LambdaEntities
{
	private static int id = 0;

	public static EntityEntry
	medkit = createEntry(CollectibleMedkit.class, LambdaUtilities.prefixLambda("medkit"), 64, 20, false),
	battery = createEntry(CollectibleBattery.class, LambdaUtilities.prefixLambda("battery"), 64, 20, false),

	bullet = createEntry(EntityBullet.class, LambdaUtilities.prefixLambda("bullet"), 80, 20, true),
	bolt = createEntry(EntityBolt.class, LambdaUtilities.prefixLambda("bolt"), 80, 20, true),

	headcrab = createEntry(EntityHeadcrab.class, LambdaUtilities.prefixLambda("headcrab"), 80, 3, true, 0xffdd77, 0xffdd77),
	zombie = createEntry(EntityHeadcrabZombie.class, LambdaUtilities.prefixLambda("headcrab_zombie"), 80, 3, true, 0xffe2dd, 0xe26961),
	vortigaunt = createEntry(EntityVortigaunt.class, LambdaUtilities.prefixLambda("vortigaunt"), 80, 3, true, 0x724735, 0x4df253),
	houndeye = createEntry(EntityHoundeye.class, LambdaUtilities.prefixLambda("houndeye"), 80, 3, true, 0xffe16b, 0x3fcfff),
	barnacle = createEntry(EntityBarnacle.class, LambdaUtilities.prefixLambda("barnacle"), 80, 20, false, 0x990f0f, 0xed4e4e),
	hornet = createEntry(EntityHornet.class, LambdaUtilities.prefixLambda("hornet"), 80, 3, true),
	snark = createEntry(EntitySnark.class, LambdaUtilities.prefixLambda("snark"), 80, 3, true),

	impact_grenade = createEntry(EntityImpactGrenade.class, LambdaUtilities.prefixLambda("impact_grenade"), 64, 20, true),
	rocket = createEntry(EntityRocket.class, LambdaUtilities.prefixLambda("rocket"), 80, 3, true),
	grenade = createEntry(EntityGrenade.class, LambdaUtilities.prefixLambda("grenade"), 64, 20, true),
	satchel_charge = createEntry(EntitySatchelCharge.class, LambdaUtilities.prefixLambda("satchel_charge"), 64, 20, true),

	sentry = createEntry(EntitySentry.class, LambdaUtilities.prefixLambda("sentry"), 64, 10, true);

	private LambdaEntities() {};

	public static void register(RegistryEvent.Register<EntityEntry> event)
	{
		event.getRegistry().registerAll
		(
			medkit,
			battery,
			bullet,
			bolt,
			headcrab,
			zombie,
			vortigaunt,
			houndeye,
			barnacle,
			hornet,
			snark,
			impact_grenade,
			rocket,
			grenade,
			satchel_charge,
			sentry
		);
	}

	// TODO Change tracker? and add factory
	private static EntityEntry createEntry(Class c, String name, int range, int frequency, boolean updates)
	{
		return EntityEntryBuilder.create().id(LambdaUtilities.createLambdaDomain(name), ++id).name(name).entity(c).tracker(range, frequency, updates).build();
	}

	private static EntityEntry createEntry(Class c, String name, int range, int frequency, boolean updates, int color1, int color2)
	{
		return EntityEntryBuilder.create().id(LambdaUtilities.createLambdaDomain(name), ++id).name(name).entity(c).tracker(range, frequency, updates).egg(color1, color2).build();
	}
}