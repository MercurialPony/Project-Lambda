package melonslise.lambda.client.renderer.tileentity;

import melonslise.lambda.common.tileentity.TileEntityLaserTripmine;
import melonslise.lambda.utility.LambdaUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

// TODO Don't use texture
public class RenderLaserTripmine extends TileEntitySpecialRenderer
{
	protected static final ResourceLocation texture = LambdaUtilities.createLambdaDomain("textures/blocks/tripmine_laser.png");

	// TODO Blockpos center to vec3d helper
	@Override
	public void render(TileEntity tile, double x, double y, double z, float partialTick, int destroyStage, float alpha)
	{
		super.render(tile, x, y, z, partialTick, destroyStage, alpha);
		TileEntityLaserTripmine mine = (TileEntityLaserTripmine) tile;
		if(mine.getChargeTicks() == 0)
		{
			Vec3d start = mine.getLaserStart();
			Vec3d end = mine.getLaserEnd();
			if(end != null && start != null)
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				GlStateManager.disableLighting();
				GlStateManager.enableBlend();
				LambdaUtilities.drawFlatQuad(start, end, 0.03D, 0xffffffff, true, partialTick);
				GlStateManager.disableBlend();
				GlStateManager.enableLighting();
			}
		}
	}

	public boolean isGlobalRenderer(TileEntity tile)
	{
		return true;
	}
}