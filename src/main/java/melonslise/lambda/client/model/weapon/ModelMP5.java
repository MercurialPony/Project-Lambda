package melonslise.lambda.client.model.weapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMP5 extends ModelBase
{
	ModelRenderer shape1, shape2, shape3, shape4, shape5, shape6, shape7, shape8, shape9, shape10, shape11, shape12, shape13,
	shape14, shape15, shape16;

	public ModelMP5()
	{
		this.shape1 = new ModelRenderer(this, 9, 8);
		this.shape1.addBox(0F, 0F, 0F, 1, 1, 13);
		this.shape1.setRotationPoint(0F, 0F, -0.5F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0.7853982F);
		this.shape2 = new ModelRenderer(this, 28, 13);
		this.shape2.addBox(0F, 0F, 0F, 1, 1, 10);
		this.shape2.setRotationPoint(0F, 1F, 2F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0.7853982F);
		this.shape3 = new ModelRenderer(this, 41, 13);
		this.shape3.addBox(0F, 0F, 0F, 1, 3, 6);
		this.shape3.setRotationPoint(-0.5F, 0.5F, 6F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0.0698132F, 0F, 0F);
		this.shape4 = new ModelRenderer(this, 0, 21);
		this.shape4.addBox(0F, 0F, 0F, 1, 2, 4);
		this.shape4.setRotationPoint(-0.5F, 0.5F, 3F);
		this.shape4.setTextureSize(64, 32);
		this.shape4.mirror = true;
		this.setRotation(shape4, -0.1396263F, 0F, 0F);
		this.shape5 = new ModelRenderer(this, 56, 18);
		this.shape5.addBox(0F, 0F, 0F, 1, 3, 1);
		this.shape5.setRotationPoint(-0.5F, -0.2F, 16.2F);
		this.shape5.setTextureSize(64, 32);
		this.shape5.mirror = true;
		this.setRotation(shape5, 0.122173F, 0F, 0F);
		this.shape6 = new ModelRenderer(this, 23, 23);
		this.shape6.addBox(0F, 0F, 0F, 1, 3, 1);
		this.shape6.setRotationPoint(-0.5F, 2.5F, 6.7F);
		this.shape6.setTextureSize(64, 32);
		this.shape6.mirror = true;
		this.setRotation(shape6, -0.3665191F, 0F, 0F);
		this.shape7 = new ModelRenderer(this, 19, 17);
		this.shape7.addBox(0F, 0F, 0F, 1, 3, 0);
		this.shape7.setRotationPoint(-0.5F, -1.5F, 2.2F);
		this.shape7.setTextureSize(64, 32);
		this.shape7.mirror = true;
		this.setRotation(shape7, 0F, 0F, 0F);
		this.shape8 = new ModelRenderer(this, 46, 25);
		this.shape8.addBox(0F, 0F, 0F, 1, 3, 1);
		this.shape8.setRotationPoint(-0.5F, 3F, 10.5F);
		this.shape8.setTextureSize(64, 32);
		this.shape8.mirror = true;
		this.setRotation(shape8, 0.4363323F, 0F, 0F);
		this.shape9 = new ModelRenderer(this, 23, 28);
		this.shape9.addBox(0F, 0F, 0F, 1, 2, 1);
		this.shape9.setRotationPoint(-0.5F, 4.9F, 5.8F);
		this.shape9.setTextureSize(64, 32);
		this.shape9.mirror = true;
		this.setRotation(shape9, -0.7853982F, 0F, 0F);
		this.shape10 = new ModelRenderer(this, 41, 25);
		this.shape10.addBox(0F, 0F, 0F, 0, 2, 2);
		this.shape10.setRotationPoint(0F, 2.2F, 9F);
		this.shape10.setTextureSize(64, 32);
		this.shape10.mirror = true;
		this.setRotation(shape10, 0.0872665F, 0F, 0F);
		this.shape11 = new ModelRenderer(this, 27, 14);
		this.shape11.addBox(0F, 0F, 0F, 0, 1, 5);
		this.shape11.setRotationPoint(0.3F, 0.3F, 12F);
		this.shape11.setTextureSize(64, 32);
		this.shape11.mirror = true;
		this.setRotation(shape11, 0F, 0F, 0F);
		this.shape12 = new ModelRenderer(this, 19, 14);
		this.shape12.addBox(0F, 0F, 0F, 1, 2, 0);
		this.shape12.setRotationPoint(-0.5F, -1F, 12F);
		this.shape12.setTextureSize(64, 32);
		this.shape12.mirror = true;
		this.setRotation(shape12, 0F, 0F, 0F);
		this.shape13 = new ModelRenderer(this, 27, 14);
		this.shape13.addBox(0F, 0F, 0F, 0, 1, 5);
		this.shape13.setRotationPoint(-0.3F, 0.3F, 12F);
		this.shape13.setTextureSize(64, 32);
		this.shape13.mirror = true;
		this.setRotation(shape13, 0F, 0F, 0F);
		this.shape14 = new ModelRenderer(this, 12, 19);
		this.shape14.addBox(0F, 0F, 0F, 2, 0, 1);
		this.shape14.setRotationPoint(0F, 0.7F, 3F);
		this.shape14.setTextureSize(64, 32);
		this.shape14.mirror = true;
		this.setRotation(shape14, 0F, 0F, -0.2617994F);
		this.shape15 = new ModelRenderer(this, 10, 23);
		this.shape15.addBox(0F, 0F, 0F, 1, 1, 5);
		this.shape15.setRotationPoint(0F, 2F, 0F);
		this.shape15.setTextureSize(64, 32);
		this.shape15.mirror = true;
		this.setRotation(shape15, 0F, 0F, 0.7853982F);
		this.shape16 = new ModelRenderer(this, 11, 25);
		this.shape16.addBox(0F, 0F, 0F, 0, 1, 1);
		this.shape16.setRotationPoint(0F, 2.7F, 5F);
		this.shape16.setTextureSize(64, 32);
		this.shape16.mirror = true;
		this.setRotation(shape16, 0F, 0F, 0F);
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
		shape10.render(scale);
		shape11.render(scale);
		shape12.render(scale);
		shape13.render(scale);
		shape14.render(scale);
		shape15.render(scale);
		shape16.render(scale);
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
		shape10.render(scale);
		shape11.render(scale);
		shape12.render(scale);
		shape13.render(scale);
		shape14.render(scale);
		shape15.render(scale);
		shape16.render(scale);
	}
}