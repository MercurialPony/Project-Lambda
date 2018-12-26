package melonslise.lambda.utility;

import melonslise.lambda.common.capability.entity.ICapabilityPower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SuitRenderUtilities
{
	public static final ResourceLocation
	suit =LambdaUtilities.createLambdaDomain("textures/gui/suit.png"),
	crosshairs = LambdaUtilities.createLambdaDomain("textures/gui/crosshairs.png");

	public static void renderDisplay(ScaledResolution resolution, int color)
	{
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		ICapabilityPower capabilityPower = LambdaUtilities.getPower(player);
		float power = capabilityPower.get();
		int ratio = Math.round(power);

		//GlStateManager.enableBlend();

		mc.fontRenderer.drawString(Integer.toString(Math.round(player.getHealth() * 5F)), 25, resolution.getScaledHeight() - 10, color);
		mc.fontRenderer.drawString(Integer.toString(Math.round(power * 5F)), 65, resolution.getScaledHeight() - 10, color);

		mc.getTextureManager().bindTexture(suit);

		GlStateManager.color(LambdaUtilities.getRed(color), LambdaUtilities.getGreen(color), LambdaUtilities.getBlue(color), LambdaUtilities.getAlpha(color));
		Gui.drawModalRectWithCustomSizedTexture(0, resolution.getScaledHeight() - 20, 40F, 0F, 20, 20, 80F, 20F);
		Gui.drawModalRectWithCustomSizedTexture(45, resolution.getScaledHeight() - 20, 20F, 0F, 20, 20 - ratio, 80F, 20F);
		Gui.drawModalRectWithCustomSizedTexture(45, resolution.getScaledHeight() - ratio, 0F, 20F - (float) ratio, 20, ratio, 80F, 20F);
		//GlStateManager.disableBlend();
		GlStateManager.color(1F, 1F, 1F, 1F);
	}

	public static void renderAmmo(ScaledResolution resolution, int color, String string, int position)
	{
		FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
		//GlStateManager.enableBlend();
		renderer.drawString(string, resolution.getScaledWidth() - renderer.getStringWidth(string) - 2, resolution.getScaledHeight() - (position + 1) * 10, color);
		//GlStateManager.disableBlend();
	}

	public static void renderCrosshair(ScaledResolution resolution, int color, int x, int y)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(crosshairs);
		GlStateManager.color(LambdaUtilities.getRed(color), LambdaUtilities.getGreen(color), LambdaUtilities.getBlue(color), LambdaUtilities.getAlpha(color));
		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(resolution.getScaledWidth() / 2 - 12, resolution.getScaledHeight() / 2 - 12, (float) x, (float) y, 24, 24, 96F, 72F);
		GlStateManager.disableBlend();
		GlStateManager.color(1F, 1F, 1F, 1F);
	}
}