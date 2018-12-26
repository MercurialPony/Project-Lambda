package melonslise.lambda.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelBattery extends ModelBase
{
	protected ModelRenderer batSide, batContact, batTop, batPanel;

	public ModelBattery()
	{
		this.batSide = new ModelRenderer(this, 0, 0);
		this.batSide.addBox(-5F, 12F, -4F, 8, 13, 8, 0F);
		this.batSide.setRotationPoint(0F, 0F, 0F);
		this.batSide.rotateAngleX = 0F;
		this.batSide.rotateAngleY = 0F;
		this.batSide.rotateAngleZ = 0F;
		this.batSide.mirror = false;
		this.batContact = new ModelRenderer(this, 24, 0);
		this.batContact.addBox(-2F, 8F, -6F, 2, 3, 1, 0F);
		this.batContact.setRotationPoint(0F, 0F, 0F);
		this.batContact.rotateAngleX = 0.6981317F;
		this.batContact.rotateAngleY = 0F;
		this.batContact.rotateAngleZ = 0F;
		this.batContact.mirror = false;
		this.batTop = new ModelRenderer(this, 30, 0);
		this.batTop.addBox(-2.466667F, 11F, 0.8F, 3, 1, 3, 0F);
		this.batTop.setRotationPoint(0F, 0F, 0F);
		this.batTop.rotateAngleX = 0F;
		this.batTop.rotateAngleY = 0F;
		this.batTop.rotateAngleZ = 0F;
		this.batTop.mirror = false;
		this.batPanel = new ModelRenderer(this, 0, 0);
		this.batPanel.addBox(-2F, 9.533334F, -3F, 2, 3, 2, 0F);
		this.batPanel.setRotationPoint(0F, 0F, 0F);
		this.batPanel.rotateAngleX = 0F;
		this.batPanel.rotateAngleY = 0F;
		this.batPanel.rotateAngleZ = 0F;
		this.batPanel.mirror = false;
	}

	@Override
	public void render(Entity entity, float swing, float swingAmount, float age, float yaw, float pitch, float scale)
	{
		super.render(entity, swing, swingAmount, age, yaw, pitch, scale);
		this.batSide.render(scale);
		this.batContact.render(scale);
		this.batTop.render(scale);
		this.batPanel.render(scale);
	}
}