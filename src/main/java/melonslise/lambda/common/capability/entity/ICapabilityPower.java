package melonslise.lambda.common.capability.entity;

import melonslise.lambda.common.capability.api.ICapability;

public interface ICapabilityPower extends ICapability
{
	public float get();

	public boolean set(float power);

	public boolean restore(float power);

	public boolean consume(float power);
}