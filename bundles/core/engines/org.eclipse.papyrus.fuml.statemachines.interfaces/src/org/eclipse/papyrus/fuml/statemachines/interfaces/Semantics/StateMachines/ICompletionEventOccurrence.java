package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface ICompletionEventOccurrence extends IEventOccurrence{

	public IStateActivation getScope();
	
	public void setScope(IStateActivation stateActivation);
	
}
