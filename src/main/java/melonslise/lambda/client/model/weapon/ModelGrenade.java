package melonslise.lambda.client.model.weapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGrenade extends ModelBase
{
	ModelRenderer shape1, shape2;

	public ModelGrenade()
	{
		this.shape1 = new ModelRenderer(this, 0, 9);
		this.shape1.addBox(0F, 0F, 0F, 2, 2, 1);
		this.shape1.setRotationPoint(0F, 0F, 0F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0F);
		this.shape2 = new ModelRenderer(this, 0, 0);
		this.shape2.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
		this.shape2.setRotationPoint(1F, 1F, 1F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
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
	}
}