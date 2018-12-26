package melonslise.lambda.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMedkit extends ModelBase
{
	protected ModelRenderer shape1, shapea, shape2, shape3, shape4;

	public ModelMedkit()
	{
		this.shape1 = new ModelRenderer(this, 0, 19);
		this.shape1.addBox(-3F, 0F, -4F, 6, 2, 8);
		this.shape1.setRotationPoint(0F, 22F, 0F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0F);
		this.shapea = new ModelRenderer(this, 0, 11);
		this.shapea.addBox(-4F, 0F, -3F, 8, 2, 6);
		this.shapea.setRotationPoint(0F, 22F, 0F);
		this.shapea.setTextureSize(64, 32);
		this.shapea.mirror = true;
		this.setRotation(shapea, 0F, 0F, 0F);
		this.shape2 = new ModelRenderer(this, 0, 4);
		this.shape2.addBox(-3F, 0F, -3F, 6, 1, 6);
		this.shape2.setRotationPoint(0F, 21.5F, 0F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
		this.shape3 = new ModelRenderer(this, 10, -2);
		this.shape3.addBox(-4F, -1F, -2F, 8, 2, 2);
		this.shape3.setRotationPoint(0F, 23F, -3F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0F, 0F, 0F);
		this.shape4 = new ModelRenderer(this, 0, -1);
		this.shape4.addBox(-2F, -1F, -3F, 4, 2, 1);
		this.shape4.setRotationPoint(-2F, 23F, -3F);
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
		this.shape1.render(scale);
		this.shape2.render(scale);
		this.shape3.render(scale);
		this.shape4.render(scale);
	}
}