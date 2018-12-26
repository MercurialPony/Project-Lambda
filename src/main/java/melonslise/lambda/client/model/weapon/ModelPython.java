package melonslise.lambda.client.model.weapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPython extends ModelBase
{
	ModelRenderer shape1, shape2, shape3, shape4, shape5, shape6, shape7, shape8, shape9;

	public ModelPython()
	{
		this.shape1 = new ModelRenderer(this, 0, 9);
		this.shape1.addBox(0F, 0F, 0F, 1, 1, 12);
		this.shape1.setRotationPoint(0F, 0F, -3F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0.7853982F);
		this.shape2 = new ModelRenderer(this, 26, 14);
		this.shape2.addBox(0F, 0F, 0F, 1, 3, 5);
		this.shape2.setRotationPoint(-0.5F, 0.5F, 5F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
		this.shape3 = new ModelRenderer(this, 12, 23);
		this.shape3.addBox(0F, 0F, 0F, 1, 1, 5);
		this.shape3.setRotationPoint(0F, 1.2F, 1.7F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0F, 0F, 0.7853982F);
		this.shape3.mirror = false;
		this.shape4 = new ModelRenderer(this, 40, 22);
		this.shape4.addBox(0F, 0F, 0F, 1, 4, 2);
		this.shape4.setRotationPoint(-0.5F, 3F, 9.5F);
		this.shape4.setTextureSize(64, 32);
		this.shape4.mirror = true;
		this.setRotation(shape4, 0.5235988F, 0.0174533F, 0F);
		this.shape5 = new ModelRenderer(this, 15, 14);
		this.shape5.addBox(0F, 0F, 0F, 2, 2, 3);
		this.shape5.setRotationPoint(0F, 0.5F, 6.466667F);
		this.shape5.setTextureSize(64, 32);
		this.shape5.mirror = true;
		this.setRotation(shape5, 0F, 0F, 0.7853982F);
		this.shape6 = new ModelRenderer(this, 38, 18);
		this.shape6.addBox(0F, 0F, 0F, 1, 1, 3);
		this.shape6.setRotationPoint(0F, 1.9F, 9F);
		this.shape6.setTextureSize(64, 32);
		this.shape6.mirror = true;
		this.setRotation(shape6, -0.5235988F, 0.4014257F, 0.7853982F);
		this.shape7 = new ModelRenderer(this, 44, 15);
		this.shape7.addBox(0F, 0F, 0F, 0, 2, 1);
		this.shape7.setRotationPoint(0F, 0F, 10F);
		this.shape7.setTextureSize(64, 32);
		this.shape7.mirror = true;
		this.setRotation(shape7, -0.4363323F, 0F, 0F);
		this.shape8 = new ModelRenderer(this, 11, 19);
		this.shape8.addBox(0F, 0F, 0F, 0, 1, 2);
		this.shape8.setRotationPoint(0F, -0.5F, -2.5F);
		this.shape8.setTextureSize(64, 32);
		this.shape8.mirror = true;
		this.setRotation(shape8, -0.4363323F, 0F, 0F);
		this.shape9 = new ModelRenderer(this, 44, 15);
		this.shape9.addBox(0F, 0F, 0F, 0, 2, 1);
		this.shape9.setRotationPoint(0F, 4.5F, 8F);
		this.shape9.setTextureSize(64, 32);
		this.shape9.mirror = true;
		this.setRotation(shape9, 1.570796F, 0F, 0F);
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
		shape1.render(scale);
		shape2.render(scale);
		shape3.render(scale);
		shape4.render(scale);
		shape5.render(scale);
		shape6.render(scale);
		shape7.render(scale);
		shape8.render(scale);
		shape9.render(scale);
	}

	public void render(float partialTick, float scale)
	{
		shape1.render(scale);
		shape2.render(scale);
		shape3.render(scale);
		shape4.render(scale);
		shape5.render(scale);
		shape6.render(scale);
		shape7.render(scale);
		shape8.render(scale);
		shape9.render(scale);
	}
}