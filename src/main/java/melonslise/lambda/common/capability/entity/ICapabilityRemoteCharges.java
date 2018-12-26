package melonslise.lambda.common.capability.entity;

import java.util.ArrayList;

import melonslise.lambda.common.capability.api.ICapability;
import melonslise.lambda.common.entity.EntitySatchelCharge;

// TODO More methods?
public interface ICapabilityRemoteCharges extends ICapability
{
	// Remote explosive base
	public ArrayList<EntitySatchelCharge> get();
}