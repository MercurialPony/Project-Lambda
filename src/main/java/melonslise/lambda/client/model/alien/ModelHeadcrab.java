package melonslise.lambda.client.model.alien;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelHeadcrab extends ModelBase
{
	protected ModelRenderer leg41, leg31, body, leg1, leg2, leg3, leg4;

	public ModelHeadcrab()
	{
		this.leg41 = new ModelRenderer(this, 0, 0);
		this.leg41.addBox(-1F, -1F, 0F, 1, 3, 1);
		this.leg41.setRotationPoint(3F, 22F, -6.533333F);
		this.leg41.setTextureSize(64, 32);
		this.leg41.mirror = true;
		this.setRotation(leg41, -0.8109963F, 0F, 0F);
		this.leg31 = new ModelRenderer(this, 0, 0);
		this.leg31.addBox(-1F, -1F, 0F, 1, 3, 1);
		this.leg31.setRotationPoint(-2F, 22F, -6F);
		this.leg31.setTextureSize(64, 32);
		this.leg31.mirror = true;
		this.setRotation(leg31, -0.8109963F, 0F, 0F);
		this.body = new ModelRenderer(this, 4, 0);
		this.body.addBox(-4F, -6F, -7F, 6, 6, 3);
		this.body.setRotationPoint(1F, 15F, 4F);
		this.body.setTextureSize(64, 32);
		this.body.mirror = true;
		this.setRotation(body, 1.570796F, 0F, 0F);
		this.leg1 = new ModelRenderer(this, 0, 9);
		this.leg1.addBox(-1F, 0F, -1F, 1, 3, 1);
		this.leg1.setRotationPoint(-2F, 21F, 4F);
		this.leg1.setTextureSize(64, 32);
		this.leg1.mirror = true;
		this.setRotation(leg1, 0.0826735F, 0F, 0F);
		this.leg2 = new ModelRenderer(this, 0, 9);
		this.leg2.addBox(-1F, 0F, -1F, 1, 3, 1);
		this.leg2.setRotationPoint(3F, 21F, 4F);
		this.leg2.setTextureSize(64, 32);
		this.leg2.mirror = true;
		this.setRotation(leg2, 0.0826735F, 0F, 0F);
		this.leg3 = new ModelRenderer(this, 0, 4);
		this.leg3.addBox(-1F, 0F, -1F, 1, 4, 1);
		this.leg3.setRotationPoint(-2F, 22F, -1F);
		this.leg3.setTextureSize(64, 32);
		this.leg3.mirror = true;
		this.setRotation(leg3, -1.48353F, 0F, 0F);
		this.leg4 = new ModelRenderer(this, 0, 4);
		this.leg4.addBox(0F, 0F, -1F, 1, 4, 1);
		this.leg4.setRotationPoint(2F, 22F, -2F);
		this.leg4.setTextureSize(64, 32);
		this.leg4.mirror = true;
		this.setRotation(leg4, -1.466077F, 0F, 0F);
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
		if(!entity.onGround)
		{
			GlStateManager.translate(0F, 1.5F, 0F);
			if(!entity.isRiding()) GlStateManager.rotate(-30F, 1F, 0F, 0F);
			else GlStateManager.rotate(30F, 1F, 0F, 0F);
			GlStateManager.translate(0F, -1.5F, 0F);
		}
		this.leg41.render(scale);
		this.leg31.render(scale);
		this.body.render(scale);
		this.leg1.render(scale);
		this.leg2.render(scale);
		this.leg3.render(scale);
		this.leg4.render(scale);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float swing, float swingAmount, float partialTicks)
	{
		this.leg1.rotateAngleX = MathHelper.cos(swing * 0.6662F) * swingAmount * 0.8F;
		this.leg2.rotateAngleX = MathHelper.cos(swing * 0.6662F + (float) Math.PI) * swingAmount * 0.4F;
	}
}