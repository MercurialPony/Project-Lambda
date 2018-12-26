package melonslise.lambda.utility;

import net.minecraft.util.math.Vec3d;

/**
 * A wrapper class used for constructing effect trails.
 * @see EffectTrail
 */
public class TrailPoint
{
	protected double x, y, z;
	protected int tick;
	protected float lastAlpha, alpha;

	public TrailPoint(double x, double y, double z, float alpha)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.lastAlpha = this.alpha = alpha;
	}


	public double getX() { return this.x; }

	public double getY() { return this.y; }

	public double getZ() { return this.z; }

	public Vec3d getPosition() { return new Vec3d(this.x, this.y, this.z); }

	public float getAlpha() { return this.alpha; }

	public float getAlpha(float partialTick) { return this.lastAlpha + (this.alpha - this.lastAlpha) * partialTick; }

	public void setAlpha(float alpha)
	{
		this.lastAlpha = this.alpha;
		this.alpha = alpha;
	}

	public float getLastAlpha() { return this.lastAlpha; }
}