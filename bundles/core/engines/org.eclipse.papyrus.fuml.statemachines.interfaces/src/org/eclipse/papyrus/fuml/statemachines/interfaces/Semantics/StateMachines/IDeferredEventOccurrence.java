package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface IDeferredEventOccurrence extends IEventOccurrence{

	public IStateActivation getConstrainingStateActivation();
	
	public void setConstrainingStateActivation(IStateActivation constrainingState);
	
	public IEventOccurrence getDeferredEventOccurrence();
	
	public void setDeferredEventOccurrence(IEventOccurrence deferredEvent);
	
}
