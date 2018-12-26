package melonslise.lambda.client.model.alien;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ModelSnark extends ModelBase
{
	protected ModelRenderer head, body1, body2, body3, leg1, leg2, leg3, leg4;

	public ModelSnark()
	{
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.head.setRotationPoint(1F, 20F, -4F);
		this.head.setTextureSize(64, 32);
		this.head.mirror = true;
		this.setRotation(head, 0F, 0F, 0F);
		this.body1 = new ModelRenderer(this, 0, 4);
		this.body1.addBox(-2F, -1F, 0F, 4, 2, 3);
		this.body1.setRotationPoint(1F, 21F, -4F);
		this.body1.setTextureSize(64, 32);
		this.body1.mirror = true;
		this.setRotation(body1, 0.2094395F, 0F, 0F);
		this.body2 = new ModelRenderer(this, 0, 16);
		this.body2.addBox(-2F, -1F, -1F, 4, 2, 4);
		this.body2.setRotationPoint(1F, 20F, -1F);
		this.body2.setTextureSize(64, 32);
		this.body2.mirror = true;
		this.setRotation(body2, -0.2094395F, 0F, 0F);
		this.body3 = new ModelRenderer(this, 0, 9);
		this.body3.addBox(-1F, 0F, -3F, 2, 2, 5);
		this.body3.setRotationPoint(1F, 21F, 0F);
		this.body3.setTextureSize(64, 32);
		this.body3.mirror = true;
		this.setRotation(body3, 0F, 0F, 0F);
		this.leg1 = new ModelRenderer(this, 8, 16);
		this.leg1.addBox(-1F, 0F, 0F, 1, 3, 1);
		this.leg1.setRotationPoint(1F, 23F, 1F);
		this.leg1.setTextureSize(64, 32);
		this.leg1.mirror = true;
		this.setRotation(leg1, 0.0174533F, 0.0174533F, 1.047198F);
		this.leg2 = new ModelRenderer(this, 8, 0);
		this.leg2.addBox(0F, 0F, 0F, 1, 3, 1);
		this.leg2.setRotationPoint(1F, 23F, 1F);
		this.leg2.setTextureSize(64, 32);
		this.leg2.mirror = true;
		this.setRotation(leg2, 0F, 0F, -1.117011F);
		this.leg3 = new ModelRenderer(this, 8, 0);
		this.leg3.addBox(-1F, 0F, 0F, 1, 3, 1);
		this.leg3.setRotationPoint(1F, 23F, -2F);
		this.leg3.setTextureSize(64, 32);
		this.leg3.mirror = true;
		this.setRotation(leg3, 0F, 0F, 1.117011F);
		this.leg4 = new ModelRenderer(this, 8, 0);
		this.leg4.addBox(0F, 0F, 0F, 1, 3, 1);
		this.leg4.setRotationPoint(1F, 23F, -2F);
		this.leg4.setTextureSize(64, 32);
		this.leg4.mirror = true;
		this.setRotation(leg4, 0F, 0F, -1.117011F);
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
		this.head.render(scale);
		this.body1.render(scale);
		this.body2.render(scale);
		this.body3.render(scale);
		this.leg1.render(scale);
		this.leg2.render(scale);
		this.leg3.render(scale);
		this.leg4.render(scale);
	}

	@Override
	public void setRotationAngles(float swing, float swingAmount, float age, float headYaw, float headPitch, float scale, Entity entity)
	{
		super.setRotationAngles(swing, swingAmount, age, headYaw, headPitch, scale, entity);
		this.head.rotateAngleY = headYaw / (180F / (float) Math.PI);
		this.head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.leg1.rotateAngleX = MathHelper.cos(swing * 4.0F) * 0.5F * swingAmount;
		this.leg2.rotateAngleX = MathHelper.cos(swing * 4.0F + (float) Math.PI) * 0.5F * swingAmount;
		this.leg3.rotateAngleX = MathHelper.cos(swing * 4.0F + (float) Math.PI) * 0.5F * swingAmount;
		this.leg4.rotateAngleX = MathHelper.cos(swing * 4.0F) * 0.5F * swingAmount;
	}

	public void render(float scale)
	{
		this.head.render(scale);
		this.body1.render(scale);
		this.body2.render(scale);
		this.body3.render(scale);
		this.leg1.render(scale);
		this.leg2.render(scale);
		this.leg3.render(scale);
		this.leg4.render(scale);
	}

	// TODO Rename ticks
	public void setRotationAngles(float scale, float ticks)
	{
		this.head.rotateAngleY = 0.3F * MathHelper.sin(ticks + 0.25F * (float) Math.PI);
		this.body1.rotateAngleY = 0.1F * MathHelper.sin(ticks + 0.25F * (float) Math.PI);
		this.head.rotateAngleX = 0.3F * MathHelper.cos(ticks);
		this.body1.rotateAngleX = 0.1F * MathHelper.cos(ticks);
	}
}