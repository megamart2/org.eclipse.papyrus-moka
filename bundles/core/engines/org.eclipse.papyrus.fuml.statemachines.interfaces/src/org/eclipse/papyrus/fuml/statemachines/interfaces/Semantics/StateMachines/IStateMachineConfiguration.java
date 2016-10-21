package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

public interface IStateMachineConfiguration {

	public boolean register(IStateActivation stateActivation);
	
	public boolean unregister(IStateActivation stateActivation);
	
	public boolean isActive(IVertexActivation activation);
	
	public IStateConfiguration getRoot();
	
	public void addToCartography(IStateConfiguration configuration);
	
	public void deleteFromCartography(IStateConfiguration configuration);
	
}
