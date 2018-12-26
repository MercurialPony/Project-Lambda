package melonslise.lambda.client.model.weapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGluonBackpack extends ModelBase
{
	ModelRenderer shape1, shape2, shape3, shape4, shape5, shape6, shape7, shape8;

	public ModelGluonBackpack()
	{
		this.shape1 = new ModelRenderer(this, 37, 10);
		this.shape1.addBox(0F, 0F, 0F, 2, 7, 2);
		this.shape1.setRotationPoint(-2.5F, 1.5F, 1F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0.7853982F, 0F);
		this.shape2 = new ModelRenderer(this, 37, 10);
		this.shape2.addBox(0F, 0F, 0F, 2, 7, 2);
		this.shape2.setRotationPoint(0.5F, 1.5F, 1F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0.7853982F, 0F);
		this.shape3 = new ModelRenderer(this, 20, 10);
		this.shape3.addBox(0F, 0F, 0F, 6, 7, 2);
		this.shape3.setRotationPoint(-2.5F, 1.2F, -2F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0.0174533F, 0F, 0F);
		this.shape4 = new ModelRenderer(this, 23, 1);
		this.shape4.addBox(0F, 0F, 0F, 3, 6, 2);
		this.shape4.setRotationPoint(-1F, 2F, -1F);
		this.shape4.setTextureSize(64, 32);
		this.shape4.mirror = true;
		this.setRotation(shape4, 0.2268928F, 0F, 0F);
		this.shape5 = new ModelRenderer(this, 15, 7);
		this.shape5.addBox(0F, 0F, 0F, 1, 2, 1);
		this.shape5.setRotationPoint(-2F, 2F, -1F);
		this.shape5.setTextureSize(64, 32);
		this.shape5.mirror = true;
		this.setRotation(shape5, -0.5410521F, 0.0174533F, 0.8901179F);
		this.shape6 = new ModelRenderer(this, 15, 7);
		this.shape6.addBox(0F, 0F, 0F, 1, 2, 1);
		this.shape6.setRotationPoint(-3.8F, 3.2F, -1.5F);
		this.shape6.setTextureSize(64, 32);
		this.shape6.mirror = true;
		this.setRotation(shape6, -0.6981317F, 0.4363323F, -0.2268928F);
		this.shape7 = new ModelRenderer(this, 13, 11);
		this.shape7.addBox(0F, 0F, 0F, 1, 6, 2);
		this.shape7.setRotationPoint(-1.5F, 1.5F, -3.8F);
		this.shape7.setTextureSize(64, 32);
		this.shape7.mirror = true;
		this.setRotation(shape7, 0.0523599F, 0F, 0F);
		this.shape8 = new ModelRenderer(this, 13, 11);
		this.shape8.addBox(0F, 0F, 0F, 1, 6, 2);
		this.shape8.setRotationPoint(1.5F, 1.5F, -3.8F);
		this.shape8.setTextureSize(64, 32);
		this.shape8.mirror = true;
		this.setRotation(shape8, 0.0523599F, 0F, 0F);
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
		//shape1.render(scale);
		//shape2.render(scale);
		shape3.render(scale);
		shape4.render(scale);
		shape5.render(scale);
		shape6.render(scale);
		shape7.render(scale);
		shape8.render(scale);
	}
}