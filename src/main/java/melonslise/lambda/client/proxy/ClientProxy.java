package melonslise.lambda.client.proxy;

import java.util.Iterator;

import melonslise.lambda.client.key.LambdaKeyBindings;
import melonslise.lambda.client.renderer.RenderBattery;
import melonslise.lambda.client.renderer.RenderBolt;
import melonslise.lambda.client.renderer.RenderBullet;
import melonslise.lambda.client.renderer.RenderMedkit;
import melonslise.lambda.client.renderer.RenderSentry;
import melonslise.lambda.client.renderer.alien.RenderBarnacle;
import melonslise.lambda.client.renderer.alien.RenderHeadcrab;
import melonslise.lambda.client.renderer.alien.RenderHeadcrabZombie;
import melonslise.lambda.client.renderer.alien.RenderHornet;
import melonslise.lambda.client.renderer.alien.RenderHoundeye;
import melonslise.lambda.client.renderer.alien.RenderSnark;
import melonslise.lambda.client.renderer.alien.RenderVortigaunt;
import melonslise.lambda.client.renderer.layer.LayerGluonBackpack;
import melonslise.lambda.client.renderer.tileentity.RenderLaserTripmine;
import melonslise.lambda.client.renderer.weapon.RenderCrossbow;
import melonslise.lambda.client.renderer.weapon.RenderGluon;
import melonslise.lambda.client.renderer.weapon.RenderGrenade;
import melonslise.lambda.client.renderer.weapon.RenderImpactGrenade;
import melonslise.lambda.client.renderer.weapon.RenderItemSnark;
import melonslise.lambda.client.renderer.weapon.RenderPistol;
import melonslise.lambda.client.renderer.weapon.RenderRevolver;
import melonslise.lambda.client.renderer.weapon.RenderRocket;
import melonslise.lambda.client.renderer.weapon.RenderSMG;
import melonslise.lambda.client.renderer.weapon.RenderSatchelCharge;
import melonslise.lambda.client.renderer.weapon.RenderTau;
import melonslise.lambda.common.entity.EntityGrenade;
import melonslise.lambda.common.entity.EntitySatchelCharge;
import melonslise.lambda.common.entity.EntitySentry;
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
import melonslise.lambda.common.item.LambdaItems;
import melonslise.lambda.common.proxy.ACommonProxy;
import melonslise.lambda.common.tileentity.TileEntityLaserTripmine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ACommonProxy
{
	// TODO MOve?
	@Override
	public void registerRenderers()
	{
		// ENTITIES
		IRenderFactory factory;
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderMedkit(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(CollectibleMedkit.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderBattery(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(CollectibleBattery.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderBullet(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderBolt(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderHeadcrab(manager, 0.3F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadcrab.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderHeadcrabZombie(manager, 0.5F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadcrabZombie.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderVortigaunt(manager, 0.5F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityVortigaunt.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderHoundeye(manager, 0.5F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityHoundeye.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderBarnacle(manager, 0F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityBarnacle.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderHornet(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityHornet.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderSnark(manager, 0.3F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntitySnark.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderImpactGrenade(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityImpactGrenade.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderRocket(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderGrenade(manager, Minecraft.getMinecraft().getRenderItem());
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderSatchelCharge(manager);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchelCharge.class, factory);
		factory = new IRenderFactory()
		{
			@Override
			public Render createRenderFor(RenderManager manager)
			{
				return new RenderSentry(manager, 0.4F);
			}
		};
		RenderingRegistry.registerEntityRenderingHandler(EntitySentry.class, factory);

		// TILE ENTITIES
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserTripmine.class, new RenderLaserTripmine());

		// ITEMS
		LambdaItems.weapon_pistol.setTileEntityItemStackRenderer(new RenderPistol());
		LambdaItems.weapon_smg.setTileEntityItemStackRenderer(new RenderSMG());
		LambdaItems.weapon_revolver.setTileEntityItemStackRenderer(new RenderRevolver());
		LambdaItems.weapon_crossbow.setTileEntityItemStackRenderer(new RenderCrossbow());
		LambdaItems.weapon_tau.setTileEntityItemStackRenderer(new RenderTau());
		LambdaItems.weapon_gluon.setTileEntityItemStackRenderer(new RenderGluon());
		LambdaItems.weapon_snark.setTileEntityItemStackRenderer(new RenderItemSnark());
	}

	public void registerKeyBindings()
	{
		LambdaKeyBindings.register();
	}

	// TODO Work for other bipeds too?
	@Override
	public void attachLayers()
	{
		Iterator<RenderPlayer> iterator = Minecraft.getMinecraft().getRenderManager().getSkinMap().values().iterator();
		while(iterator.hasNext())
		{
			RenderPlayer renderer = iterator.next();
			renderer.addLayer(new LayerGluonBackpack(renderer));
		}
	}
}