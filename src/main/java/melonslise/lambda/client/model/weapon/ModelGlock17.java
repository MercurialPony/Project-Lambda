package melonslise.lambda.client.model.weapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGlock17 extends ModelBase
{
	protected ModelRenderer shape1, shape2, shape3, shape5, shape6, shape4;

	public ModelGlock17()
	{
		this.shape1 = new ModelRenderer(this, 7, 10);
		this.shape1.addBox(0F, 0F, 0F, 1, 1, 3);
		this.shape1.setRotationPoint(0F, 0F, 1F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0F);
		this.shape2 = new ModelRenderer(this, 15, 12);
		this.shape2.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape2.setRotationPoint(0F, 0.3F, 4F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
		this.shape3 = new ModelRenderer(this, 19, 11);
		this.shape3.addBox(0F, 0F, 0F, 1, 1, 2);
		this.shape3.setRotationPoint(0F, 0F, 5F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0F, 0F, 0F);
		this.shape5 = new ModelRenderer(this, 23, 16);
		this.shape5.addBox(0F, 0F, 0F, 1, 3, 1);
		this.shape5.setRotationPoint(0F, 0.5F, 5F);
		this.shape5.setTextureSize(64, 32);
		this.shape5.mirror = true;
		this.setRotation(shape5, 0.3490659F, 0F, -0.0174533F);
		this.shape6 = new ModelRenderer(this, 15, 14);
		this.shape6.addBox(0F, 0F, 0F, 1, 1, 3);
		this.shape6.setRotationPoint(0.5F, 0F, 3.5F);
		this.shape6.setTextureSize(64, 32);
		this.shape6.mirror = true;
		this.setRotation(shape6, 0F, 0F, 0.7853982F);
		this.shape4 = new ModelRenderer(this, 18, 19);
		this.shape4.addBox(0F, 0F, 0F, 0, 1, 2);
		this.shape4.setRotationPoint(0.5F, 1F, 3.7F);
		this.shape4.setTextureSize(64, 32);
		this.shape4.mirror = true;
		this.setRotation(shape4, 0F, 0F, 0F);

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
		this.shape1.render(scale);
		this.shape2.render(scale);
		this.shape3.render(scale);
		this.shape5.render(scale);
		this.shape6.render(scale);
		this.shape4.render(scale);
	}

	public void render(float partialTicks, float scale)
	{
		this.shape1.render(scale);
		this.shape2.render(scale);
		this.shape3.render(scale);
		this.shape5.render(scale);
		this.shape6.render(scale);
		this.shape4.render(scale);
	}
}