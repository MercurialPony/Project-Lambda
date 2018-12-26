package melonslise.lambda.client.effect.api;

// TODO Pass interp player coords to render method
// TODO Renders at players feet
public abstract class Effect
{
	private boolean expired;

	public abstract void render(float partialTick);

	public abstract void update();

	public boolean isExpired() { return this.expired; }

	public void setExpired() { this.expired = true; }
}