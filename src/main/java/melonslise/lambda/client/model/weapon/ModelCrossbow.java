package melonslise.lambda.client.model.weapon;

import melonslise.lambda.common.capability.entity.ICapabilityReloading;
import melonslise.lambda.common.item.api.AItemUsable;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

// TODO Fix one of the string rotations (shape 12 or 11)
public class ModelCrossbow extends ModelBase
{
	protected ModelRenderer shape1, shape2, shape3, shape4, shape5, shape6, shape7, shape8, shape9, shape10, shape11, shape12,
	shape13, shape14, shape15;

	public ModelCrossbow()
	{
		this.shape1 = new ModelRenderer(this, 8, 16);
		this.shape1.addBox(0F, 0F, 0F, 2, 2, 7);
		this.shape1.setRotationPoint(0F, 0F, 0F);
		this.shape1.setTextureSize(64, 32);
		this.shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0.7853982F);
		this.shape2 = new ModelRenderer(this, 20, 10);
		this.shape2.addBox(0F, 0F, 0F, 1, 2, 10);
		this.shape2.setRotationPoint(-0.5F, 0F, -1.5F);
		this.shape2.setTextureSize(64, 32);
		this.shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);
		this.shape3 = new ModelRenderer(this, 0, 29);
		this.shape3.addBox(0F, 0F, 0F, 4, 1, 0);
		this.shape3.setRotationPoint(0F, 0.5F, -1F);
		this.shape3.setTextureSize(64, 32);
		this.shape3.mirror = true;
		this.setRotation(shape3, 0F, -0.2617994F, 0F);
		this.shape4 = new ModelRenderer(this, 0, 29);
		this.shape4.addBox(-4F, 0F, 0F, 4, 1, 0);
		this.shape4.setRotationPoint(0F, 0.5F, -1F);
		this.shape4.setTextureSize(64, 32);
		this.shape4.mirror = true;
		this.setRotation(shape4, 0F, 0.2617994F, 0F);
		this.shape5 = new ModelRenderer(this, 0, 30);
		this.shape5.addBox(0F, 0F, 0F, 4, 1, 0);
		this.shape5.setRotationPoint(3.8F, 0.5F, 0F);
		this.shape5.setTextureSize(64, 32);
		this.shape5.mirror = true;
		this.setRotation(shape5, 0F, -0.5235988F, 0F);
		this.shape5.mirror = false;
		this.shape6 = new ModelRenderer(this, 0, 30);
		this.shape6.addBox(-5F, 0F, 0F, 4, 1, 0);
		this.shape6.setRotationPoint(-2.9F, 0.5F, -0.5F);
		this.shape6.setTextureSize(64, 32);
		this.shape6.mirror = true;
		this.setRotation(shape6, 0F, 0.5235988F, 0F);
		this.shape7 = new ModelRenderer(this, 34, 14);
		this.shape7.addBox(0F, 0F, -3F, 1, 1, 9);
		this.shape7.setRotationPoint(0F, 0.5F, 8F);
		this.shape7.setTextureSize(64, 32);
		this.shape7.mirror = true;
		this.setRotation(shape7, -0.6283185F, 0.4363323F, 0.6981317F);
		this.shape8 = new ModelRenderer(this, 46, 14);
		this.shape8.addBox(0F, 0F, 0F, 1, 2, 6);
		this.shape8.setRotationPoint(-0.5F, 2F, 9.5F);
		this.shape8.setTextureSize(64, 32);
		this.shape8.mirror = true;
		this.setRotation(shape8, 0.1396263F, 0F, 0F);
		this.shape9 = new ModelRenderer(this, 43, 8);
		this.shape9.addBox(0F, 0F, 0F, 1, 1, 4);
		this.shape9.setRotationPoint(0F, -1F, 4F);
		this.shape9.setTextureSize(64, 32);
		this.shape9.mirror = true;
		this.setRotation(shape9, 0F, 0F, 0.7853982F);
		this.shape10 = new ModelRenderer(this, 27, 23);
		this.shape10.addBox(0F, 0F, 0F, 1, 5, 2);
		this.shape10.setRotationPoint(-0.5F, 1F, 3.5F);
		this.shape10.setTextureSize(64, 32);
		this.shape10.mirror = true;
		this.setRotation(shape10, -0.2617994F, 0F, 0F);
		this.shape11 = new ModelRenderer(this, 0, 31);
		this.shape11.addBox(-0.5F, 0F, 0F, 9, 0, 1);
		this.shape11.setRotationPoint(7F, 1F, 1.3F);
		this.shape11.setTextureSize(64, 32);
		this.shape11.mirror = true;
		this.setRotation(shape11, 3.141593F, 3.141593F, -0.0872665F);
		this.shape12 = new ModelRenderer(this, 0, 31);
		this.shape12.addBox(0F, 0F, 0F, 9, 0, 1);
		this.shape12.setRotationPoint(-7.5F, 1F, 1.3F);
		this.shape12.setTextureSize(64, 32);
		this.shape12.mirror = true;
		this.setRotation(shape12, 0F, 0.0174533F, -0.0872665F);
		this.shape13 = new ModelRenderer(this, 13, 6);
		this.shape13.addBox(0F, 0F, 0F, 0, 1, 8);
		this.shape13.setRotationPoint(0F, -0.2F, -3F);
		this.shape13.setTextureSize(64, 32);
		this.shape13.mirror = true;
		this.setRotation(shape13, 0F, 0F, 0F);
		this.shape14 = new ModelRenderer(this, 0, 24);
		this.shape14.addBox(0F, 0F, 0F, 0, 2, 2);
		this.shape14.setRotationPoint(0F, 1.5F, 7F);
		this.shape14.setTextureSize(64, 32);
		this.shape14.mirror = true;
		this.setRotation(shape14, -0.2617994F, 0F, 0F);
		this.shape15 = new ModelRenderer(this, 13, 6);
		this.shape15.addBox(0F, 0F, 0F, 0, 1, 8);
		this.shape15.setRotationPoint(0.5F, 0.3F, -3F);
		this.shape15.setTextureSize(64, 32);
		this.shape15.mirror = true;
		this.setRotation(shape15, 0F, 0F, 1.570796F);
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
		this.shape4.render(scale);
		this.shape5.render(scale);
		this.shape6.render(scale);
		this.shape7.render(scale);
		this.shape8.render(scale);
		this.shape9.render(scale);
		this.shape10.render(scale);
		this.shape11.render(scale);
		this.shape12.render(scale);
		this.shape13.render(scale);
		this.shape14.render(scale);
		this.shape15.render(scale);
	}

	public void render(ItemStack stack, float partialTick, float scale)
	{
		this.shape1.render(scale);
		this.shape2.render(scale);
		this.shape3.render(scale);
		this.shape4.render(scale);
		this.shape5.render(scale);
		this.shape6.render(scale);
		this.shape7.render(scale);
		this.shape8.render(scale);
		this.shape9.render(scale);
		this.shape10.render(scale);
		this.shape11.render(scale);
		this.shape12.render(scale);
		this.shape13.render(scale);
		this.shape14.render(scale);
		this.shape15.render(scale);
	}

	// TODO Shorten
	// TODO Helper for user
	public void setRotations(ItemStack stack, float partialTick)
	{
		float rotation = (float) LambdaUtilities.getTag(stack).getInteger(AItemUsable.keyCooldown) / 20F;
		Entity entity = Minecraft.getMinecraft().world.getEntityByID(LambdaUtilities.getTag(stack).getInteger(AItemUsable.keyUser));
		boolean isReloading = false;
		if(entity != null)
		{
			ICapabilityReloading reloading = LambdaUtilities.getReloading(entity);
			if(reloading != null && reloading.get())
			{
				rotation = (float) reloading.getTicks() / 20F;
				isReloading = true;
			}
		}
		this.shape11.setRotationPoint(7F, 1F, 2F);
		this.shape12.setRotationPoint(-7F, 1F, 2F);
		if(LambdaUtilities.getMagazine(stack) != 0 || isReloading)
		{
			this.setRotation(shape11, 0F, 0F, 0.1F);
			this.setRotation(shape12, 0F, 0F, -0.1F);
			this.shape11.rotateAngleY = 3.2F + 0.75F - rotation;
			this.shape12.rotateAngleY = 0.1F - 0.75F + rotation;
		}
	}
}