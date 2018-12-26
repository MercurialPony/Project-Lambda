package melonslise.lambda.client.model.alien;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelHoundeye extends ModelBase
{
	protected ModelRenderer head, head0, body, body0, bleg0, lleg0, rleg0, back0, back1, back2, lleg1, rleg1, bleg1, rleg2, lleg2, bleg2;

	public ModelHoundeye()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4F, -4F, -3.666667F, 8, 8, 5);
		this.head.setRotationPoint(0F, 15F, -5F);
		this.head.setTextureSize(64, 64);
		this.head.mirror = true;
		this.setRotation(head, 0F, 0F, 0F);
		this.head0 = new ModelRenderer(this, 0, 40);
		this.head0.addBox(-4F, -3F, -1F, 8, 3, 4);
		this.head0.setRotationPoint(0F, 14F, -5F);
		this.head0.setTextureSize(64, 64);
		this.head0.mirror = true;
		this.setRotation(head0, 0.2094395F, 0F, 0F);
		this.body = new ModelRenderer(this, 0, 13);
		this.body.addBox(-4F, -3F, 0F, 8, 6, 8);
		this.body.setRotationPoint(0F, 16F, -4F);
		this.body.setTextureSize(64, 64);
		this.body.mirror = true;
		this.setRotation(body, 0F, 0F, 0F);
		this.body0 = new ModelRenderer(this, 0, 27);
		this.body0.addBox(-3F, -1F, 0F, 6, 4, 9);
		this.body0.setRotationPoint(0F, 12F, -3F);
		this.body0.setTextureSize(64, 64);
		this.body0.mirror = true;
		this.setRotation(body0, -0.2443461F, 0F, 0F);
		this.bleg0 = new ModelRenderer(this, 32, 13);
		this.bleg0.addBox(-2F, 0F, -1F, 4, 7, 4);
		this.bleg0.setRotationPoint(0F, 14F, 3F);
		this.bleg0.setTextureSize(64, 64);
		this.bleg0.mirror = true;
		this.setRotation(bleg0, 0.2094395F, 0F, 0F);
		this.lleg0 = new ModelRenderer(this, 26, 0);
		this.lleg0.addBox(0F, 0F, -2F, 2, 6, 4);
		this.lleg0.setRotationPoint(4F, 15F, -5F);
		this.lleg0.setTextureSize(64, 64);
		this.lleg0.mirror = true;
		this.setRotation(lleg0, 0F, 0F, 0F);
		this.rleg0 = new ModelRenderer(this, 26, 0);
		this.rleg0.addBox(-2F, 0F, -2F, 2, 6, 4);
		this.rleg0.setRotationPoint(-4F, 15F, -5F);
		this.rleg0.setTextureSize(64, 64);
		this.rleg0.mirror = true;
		this.setRotation(rleg0, 0F, 0F, 0F);
		this.back0 = new ModelRenderer(this, 0, 47);
		this.back0.addBox(-4F, -1F, -1F, 8, 2, 3);
		this.back0.setRotationPoint(0F, 13F, -2F);
		this.back0.setTextureSize(64, 64);
		this.back0.mirror = true;
		this.setRotation(back0, 0.6981317F, 0F, 0F);
		this.back1 = new ModelRenderer(this, 0, 52);
		this.back1.addBox(-3F, -1F, 0F, 6, 2, 2);
		this.back1.setRotationPoint(0F, 14F, 1.466667F);
		this.back1.setTextureSize(64, 64);
		this.back1.mirror = true;
		this.setRotation(back1, 0.837758F, 0F, 0F);
		this.back2 = new ModelRenderer(this, 0, 56);
		this.back2.addBox(-2F, -2F, 0F, 4, 2, 2);
		this.back2.setRotationPoint(0F, 15F, 4F);
		this.back2.setTextureSize(64, 64);
		this.back2.mirror = true;
		this.setRotation(back2, 0.3839724F, 0F, 0F);
		this.lleg1 = new ModelRenderer(this, 38, 0);
		this.lleg1.addBox(0F, 5F, 1F, 2, 4, 2);
		this.lleg1.setRotationPoint(4F, 15F, -5F);
		this.lleg1.setTextureSize(64, 64);
		this.lleg1.mirror = true;
		this.setRotation(lleg1, -0.2792527F, 0F, 0F);
		this.rleg1 = new ModelRenderer(this, 38, 0);
		this.rleg1.addBox(-2F, 4F, 1.3F, 2, 4, 2);
		this.rleg1.setRotationPoint(-4F, 15F, -5F);
		this.rleg1.setTextureSize(64, 64);
		this.rleg1.mirror = true;
		this.setRotation(rleg1, -0.2792527F, 0F, 0F);
		this.bleg1 = new ModelRenderer(this, 38, 0);
		this.bleg1.addBox(-1F, 0F, -1F, 2, 4, 2);
		this.bleg1.setRotationPoint(0F, 20F, 5F);
		this.bleg1.setTextureSize(64, 64);
		this.bleg1.mirror = true;
		this.setRotation(bleg1, 0.2792527F, 0F, 0F);
		this.rleg2 = new ModelRenderer(this, 26, 10);
		this.rleg2.addBox(-2F, 8F, -2F, 2, 1, 3);
		this.rleg2.setRotationPoint(-4F, 15F, -5F);
		this.rleg2.setTextureSize(64, 64);
		this.rleg2.mirror = true;
		this.setRotation(rleg2, 0F, 0F, 0F);
		this.lleg2 = new ModelRenderer(this, 26, 10);
		this.lleg2.addBox(0F, 8F, -2F, 2, 1, 3);
		this.lleg2.setRotationPoint(4F, 15F, -5F);
		this.lleg2.setTextureSize(64, 64);
		this.lleg2.mirror = true;
		this.setRotation(lleg2, 0F, 0F, 0F);
		this.bleg2 = new ModelRenderer(this, 26, 10);
		this.bleg2.addBox(-1F, 3F, -1F, 2, 1, 3);
		this.bleg2.setRotationPoint(0F, 20F, 5F);
		this.bleg2.setTextureSize(64, 64);
		this.bleg2.mirror = true;
		this.setRotation(bleg2, 0F, 0F, 0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(Entity entity, float swing, float swingAmount, float age, float yaw, float pitch, float scale)
	{
		super.render(entity, swing, swingAmount, age, yaw, pitch, scale);
		head.render(scale);
		head0.render(scale);
		body.render(scale);
		body0.render(scale);
		bleg0.render(scale);
		lleg0.render(scale);
		rleg0.render(scale);
		back0.render(scale);
		back1.render(scale);
		back2.render(scale);
		lleg1.render(scale);
		rleg1.render(scale);
		bleg1.render(scale);
		rleg2.render(scale);
		lleg2.render(scale);
		bleg2.render(scale);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float swing, float swingAmount, float partialTick)
	{
		float angle = MathHelper.cos(swing * 0.35F) * swingAmount * 0.9F, angle2 = MathHelper.cos(swing * 0.35F + (float) Math.PI) * swingAmount * 1.3F;
		this.lleg0.rotateAngleX = this.lleg1.rotateAngleX = this.lleg2.rotateAngleX = angle;
		this.rleg0.rotateAngleX = this.rleg1.rotateAngleX = this.rleg2.rotateAngleX = angle2;
		this.bleg0.rotateAngleX = this.bleg1.rotateAngleX = this.bleg2.rotateAngleX = -angle;
	}
}