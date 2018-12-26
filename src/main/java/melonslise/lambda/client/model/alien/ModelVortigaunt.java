package melonslise.lambda.client.model.alien;

import org.lwjgl.opengl.GL11;

import melonslise.lambda.common.entity.alien.EntityVortigaunt;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

// TODO Fix one of the hands rotations
public class ModelVortigaunt extends ModelBase
{
	// TODO Re add fingers?
	protected ModelRenderer chest, stomache, head, rarm0, rarm1, larm0, larm1, tent0, rhand, lhand, rear, lear, neck, collar1, collar2, collar3, lcollar1, collar4, collar5, rcollar1,rleg0, lleg0, rleg1, lleg1,
	rleg2, lleg2, rleg3, lleg3, rleg4, rleg5, lleg4, lleg5, collar6;

	public ModelVortigaunt()
	{
		this.chest = new ModelRenderer(this, 40, 3);
		this.chest.addBox(0F, 0F, 0F, 7, 7, 5);
		this.chest.setRotationPoint(-3F, 3F, 0F);
		this.chest.setTextureSize(64, 32);
		this.chest.mirror = true;
		this.setRotation(chest, 0.3316126F, 0F, 0F);
		this.stomache = new ModelRenderer(this, 20, 18);
		this.stomache.addBox(1F, -2F, 7F, 5, 6, 3);
		this.stomache.setRotationPoint(-3F, 13F, -2F);
		this.stomache.setTextureSize(64, 32);
		this.stomache.mirror = true;
		this.setRotation(stomache, 0.296706F, 0F, 0F);
		this.head = new ModelRenderer(this, 0, 15);
		this.head.addBox(-3F, -2.7F, -5F, 5, 4, 5);
		this.head.setRotationPoint(1F, 5F, -1F);
		this.head.setTextureSize(64, 32);
		this.head.mirror = true;
		this.setRotation(head, 0F, 0F, 0F);
		this.rarm0 = new ModelRenderer(this, 38, 24);
		this.rarm0.addBox(-1F, 0F, -1F, 2, 6, 2);
		this.rarm0.setRotationPoint(-4F, 5F, 2F);
		this.rarm0.setTextureSize(64, 32);
		this.rarm0.mirror = true;
		this.setRotation(rarm0, 0F, 0F, 0F);
		this.rarm1 = new ModelRenderer(this, 36, 15);
		this.rarm1.addBox(-1F, 4F, -9F, 2, 2, 7);
		this.rarm1.setRotationPoint(-4F, 5F, 2F);
		this.rarm1.setTextureSize(64, 32);
		this.rarm1.mirror = true;
		this.setRotation(rarm1, 0.5410521F, 0F, 0F);
		this.larm0 = new ModelRenderer(this, 38, 24);
		this.larm0.addBox(0F, 0F, -1F, 2, 6, 2);
		this.larm0.setRotationPoint(4F, 5F, 2F);
		this.larm0.setTextureSize(64, 32);
		this.larm0.mirror = true;
		this.setRotation(larm0, 0F, 0F, 0F);
		this.larm1 = new ModelRenderer(this, 36, 15);
		this.larm1.addBox(0F, 4.5F, -8.7F, 2, 2, 7);
		this.larm1.setRotationPoint(4F, 5F, 2F);
		this.larm1.setTextureSize(64, 32);
		this.larm1.mirror = true;
		this.setRotation(larm1, 0.5410521F, 0F, 0F);
		this.tent0 = new ModelRenderer(this, 8, 0);
		this.tent0.addBox(2F, 2F, 4F, 2, 3, 1);
		this.tent0.setRotationPoint(-3F, 4F, -1F);
		this.tent0.setTextureSize(64, 32);
		this.tent0.mirror = true;
		this.setRotation(tent0, -0.5759587F, 0F, 0F);
		this.rhand = new ModelRenderer(this, 30, 4);
		this.rhand.addBox(-1F, 8F, -8F, 2, 2, 3);
		this.rhand.setRotationPoint(-4F, 5F, 2F);
		this.rhand.setTextureSize(64, 32);
		this.rhand.mirror = true;
		this.setRotation(rhand, 0F, 0F, 0F);
		this.lhand = new ModelRenderer(this, 30, 4);
		this.lhand.addBox(0F, 8F, -7F, 2, 2, 3);
		this.lhand.setRotationPoint(4F, 5F, 2F);
		this.lhand.setTextureSize(64, 32);
		this.lhand.mirror = true;
		this.setRotation(lhand, 0F, 0F, 0F);
		this.rear = new ModelRenderer(this, 60, 1);
		this.rear.addBox(-4F, -1F, -4F, 1, 1, 1);
		this.rear.setRotationPoint(1F, 5F, -1F);
		this.rear.setTextureSize(64, 32);
		this.rear.mirror = true;
		this.setRotation(rear, 0F, 0F, 0F);
		this.lear = new ModelRenderer(this, 60, 1);
		this.lear.addBox(2F, -1F, -4F, 1, 1, 1);
		this.lear.setRotationPoint(1F, 5F, -1F);
		this.lear.setTextureSize(64, 32);
		this.lear.mirror = true;
		this.setRotation(lear, 0F, 0F, 0F);
		this.neck = new ModelRenderer(this, -1, 7);
		this.neck.addBox(-1F, 0F, 0F, 5, 5, 3);
		this.neck.setRotationPoint(-1F, 2F, -1F);
		this.neck.setTextureSize(64, 32);
		this.neck.mirror = true;
		this.setRotation(neck, 0F, 0F, 0F);
		this.collar1 = new ModelRenderer(this, 61, 16);
		this.collar1.addBox(0F, -3F, -1F, 1, 1, 1);
		this.collar1.setRotationPoint(-1F, 4F, 0F);
		this.collar1.setTextureSize(64, 32);
		this.collar1.mirror = true;
		this.setRotation(collar1, 0F, 0F, 0F);
		this.collar2 = new ModelRenderer(this, 56, 18);
		this.collar2.addBox(4F, 0F, 0F, 1, 1, 3);
		this.collar2.setRotationPoint(-5F, 0F, -1F);
		this.collar2.setTextureSize(64, 32);
		this.collar2.mirror = true;
		this.setRotation(collar2, 0F, 0F, 0F);
		this.collar3 = new ModelRenderer(this, 61, 16);
		this.collar3.addBox(4F, -3F, 1F, 1, 1, 1);
		this.collar3.setRotationPoint(-5F, 4F, 0F);
		this.collar3.setTextureSize(64, 32);
		this.collar3.mirror = true;
		this.setRotation(collar3, 0F, 0F, 0F);
		this.lcollar1 = new ModelRenderer(this, 14, 4);
		this.lcollar1.addBox(-1F, 5F, -8F, 4, 4, 2);
		this.lcollar1.setRotationPoint(4F, 5F, 2F);
		this.lcollar1.setTextureSize(64, 32);
		this.lcollar1.mirror = true;
		this.setRotation(lcollar1, 0.2443461F, 0F, 0F);
		this.collar4 = new ModelRenderer(this, 14, 0);
		this.collar4.addBox(2F, 6F, -1F, 2, 1, 2);
		this.collar4.setRotationPoint(-3F, 4F, 0F);
		this.collar4.setTextureSize(64, 32);
		this.collar4.mirror = true;
		this.setRotation(collar4, 0F, 0F, 0F);
		this.collar5 = new ModelRenderer(this, 22, 0);
		this.collar5.addBox(2F, 5F, 3F, 1, 2, 1);
		this.collar5.setRotationPoint(-3F, 5F, -1F);
		this.collar5.setTextureSize(64, 32);
		this.collar5.mirror = true;
		this.setRotation(collar5, -0.5759587F, -0.0174533F, 0F);
		this.rcollar1 = new ModelRenderer(this, 14, 4);
		this.rcollar1.addBox(-2F, 5.4F, -8.5F, 4, 4, 2);
		this.rcollar1.setRotationPoint(-4F, 5F, 2F);
		this.rcollar1.setTextureSize(64, 32);
		this.rcollar1.mirror = true;
		this.setRotation(rcollar1, 0.2443461F, 0F, 0F);
		this.rleg0 = new ModelRenderer(this, 46, 24);
		this.rleg0.addBox(-1F, 0F, -1F, 2, 6, 2);
		this.rleg0.setRotationPoint(-3F, 12F, 7F);
		this.rleg0.setTextureSize(64, 32);
		this.rleg0.mirror = true;
		this.setRotation(rleg0, 0F, -0.0174533F, 0F);
		this.lleg0 = new ModelRenderer(this, 46, 24);
		this.lleg0.addBox(-1F, 0F, -1F, 2, 6, 2);
		this.lleg0.setRotationPoint(4F, 12F, 7F);
		this.lleg0.setTextureSize(64, 32);
		this.lleg0.mirror = true;
		this.setRotation(lleg0, 0F, 0F, 0F);
		this.rleg1 = new ModelRenderer(this, 54, 22);
		this.rleg1.addBox(-1F, 3F, 4F, 2, 7, 3);
		this.rleg1.setRotationPoint(-3F, 12F, 7F);
		this.rleg1.setTextureSize(64, 32);
		this.rleg1.mirror = true;
		this.setRotation(rleg1, -0.8552113F, -0.0174533F, 0F);
		this.lleg1 = new ModelRenderer(this, 54, 22);
		this.lleg1.addBox(-1F, 3F, 4F, 2, 7, 3);
		this.lleg1.setRotationPoint(4F, 12F, 7F);
		this.lleg1.setTextureSize(64, 32);
		this.lleg1.mirror = true;
		this.setRotation(lleg1, -0.8552113F, 0F, 0F);
		this.rleg2 = new ModelRenderer(this, 0, 24);
		this.rleg2.addBox(-2F, 9F, -7F, 4, 3, 5);
		this.rleg2.setRotationPoint(-3F, 12F, 7F);
		this.	rleg2.setTextureSize(64, 32);
		this.rleg2.mirror = true;
		this.setRotation(rleg2, 0F, -0.0174533F, 0F);
		this.lleg2 = new ModelRenderer(this, 0, 24);
		this.lleg2.addBox(-2F, 9F, -7F, 4, 3, 5);
		this.lleg2.setRotationPoint(4F, 12F, 7F);
		this.lleg2.setTextureSize(64, 32);
		this.lleg2.mirror = true;
		this.setRotation(lleg2, 0F, 0F, 0F);
		this.rleg3 = new ModelRenderer(this, 18, 27);
		this.rleg3.addBox(-1F, 10F, -10F, 2, 2, 3);
		this.rleg3.setRotationPoint(-3F, 12F, 7F);
		this.rleg3.setTextureSize(64, 32);
		this.rleg3.mirror = true;
		this.setRotation(rleg3, 0F, 0F, 0F);
		this.lleg3 = new ModelRenderer(this, 18, 27);
		this.lleg3.addBox(-1F, 10F, -10F, 2, 2, 3);
		this.lleg3.setRotationPoint(4F, 12F, 7F);
		this.lleg3.setTextureSize(64, 32);
		this.lleg3.mirror = true;
		this.setRotation(lleg3, 0F, 0F, 0F);
		this.rleg4 = new ModelRenderer(this, 28, 28);
		this.rleg4.addBox(-2F, 11F, -2F, 1, 1, 3);
		this.rleg4.setRotationPoint(-3F, 12F, 7F);
		this.rleg4.setTextureSize(64, 32);
		this.rleg4.mirror = true;
		this.setRotation(rleg4, 0F, 0F, 0F);
		this.rleg5 = new ModelRenderer(this, 28, 28);
		this.rleg5.addBox(1F, 11F, -2F, 1, 1, 3);
		this.rleg5.setRotationPoint(-3F, 12F, 7F);
		this.rleg5.setTextureSize(64, 32);
		this.rleg5.mirror = true;
		this.setRotation(rleg5, 0F, 0F, 0F);
		this.lleg4 = new ModelRenderer(this, 28, 28);
		this.lleg4.addBox(-2F, 11F, -2F, 1, 1, 3);
		this.lleg4.setRotationPoint(4F, 12F, 7F);
		this.lleg4.setTextureSize(64, 32);
		this.lleg4.mirror = true;
		this.setRotation(lleg4, 0F, 0F, 0F);
		this.lleg5 = new ModelRenderer(this, 28, 28);
		this.lleg5.addBox(1F, 11F, -2F, 1, 1, 3);
		this.lleg5.setRotationPoint(4F, 12F, 7F);
		this.lleg5.setTextureSize(64, 32);
		this.lleg5.mirror = true;
		this.setRotation(lleg5, 0F, 0F, 0F);
		this.collar6 = new ModelRenderer(this, 22, 0);
		this.collar6.addBox(0F, 0F, 0F, 1, 2, 1);
		this.collar6.setRotationPoint(0F, 11F, -1F);
		this.collar6.setTextureSize(64, 32);
		this.collar6.mirror = true;
		this.setRotation(collar6, -0.5759587F, -0.0174533F, -0.2792527F);
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
		GL11.glTranslatef(0F, -0.45F, 0F);
		GL11.glScalef(1.3F, 1.3F, 1.3F);
		this.chest.render(scale);
		this.stomache.render(scale);
		this.head.render(scale);
		this.rarm0.render(scale);
		this.rarm1.render(scale);
		this.larm0.render(scale);
		this.larm1.render(scale);
		this.tent0.render(scale);
		this.rhand.render(scale);
		this.lhand.render(scale);
		this.rear.render(scale);
		this.lear.render(scale);
		this.neck.render(scale);
		this.collar1.render(scale);
		this.collar2.render(scale);
		this.collar3.render(scale);
		this.lcollar1.render(scale);
		this.collar4.render(scale);
		this.collar5.render(scale);
		this.rcollar1.render(scale);
		this.rleg0.render(scale);
		this.lleg0.render(scale);
		this.rleg1.render(scale);
		this.lleg1.render(scale);
		this.rleg2.render(scale);
		this.lleg2.render(scale);
		this.rleg3.render(scale);
		this.lleg3.render(scale);
		this.rleg4.render(scale);
		this.rleg5.render(scale);
		this.lleg4.render(scale);
		this.lleg5.render(scale);
		this.collar6.render(scale);
	}

	// TODO FIx / improve
	@Override
	public void setRotationAngles(float swing, float swingAmount, float age, float yaw, float pitch, float scale, Entity entity)
	{
		super.setRotationAngles(swing, swingAmount, age, yaw, pitch, scale, entity);

		float headX = pitch / (180F / (float) Math.PI), headY = yaw / (180F / (float) Math.PI);

		this.head.rotateAngleX = this.lear.rotateAngleX =this.rear.rotateAngleX = headX;
		this.head.rotateAngleY = this.lear.rotateAngleY= this.rear.rotateAngleY = headY;
	}

	// TODO Interpolation methods helper
	public void setLivingAnimations(EntityLivingBase entity, float swing, float swingAmount, float partialTick)
	{
		float rotation1 = MathHelper.cos(swing * 0.5F) * swingAmount, rotation2 = MathHelper.cos(swing * 0.5F + (float) Math.PI) * swingAmount;

		this.rleg0.rotateAngleX = this.rleg2.rotateAngleX = this.rleg3.rotateAngleX = this.rleg4.rotateAngleX = this.rleg5.rotateAngleX =rotation1;
		this.rleg1.rotateAngleX = -0.85F + rotation1;
		this.lleg0.rotateAngleX = this.lleg2.rotateAngleX = this.lleg3.rotateAngleX = this.lleg4.rotateAngleX = this.lleg5.rotateAngleX = rotation2;
		this.lleg1.rotateAngleX = -0.85F + rotation2;

		if (entity instanceof EntityVortigaunt)
		{
			EntityVortigaunt vortigaunt = (EntityVortigaunt) entity;
			int chargeTicks = vortigaunt.charge;
			float rotation = vortigaunt.oldRotationHand + (vortigaunt.rotationHand - vortigaunt.oldRotationHand) * partialTick;
			if(chargeTicks > 0)
			{
				this.rarm0.rotateAngleY = this.rarm1.rotateAngleY = this.rhand.rotateAngleY = this.rcollar1.rotateAngleY = -0.3F;
				this.larm0.rotateAngleY = this.larm1.rotateAngleY = this.lhand.rotateAngleY = this.lcollar1.rotateAngleY = 0.3F;
				this.rarm0.rotateAngleX = this.rhand.rotateAngleX = this.larm0.rotateAngleX = this.lhand.rotateAngleX = rotation - 0.6F;
				this.rarm1.rotateAngleX = this.larm1.rotateAngleX = rotation - 0.15F;
				this.rcollar1.rotateAngleX = this.lcollar1.rotateAngleX = rotation - 0.35F;
				return;
			}
			if(vortigaunt.claw > 0)
			{
				if(vortigaunt.claw <= 6 || vortigaunt.claw > 12)
				{
					this.rarm0.rotateAngleX = this.rhand.rotateAngleX = rotation - 0.7F;
					this.rarm1.rotateAngleX = rotation - 0.2F;
					this.rcollar1.rotateAngleX = rotation - 0.4F;
				}
				else
				{
					this.larm0.rotateAngleX = this.lhand.rotateAngleX = rotation - 0.7F;
					this.larm1.rotateAngleX = rotation - 0.2F;
					this.lcollar1.rotateAngleX = rotation - 0.4F;
				}
				return;
			}
		}
		this.rarm0.rotateAngleY = this.rarm1.rotateAngleY = this.rhand.rotateAngleY = this.rcollar1.rotateAngleY = this.larm0.rotateAngleY = this.larm1.rotateAngleY = this.lhand.rotateAngleY = this.lcollar1.rotateAngleY = 0F;
		rotation1 *= 0.7F;
		rotation2 *= 0.7F;
		this.rarm0.rotateAngleX = this.rhand.rotateAngleX = rotation2;
		this.rarm1.rotateAngleX = this.larm1.rotateAngleX = 0.55F + rotation2;
		this.rcollar1.rotateAngleX = this.lcollar1.rotateAngleX = 0.25F + rotation2;
		this.larm0.rotateAngleX = this.lhand.rotateAngleX = rotation1;
	}
}