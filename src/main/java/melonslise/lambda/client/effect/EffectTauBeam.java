package melonslise.lambda.client.effect;

import org.lwjgl.opengl.GL11;

import melonslise.lambda.LambdaCore;
import melonslise.lambda.client.effect.api.Effect;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

// TODO Base class?
// TODO Rename?
// TODO Move?
public class EffectTauBeam extends Effect
{
	protected static final ResourceLocation texture = new ResourceLocation(LambdaCore.ID, "textures/entities/tau_beam.png");

	public int ticks;
	private Entity source;
	private Vec3d start, end;
	private boolean charged;

	public EffectTauBeam(int ticks, Entity source, Vec3d start, Vec3d end, boolean charged)
	{
		this.ticks = ticks;
		this.source = source;
		this.start = start;
		this.end = end;
		this.charged = charged;
	}

	// TODO Account for bobbing / looking around
	public void render(float partialTick)
	{
		if(this.source != null)
		{
			this.start = new Vec3d(this.source.lastTickPosX + (this.source.posX - this.source.lastTickPosX) * (double) partialTick, this.source.lastTickPosY + (this.source.posY - this.source.lastTickPosY) * (double) partialTick + (double) this.source.getEyeHeight(), this.source.lastTickPosZ + (this.source.posZ - this.source.lastTickPosZ) * (double) partialTick);
			if(this.source instanceof EntityLivingBase) this.start = start.add(LambdaUtilities.getHeldOffset((EntityLivingBase) this.source, LambdaUtilities.getUsingItem(this.source).getHand(), new Vec3d(-0.3F, -0.2F, 0.7F)));
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		LambdaUtilities.drawFlatQuad(this.start, this.end, charged ? 0.06D : 0.03D, charged ? 0xffffffff : 0xffffff00, true, partialTick);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	// TODO Fix/check tick counter / order
	@Override
	public void update()
	{
		--this.ticks;
		if(ticks <= 0) this.setExpired();
	}
}