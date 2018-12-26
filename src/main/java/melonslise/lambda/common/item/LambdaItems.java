package melonslise.lambda.common.item;

import melonslise.lambda.common.block.LambdaBlocks;
import melonslise.lambda.common.item.api.LambdaItem;
import melonslise.lambda.common.item.api.block.ItemBlockNamed;
import melonslise.lambda.common.item.armor.ItemSuitHazard;
import melonslise.lambda.common.item.block.ItemBlockInfo;
import melonslise.lambda.common.item.weapon.ItemCrossbow;
import melonslise.lambda.common.item.weapon.ItemCrowbar;
import melonslise.lambda.common.item.weapon.ItemGluon;
import melonslise.lambda.common.item.weapon.ItemGrenade;
import melonslise.lambda.common.item.weapon.ItemHivehand;
import melonslise.lambda.common.item.weapon.ItemPistol;
import melonslise.lambda.common.item.weapon.ItemRPG;
import melonslise.lambda.common.item.weapon.ItemRevolver;
import melonslise.lambda.common.item.weapon.ItemSMG;
import melonslise.lambda.common.item.weapon.ItemSatchelCharge;
import melonslise.lambda.common.item.weapon.ItemSatchelRemote;
import melonslise.lambda.common.item.weapon.ItemShotgun;
import melonslise.lambda.common.item.weapon.ItemSnark;
import melonslise.lambda.common.item.weapon.ItemTau;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

// TODO Proper item names?
public class LambdaItems
{
	public static Item
	suit_hazard_head = new ItemSuitHazard("suit.hazard.head", EntityEquipmentSlot.HEAD),
	suit_hazard_chest = new ItemSuitHazard("suit.hazard.chest", EntityEquipmentSlot.CHEST),
	suit_hazard_legs = new ItemSuitHazard("suit.hazard.legs", EntityEquipmentSlot.LEGS),
	suit_hazard_boots = new ItemSuitHazard("suit.hazard.boots", EntityEquipmentSlot.FEET),

	medkit = new ItemMedkit("medkit"),
	battery = new ItemBattery("battery"),
	sentry = new ItemSentry("sentry"),

	charger_health = new ItemBlockNamed(LambdaBlocks.charger_health),
	charger_power = new ItemBlockNamed(LambdaBlocks.charger_power),

	ammo_pistol = new LambdaItem("ammo.pistol"),
	ammo_revolver = new LambdaItem("ammo.revolver"),
	ammo_smg = new LambdaItem("ammo.smg"),
	ammo_smg_grenade = new LambdaItem("ammo.smg.grenade"),
	ammo_shotgun = new LambdaItem("ammo.shotgun"),
	ammo_crossbow = new LambdaItem("ammo.crossbow"),
	ammo_rpg = new LambdaItem("ammo.rpg"),
	ammo_uranium = new LambdaItem("ammo.uranium"),

	weapon_crowbar = new ItemCrowbar("weapon.crowbar", 4F, -1F),

	weapon_pistol = new ItemPistol("weapon.pistol", 17, ammo_pistol),
	weapon_revolver = new ItemRevolver("weapon.revolver", 6, ammo_revolver),

	weapon_smg = new ItemSMG("weapon.smg", 50, ammo_smg, ammo_smg_grenade),
	weapon_shotgun = new ItemShotgun("weapon.shotgun", 8, ammo_shotgun),
	weapon_crossbow = new ItemCrossbow("weapon.crossbow", 5, ammo_crossbow),

	weapon_rpg = new ItemRPG("weapon.rpg", ammo_rpg),
	weapon_tau = new ItemTau("weapon.tau", ammo_uranium),
	weapon_gluon = new ItemGluon("weapon.gluon", ammo_uranium),
	weapon_hivehand = new ItemHivehand("weapon.hivehand", 8),

	weapon_grenade = new ItemGrenade("weapon.grenade"),
	weapon_satchel_charge = new ItemSatchelCharge("weapon.satchel.charge"),
	weapon_satchel_remote = new ItemSatchelRemote("weapon.satchel.remote"),
	weapon_tripmine = new ItemBlockInfo(LambdaBlocks.weapon_tripmine),
	weapon_snark = new ItemSnark("weapon.snark");

	private LambdaItems() {};

	public static void register(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll
		(
			suit_hazard_head,
			suit_hazard_chest,
			suit_hazard_legs,
			suit_hazard_boots,
			medkit,
			battery,
			sentry,
			ammo_pistol,
			ammo_revolver,
			ammo_smg,
			ammo_smg_grenade,
			ammo_shotgun,
			ammo_crossbow,
			ammo_rpg,
			ammo_uranium,
			charger_health,
			charger_power,
			weapon_crowbar,
			weapon_pistol,
			weapon_revolver,
			weapon_smg,
			weapon_shotgun,
			weapon_crossbow,
			weapon_rpg,
			weapon_tau,
			weapon_gluon,
			weapon_hivehand,
			weapon_grenade,
			weapon_satchel_charge,
			weapon_satchel_remote,
			weapon_tripmine,
			weapon_snark
		);
	}

	// TODO figure out variants
	// TODO Path helper
	public static void registerModels()
	{
		registerModels
		(
			"", "inventory",
			charger_health,
			charger_power,
			medkit,
			battery,
			sentry
		);
		registerModels
		(
			"ammo/", "inventory",
			ammo_pistol,
			ammo_revolver,
			ammo_smg,
			ammo_smg_grenade,
			ammo_shotgun,
			ammo_crossbow,
			ammo_rpg,
			ammo_uranium
		);
		registerModels
		(
			"suit/hazard/", "inventory",
			suit_hazard_head,
			suit_hazard_chest,
			suit_hazard_legs,
			suit_hazard_boots
		);
		registerModels
		(
			"weapon/", "inventory",
			weapon_crowbar,
			weapon_pistol,
			weapon_revolver,
			weapon_smg,
			weapon_shotgun,
			weapon_crossbow,
			weapon_rpg,
			weapon_tau,
			weapon_gluon,
			weapon_hivehand,
			weapon_grenade,
			weapon_satchel_charge,
			weapon_satchel_remote,
			weapon_tripmine,
			weapon_snark
		);
		registerVariants
		(
			"weapon/gui/",
			weapon_pistol,
			weapon_revolver,
			weapon_smg,
			weapon_crossbow,
			weapon_tau,
			weapon_gluon,
			weapon_snark
		);
	}

	private static void registerModels(String path, String variant, Item... items)
	{
		for(Item item : items) registerModel(item, path, variant);
	}

	private static void registerModel(Item item, String path, String variant)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, getModel(item, path, variant));
	}

	// TODO Rename?
	// TODO Move
	public static ModelResourceLocation getModel(Item item, String path, String variant)
	{
		ResourceLocation name = item.getRegistryName();
		return new ModelResourceLocation(new ResourceLocation(name.getResourceDomain(), path + name.getResourcePath()), variant);
	}

	private static void registerVariants(String path, Item... items)
	{
		for(Item item : items) registerVariants(item, path);
	}

	// TODO WTF
	private static void registerVariants(Item item, String... paths)
	{
		ResourceLocation name = item.getRegistryName();
		ResourceLocation[] locations = new ResourceLocation[paths.length];
		for(int a = 0; a < locations.length; ++a) locations[a] = new ResourceLocation(name.getResourceDomain(), paths[a] + name.getResourcePath());
		ModelBakery.registerItemVariants(item, locations);
	}
}