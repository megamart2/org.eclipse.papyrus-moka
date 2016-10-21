package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface IHistoryPseudostateActivation extends IPseudostateActivation{

	public boolean hasDefaultTransition();
	
	public void restore(IStateActivation stateActivation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence);
	
	public abstract void restore(IRegionActivation regionActivation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence);
	
}
