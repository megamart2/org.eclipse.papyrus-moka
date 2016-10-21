package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

public interface ILocalTransition extends ITransitionActivation {

	public IStateActivation getContainingState();
	
}
