package melonslise.lambda.client.model;

import melonslise.lambda.common.entity.EntitySentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

// TODO Move?
public class ModelSentry extends ModelBase
{
	ModelRenderer chassis, leg0, leg10, leg11, contact, side0, side1, top, sensor, gun0, gun1;

	public ModelSentry()
	{
		this.chassis = new ModelRenderer(this, 44, 4);
		this.chassis.addBox(-2.5F, 0F, -2.5F, 5, 2, 5);
		this.chassis.setRotationPoint(0F, 14F, 0F);
		this.chassis.setTextureSize(64, 32);
		this.chassis.mirror = true;
		this.setRotation(chassis, 0F, 0F, 0F);
		this.leg0 = new ModelRenderer(this, 51, 11);
		this.leg0.addBox(-1F, 0F, 0F, 2, 9, 1);
		this.leg0.setRotationPoint(0F, 16F, 1F);
		this.leg0.setTextureSize(64, 32);
		this.leg0.mirror = true;
		this.setRotation(leg0, 0.3141593F, 0F, 0F);
		this.leg10 = new ModelRenderer(this, 60, 11);
		this.leg10.addBox(-1F, 0F, -1F, 1, 9, 1);
		this.leg10.setRotationPoint(-1F, 16F, -1F);
		this.leg10.setTextureSize(64, 32);
		this.leg10.mirror = true;
		this.setRotation(leg10, -0.4363323F, 0.2617994F, 0F);
		this.leg11 = new ModelRenderer(this, 60, 11);
		this.leg11.addBox(-1F, 0F, -1F, 1, 9, 1);
		this.leg11.setRotationPoint(1.7F, 16F, -1F);
		this.leg11.setTextureSize(64, 32);
		this.leg11.mirror = true;
		this.setRotation(leg11, -0.4363323F, -0.2617994F, 0F);
		this.contact = new ModelRenderer(this, 56, 0);
		this.contact.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.contact.setRotationPoint(0F, 12.5F, 0F);
		this.contact.setTextureSize(64, 32);
		this.contact.mirror = true;
		this.setRotation(contact, 0F, 0F, 0F);
		this.side0 = new ModelRenderer(this, 0, 8);
		this.side0.addBox(1F, 0F, -1.5F, 1, 11, 3);
		this.side0.setRotationPoint(0F, 2F, 0F);
		this.side0.setTextureSize(64, 32);
		this.side0.mirror = true;
		this.setRotation(side0, 0F, 0F, 0F);
		this.side1 = new ModelRenderer(this, 0, 8);
		this.side1.addBox(0F, 0F, 0F, 1, 11, 3);
		this.side1.setRotationPoint(-2F, 2F, -1.5F);
		this.side1.setTextureSize(64, 32);
		this.side1.mirror = true;
		this.setRotation(side1, 0F, 0F, 0F);
		this.top = new ModelRenderer(this, 0, 0);
		this.top.addBox(0F, 0F, -1.5F, 2, 1, 3);
		this.top.setRotationPoint(-1F, 2F, 0F);
		this.top.setTextureSize(64, 32);
		this.top.mirror = true;
		this.setRotation(top, 0F, 0F, 0F);
		this.sensor = new ModelRenderer(this, 0, 4);
		this.sensor.addBox(-1F, -1F, -1F, 2, 1, 2);
		this.sensor.setRotationPoint(1F, 2F, 0F);
		this.sensor.setTextureSize(64, 32);
		this.sensor.mirror = true;
		this.setRotation(sensor, 0F, 0F, 0F);
		this.gun0 = new ModelRenderer(this, 16, 0);
		this.gun0.addBox(-1F, -1F, -1F, 2, 7, 2);
		this.gun0.setRotationPoint(0F, 5F, 0F);
		this.gun0.setTextureSize(64, 32);
		this.gun0.mirror = true;
		this.setRotation(gun0, 0F, 0F, 0F);
		this.gun1 = new ModelRenderer(this, 24, 0);
		this.gun1.addBox(0F, 0F, -0.5F, 2, 3, 2);
		this.gun1.setRotationPoint(-1F, 5F, 1F);
		this.gun1.setTextureSize(64, 32);
		this.gun1.mirror = true;
		this.setRotation(gun1, 0F, 0F, 0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	// TODO Move the rotation code elsewhere?
	@Override
	public void render(Entity entity, float swing, float swingAmount, float age, float headYaw, float headPitch, float scale)
	{
		super.render(entity, swing, swingAmount, age, headYaw, headPitch, scale);
		float partialTick = Minecraft.getMinecraft().getRenderPartialTicks();
		GlStateManager.pushMatrix();
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTick, 0F, 1F, 0F);
		this.chassis.render(scale);
		this.leg0.render(scale);
		this.leg10.render(scale);
		this.leg11.render(scale);
		this.contact.render(scale);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float rotation = headYaw;
		if(entity instanceof EntitySentry)
		{
			EntitySentry sentry = (EntitySentry) entity;
			rotation = sentry.rotationYawTopOld + (sentry.rotationYawTop - sentry.rotationYawTopOld) * partialTick;
		}
		GlStateManager.rotate(rotation, 0F, 1F, 0F);
		this.side0.render(scale);
		this.side1.render(scale);
		this.top.render(scale);
		this.sensor.render(scale);
		this.gun0.render(scale);
		this.gun1.render(scale);
		GlStateManager.popMatrix();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		EntitySentry sentry = (EntitySentry) entity;
		if(sentry.active)
		{
			// TODO Sentry smooth activation
			/*
			if(sentry.activationCounter < 20)
			{
				float rotation = 1.570796F * sentry.activationCounter / 20;
				gun0.rotateAngleX = -rotation;
				gun1.rotateAngleX = rotation;
				sensor.rotateAngleZ = rotation;
			}
			 */
			float rotation = -0.01745329F * (sentry.prevRotationPitch + (sentry.rotationPitch - sentry.prevRotationPitch) * Minecraft.getMinecraft().getRenderPartialTicks());
			if(rotation > 1F) rotation = 1F;
			else if(rotation < -1F) rotation = -1F;
			// Swap rotation to + to reverse the angle
			this.gun0.rotateAngleX = -1.570796F - rotation;
			this.gun1.rotateAngleX = 1.570796F - rotation;
			this.sensor.rotateAngleZ = 1.570796F;
		}
		else this.gun0.rotateAngleX = this.gun1.rotateAngleX = this.sensor.rotateAngleZ = 0F;
	}
}