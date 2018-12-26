package melonslise.lambda.client.model.weapon;

import melonslise.lambda.common.item.weapon.ItemTau;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

// TODO Fix shape4 rotation origin? Or use another proper shape
public class ModelTauCannon extends ModelBase
{
	ModelRenderer shape1, shape2, shape3, shape4, shape5, shape6, shape7, shape8, shape9, shape10, shape12, shape13, shape14,
	shape15, shape16, shape17, shape18, shape11, shape19, shape20;

	// TODO Fix missing shape on front
	public ModelTauCannon()
	{
		this.shape1 = new ModelRenderer(this, 0, 4);
		this.shape1.addBox(0F, 0F, 0F, 1, 4, 4);
		this.shape1.setRotationPoint(0F, 20F, 0F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0F);
		this.shape2 = new ModelRenderer(this, 0, 12);
		this.shape2.addBox(0F, -1F, 1F, 1, 2, 7);
		this.shape2.setRotationPoint(0F, 19F, 0F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
		this.shape3 = new ModelRenderer(this, 30, 15);
		this.shape3.addBox(0F, 0F, 6F, 1, 4, 2);
		this.shape3.setRotationPoint(0F, 20F, 0F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0F, 0F, 0F);
		this.shape4 = new ModelRenderer(this, 50, 9);
		this.shape4.addBox(-2F, -2.5F, -1F, 5, 5, 2);
		this.shape4.setRotationPoint(0.5F, 21.5F, 0F);
		this.shape4.setTextureSize(64, 32);
		this.shape4.mirror = true;
		this.setRotation(shape4, 0F, 0F, 0F);
		this.shape5 = new ModelRenderer(this, 47, 0);
		this.shape5.addBox(0F, 1F, -7F, 1, 1, 6);
		this.shape5.setRotationPoint(0F, 20F, 0F);
		this.shape5.setTextureSize(64, 32);
		this.shape5.mirror = true;
		this.setRotation(shape5, 0F, 0F, 0F);
		this.shape6 = new ModelRenderer(this, 47, 0);
		this.shape6.addBox(0F, 0F, -10F, 3, 3, 6);
		this.shape6.setRotationPoint(-1F, 20F, -2F);
		this.shape6.setTextureSize(64, 32);
		this.shape6.mirror = true;
		this.setRotation(shape6, 0F, 0F, 0F);
		this.shape7 = new ModelRenderer(this, -1, 21);
		this.shape7.addBox(-2F, -1F, 8F, 5, 5, 2);
		this.shape7.setRotationPoint(0F, 20F, 0F);
		this.shape7.setTextureSize(64, 32);
		this.shape7.mirror = true;
		this.setRotation(shape7, 0F, 0F, 0.0349066F);
		this.shape8 = new ModelRenderer(this, 21, 21);
		this.shape8.addBox(0F, 2F, 0F, 1, 3, 7);
		this.shape8.setRotationPoint(0F, 20F, 12F);
		this.shape8.setTextureSize(64, 32);
		this.shape8.mirror = true;
		this.setRotation(shape8, -0.1396263F, 0F, 0F);
		this.shape9 = new ModelRenderer(this, 4, 0);
		this.shape9.addBox(-0.05F, 0F, 0F, 1, 2, 2);
		this.shape9.setRotationPoint(1F, 19F, 1F);
		this.shape9.setTextureSize(64, 32);
		this.shape9.mirror = true;
		this.setRotation(shape9, 0F, 0F, 0F);
		this.shape10 = new ModelRenderer(this, 0, 0);
		this.shape10.addBox(0F, 1F, 0F, 1, 3, 1);
		this.shape10.setRotationPoint(1F, 17F, 1F);
		this.shape10.setTextureSize(64, 32);
		this.shape10.mirror = true;
		this.setRotation(shape10, 0F, 0F, 0F);
		this.shape11 = new ModelRenderer(this, 47, 0);
		this.shape11.addBox(0F, 0F, 0F, 1, 1, 8);
		this.shape11.setRotationPoint(1F, 17F, -7F);
		this.shape11.setTextureSize(64, 32);
		this.shape11.mirror = true;
		this.setRotation(shape11, 0F, 0F, 0F);
		this.shape12 = new ModelRenderer(this, 60, 0);
		this.shape12.addBox(0F, 0F, -3F, 1, 2, 1);
		this.shape12.setRotationPoint(1F, 18F, -5F);
		this.shape12.setTextureSize(64, 32);
		this.shape12.mirror = true;
		this.setRotation(shape12, 0F, 0F, 0F);
		this.shape13 = new ModelRenderer(this, 4, 0);
		this.shape13.addBox(0F, 0F, 0F, 1, 2, 2);
		this.shape13.setRotationPoint(-0.9F, 19F, 1F);
		this.shape13.setTextureSize(64, 32);
		this.shape13.mirror = true;
		this.setRotation(shape13, 0F, 0F, 0F);
		this.shape14 = new ModelRenderer(this, 0, 0);
		this.shape14.addBox(-1F, -2F, 1F, 1, 3, 1);
		this.shape14.setRotationPoint(0F, 20F, 0F);
		this.shape14.setTextureSize(64, 32);
		this.shape14.mirror = true;
		this.setRotation(shape14, 0F, 0F, 0F);
		this.shape15 = new ModelRenderer(this, 47, 0);
		this.shape15.addBox(-1F, -3F, -7F, 1, 1, 8);
		this.shape15.setRotationPoint(0F, 20F, 0F);
		this.shape15.setTextureSize(64, 32);
		this.shape15.mirror = true;
		this.setRotation(shape15, 0F, 0F, 0F);
		this.shape16 = new ModelRenderer(this, 60, 0);
		this.shape16.addBox(-1F, -2F, -8F, 1, 2, 1);
		this.shape16.setRotationPoint(0F, 20F, 0F);
		this.shape16.setTextureSize(64, 32);
		this.shape16.mirror = true;
		this.setRotation(shape16, 0F, 0F, 0F);
		this.shape17 = new ModelRenderer(this, 16, 12);
		this.shape17.addBox(0F, 1F, 4F, 5, 3, 2);
		this.shape17.setRotationPoint(-2F, 20F, 0F);
		this.shape17.setTextureSize(64, 32);
		this.shape17.mirror = true;
		this.setRotation(shape17, 0F, 0F, 0F);
		this.shape18 = new ModelRenderer(this, 16, 17);
		this.shape18.addBox(0F, 0F, 0F, 0, 1, 1);
		this.shape18.setRotationPoint(0F, 20F, 5F);
		this.shape18.setTextureSize(64, 32);
		this.shape18.mirror = true;
		this.setRotation(shape18, 0F, 0F, 0F);
		this.shape19 = new ModelRenderer(this, 54, 0);
		this.shape19.addBox(0F, 1F, -16F, 1, 1, 4);
		this.shape19.setRotationPoint(0F, 20F, 0F);
		this.shape19.setTextureSize(64, 32);
		this.shape19.mirror = true;
		this.setRotation(shape19, 0F, 0F, 0F);
		this.shape20 = new ModelRenderer(this, 13, 21);
		this.shape20.addBox(0F, 0F, 9F, 1, 2, 3);
		this.shape20.setRotationPoint(0F, 23F, 0F);
		this.shape20.setTextureSize(64, 32);
		this.shape20.mirror = true;
		this.setRotation(shape20, 0F, 0F, 0F);
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
		shape12.render(scale);
		shape13.render(scale);
		shape14.render(scale);
		shape15.render(scale);
		shape16.render(scale);
		shape17.render(scale);
		shape18.render(scale);
		shape11.render(scale);
		shape19.render(scale);
		shape20.render(scale);
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
		shape12.render(scale);
		shape13.render(scale);
		shape14.render(scale);
		shape15.render(scale);
		shape16.render(scale);
		shape17.render(scale);
		shape18.render(scale);
		shape11.render(scale);
		shape19.render(scale);
		shape20.render(scale);
	}

	// TODO Find a way to add smooth acceleration
	public void setRotations(ItemStack stack, float partialTick)
	{
		this.shape4.rotateAngleZ += 0.3F * (float) LambdaUtilities.getTag(stack).getInteger(ItemTau.keyCharge);
	}
}