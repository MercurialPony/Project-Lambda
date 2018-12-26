package melonslise.lambda.client.renderer.model;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import melonslise.lambda.client.renderer.weapon.RenderItemModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class ItemModelWrapper implements IBakedModel
{
	private final RenderItemModel renderer;
	private final IBakedModel model;

	public ItemModelWrapper(RenderItemModel renderer, IBakedModel model)
	{
		this.renderer = renderer;
		this.model = model;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		return this.model.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return this.model.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d()
	{
		return this.model.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return this.model.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type)
	{
		this.renderer.camera = type;
		return Pair.of(this, this.model.handlePerspective(type).getRight());
	}
}