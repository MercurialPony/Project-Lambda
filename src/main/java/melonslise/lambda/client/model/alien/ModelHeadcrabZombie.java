package melonslise.lambda.client.model.alien;

import melonslise.lambda.common.entity.alien.EntityHeadcrabZombie;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelHeadcrabZombie extends ModelBase
{
	protected ModelRenderer head, hcBack11, hcBack12, hcBack21, hcBack22, body, rightarm, leftarm, rightleg, leftleg, hcHead, hcFront11, hcFront21, hcFront12, hcFront22;

	public ModelHeadcrabZombie()
	{
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(0F, 0F, 0F, 5, 4, 5);
		this.head.setRotationPoint(-2.333333F, -4F, -2.3F);
		this.head.setTextureSize(64, 32);
		this.head.mirror = true;
		this.setRotation(head, 0F, 0F, 0.122173F);
		this.hcBack11 = new ModelRenderer(this, 56, 21);
		this.hcBack11.addBox(0F, 0.5333334F, 0F, 1, 2, 1);
		this.hcBack11.setRotationPoint(-3F, -4F, 2F);
		this.hcBack11.setTextureSize(64, 32);
		this.hcBack11.mirror = true;
		this.setRotation(hcBack11, 0.3292855F, 0F, 0F);
		this.hcBack12 = new ModelRenderer(this, 56, 21);
		this.hcBack12.addBox(0F, 1F, 0F, 1, 2, 1);
		this.hcBack12.setRotationPoint(2F, -4.333333F, 1.7F);
		this.hcBack12.setTextureSize(64, 32);
		this.hcBack12.mirror = true;
		this.setRotation(hcBack12, 0.3292855F, 0F, 0F);
		this.hcBack21 = new ModelRenderer(this, 56, 18);
		this.hcBack21.addBox(-0.6F, 0.06666667F, 0.03F, 1, 2, 1);
		this.hcBack21.setRotationPoint(-2.333333F, -2.4F, 2.7F);
		this.hcBack21.setTextureSize(64, 32);
		this.hcBack21.mirror = true;
		this.setRotation(hcBack21, -0.1780236F, 0F, 0F);
		this.hcBack22 = new ModelRenderer(this, 56, 18);
		this.hcBack22.addBox(0F, 0F, 0F, 1, 2, 1);
		this.hcBack22.setRotationPoint(2F, -2F, 2.5F);
		this.hcBack22.setTextureSize(64, 32);
		this.hcBack22.mirror = true;
		this.setRotation(hcBack22, -0.1780236F, 0F, 0F);
		this.body = new ModelRenderer(this, 16, 16);
		this.body.addBox(-4F, 0F, -2F, 8, 12, 4);
		this.body.setRotationPoint(-0.6F, 0.2F, 0F);
		this.body.setTextureSize(64, 32);
		this.body.mirror = true;
		this.setRotation(body, 0F, 0F, -0.0523599F);
		this.rightarm = new ModelRenderer(this, 42, 17);
		this.rightarm.addBox(-3F, -1F, -2F, 3, 12, 3);
		this.rightarm.setRotationPoint(-4F, 2.466667F, 1F);
		this.rightarm.setTextureSize(64, 32);
		this.rightarm.mirror = true;
		this.setRotation(rightarm, -0.553982F, 0F, 0F);
		this.leftarm = new ModelRenderer(this, 42, 17);
		this.leftarm.addBox(-1F, -1F, -2F, 3, 12, 3);
		this.leftarm.setRotationPoint(4.3F, 1F, 0.5F);
		this.leftarm.setTextureSize(64, 32);
		this.leftarm.mirror = true;
		this.setRotation(leftarm, -0.2792527F, 0F, -0.0523599F);
		this.rightleg = new ModelRenderer(this, 0, 16);
		this.rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		this.rightleg.setRotationPoint(-2F, 12F, 0F);
		this.rightleg.setTextureSize(64, 32);
		this.rightleg.mirror = true;
		this.setRotation(rightleg, 0.1919862F, 0F, 0F);
		this.leftleg = new ModelRenderer(this, 0, 16);
		this.leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		this.leftleg.setRotationPoint(2F, 12F, 0F);
		this.leftleg.setTextureSize(64, 32);
		this.leftleg.mirror = true;
		this.setRotation(leftleg, -0.418879F, 0F, -0.0349066F);
		this.hcHead = new ModelRenderer(this, 32, 7);
		this.hcHead.addBox(-3F, -1F, -3F, 6, 3, 6);
		this.hcHead.setRotationPoint(0F, -4F, -0.3F);
		this.hcHead.setTextureSize(64, 32);
		this.hcHead.mirror = true;
		this.setRotation(hcHead, 0.418879F, 0F, 0.1047198F);
		this.hcFront11 = new ModelRenderer(this, 56, 28);
		this.hcFront11.addBox(0F, 0F, -3F, 1, 1, 3);
		this.hcFront11.setRotationPoint(1.9F, -3F, -2.3F);
		this.hcFront11.setTextureSize(64, 32);
		this.hcFront11.mirror = true;
		this.setRotation(hcFront11, 1.064651F, 0F, 0.1047198F);
		this.hcFront21 = new ModelRenderer(this, 56, 24);
		this.hcFront21.addBox(0F, 0F, -0.4666667F, 1, 3, 1);
		this.hcFront21.setRotationPoint(1.9F, -0.4666667F, -3.3F);
		this.hcFront21.setTextureSize(64, 32);
		this.hcFront21.mirror = true;
		this.setRotation(hcFront21, 0.1082104F, 0F, 0F);
		this.hcFront12 = new ModelRenderer(this, 56, 28);
		this.hcFront12.addBox(0F, 0F, -3.466667F, 1, 1, 3);
		this.hcFront12.setRotationPoint(-2.9F, -3F, -2.3F);
		this.hcFront12.setTextureSize(64, 32);
		this.hcFront12.mirror = true;
		this.setRotation(hcFront12, 1.064651F, 0F, 0.1047198F);
		this.hcFront22 = new ModelRenderer(this, 56, 24);
		this.hcFront22.addBox(0F, 0F, 0F, 1, 3, 1);
		this.hcFront22.setRotationPoint(-3F, -9.992007E-15F, -4F);
		this.hcFront22.setTextureSize(64, 32);
		this.hcFront22.mirror = true;
		this.setRotation(hcFront22, 0.1198459F, 0F, 0.122173F);
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
		this.hcBack11.render(scale);
		this.hcBack12.render(scale);
		this.hcBack21.render(scale);
		this.hcBack22.render(scale);
		this.body.render(scale);
		this.rightarm.render(scale);
		this.leftarm.render(scale);
		this.rightleg.render(scale);
		this.leftleg.render(scale);
		this.hcHead.render(scale);
		this.head.render(scale);
		this.hcFront11.render(scale);
		this.hcFront21.render(scale);
		this.hcFront12.render(scale);
		this.hcFront22.render(scale);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float swing, float swingAmount, float partialTicks)
	{
		this.rightleg.rotateAngleX = MathHelper.cos(swing * 0.6F) * swingAmount * 0.8F;
		this.leftleg.rotateAngleX = MathHelper.cos(swing * 0.6F + (float) Math.PI) * swingAmount * 0.4F;
		if(entity instanceof EntityHeadcrabZombie)
		{
			EntityHeadcrabZombie zombie = (EntityHeadcrabZombie) entity;
			if (zombie.swing > 0)
			{
				float rotation = zombie.oldRotationHand + (zombie.rotationHand - zombie.oldRotationHand) * partialTicks;
				if(zombie.swing <= 15) this.rightarm.rotateAngleX = rotation;
				else this.leftarm.rotateAngleX = rotation;
				return;
			}
		}
		if(entity.isBurning())
		{
			this.leftarm.rotateAngleX = this.rightarm.rotateAngleX = -3F;
			return;
		}
		this.leftarm.rotateAngleX = -1.1F + MathHelper.cos(swing * 0.6662F) * 2F * swingAmount * 0.2F;
		this.rightarm.rotateAngleX = -1.25F + MathHelper.cos(swing * 0.6662F + (float) Math.PI) * 2F * swingAmount * 0.2F;
	}
}