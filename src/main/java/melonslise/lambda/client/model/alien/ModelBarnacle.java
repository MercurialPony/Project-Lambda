package melonslise.lambda.client.model.alien;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBarnacle extends ModelBase
{
	protected ModelRenderer shape1, shape2, shape3, shape41, shape42, shape43, shape44, shape45, shape46, shape47, shape48, shape49, shape40, shape4a;

	public ModelBarnacle()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.shape1 = new ModelRenderer(this, 0, 18);
		this.shape1.addBox(-6F, 0F, -6F, 12, 6, 12);
		this.shape1.setRotationPoint(0F, 8F, 0F);
		this.shape1.setTextureSize(128, 64);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0F);
		this.shape2 = new ModelRenderer(this, 0, 36);
		this.shape2.addBox(-5.5F, 0F, -5.5F, 11, 3, 11);
		this.shape2.setRotationPoint(0F, 14F, 0F);
		this.shape2.setTextureSize(128, 64);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
		this.shape3 = new ModelRenderer(this, 0, 50);
		this.shape3.addBox(-5F, 0F, -5F, 9, 4, 10);
		this.shape3.setRotationPoint(0F, 17F, 0F);
		this.shape3.setTextureSize(128, 64);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0F, 0F, 0F);
		this.shape41 = new ModelRenderer(this, 0, 0);
		this.shape41.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape41.setRotationPoint(3F, 21F, -4F);
		this.shape41.setTextureSize(128, 64);
		this.shape41.mirror = true;
		this.setRotation(shape41, 0F, 0F, 0F);
		this.shape42 = new ModelRenderer(this, 0, 0);
		this.shape42.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape42.setRotationPoint(2F, 21F, -2F);
		this.shape42.setTextureSize(128, 64);
		this.shape42.mirror = true;
		this.setRotation(shape42, 0F, 0F, 0F);
		this.shape43 = new ModelRenderer(this, 0, 0);
		this.shape43.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape43.setRotationPoint(3F, 21F, 1F);
		this.shape43.setTextureSize(128, 64);
		this.shape43.mirror = true;
		this.setRotation(shape43, 0F, 0F, 0F);
		this.shape44 = new ModelRenderer(this, 0, 0);
		this.shape44.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape44.setRotationPoint(3F, 21F, 3F);
		this.shape44.setTextureSize(128, 64);
		this.shape44.mirror = true;
		this.setRotation(shape44, 0F, 0F, 0F);
		this.shape45 = new ModelRenderer(this, 0, 0);
		this.shape45.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape45.setRotationPoint(1F, 21F, 4F);
		this.shape45.setTextureSize(128, 64);
		this.shape45.mirror = true;
		this.setRotation(shape45, 0F, 0F, 0F);
		this.shape46 = new ModelRenderer(this, 0, 0);
		this.shape46.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape46.setRotationPoint(-2F, 21F, 3F);
		this.shape46.setTextureSize(128, 64);
		this.shape46.mirror = true;
		this.setRotation(shape46, 0F, 0F, 0F);
		this.shape47 = new ModelRenderer(this, 0, 0);
		this.shape47.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape47.setRotationPoint(-4F, 21F, 3F);
		this.shape47.setTextureSize(128, 64);
		this.shape47.mirror = true;
		this.setRotation(shape47, 0F, 0F, 0F);
		this.shape48 = new ModelRenderer(this, 0, 0);
		this.shape48.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape48.setRotationPoint(1F, 21F, -5.133333F);
		this.shape48.setTextureSize(128, 64);
		this.shape48.mirror = true;
		this.setRotation(shape48, 0F, 0F, 0F);
		this.shape49 = new ModelRenderer(this, 0, 0);
		this.shape49.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape49.setRotationPoint(-2F, 21F, -3F);
		this.shape49.setTextureSize(128, 64);
		this.shape49.mirror = true;
		this.setRotation(shape49, 0F, 0F, 0F);
		this.shape40 = new ModelRenderer(this, 0, 0);
		this.shape40.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape40.setRotationPoint(-5F, 21F, 0F);
		this.shape40.setTextureSize(128, 64);
		this.shape40.mirror = true;
		this.setRotation(shape40, 0F, 0F, 0F);
		this.shape4a = new ModelRenderer(this, 0, 0);
		this.shape4a.addBox(0F, 0F, 0F, 1, 1, 1);
		this.shape4a.setRotationPoint(-4F, 21F, -2F);
		this.shape4a.setTextureSize(128, 64);
		this.shape4a.mirror = true;
		this.setRotation(shape4a, 0F, 0F, 0F);
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
		this.shape41.render(scale);
		this.shape42.render(scale);
		this.shape43.render(scale);
		this.shape44.render(scale);
		this.shape45.render(scale);
		this.shape46.render(scale);
		this.shape47.render(scale);
		this.shape48.render(scale);
		this.shape49.render(scale);
		this.shape40.render(scale);
		this.shape4a.render(scale);
	}

	@Override
	public void setRotationAngles(float swing, float swingAmount, float age, float yaw, float pitch, float scale, Entity entity)
	{
		super.setRotationAngles(swing, swingAmount, age, yaw, pitch, scale, entity);
		this.setRotation(shape1, 0F, MathHelper.sin(age * 0.1F) * 0.05F, 0F);
		this.setRotation(shape2, 0F, -MathHelper.cos(age * 0.1F) * 0.05F, 0F);
		this.setRotation(shape3, 0F, MathHelper.sin(age * 0.1F + 0.7853981634F) * 0.05F, 0F);
	}
}