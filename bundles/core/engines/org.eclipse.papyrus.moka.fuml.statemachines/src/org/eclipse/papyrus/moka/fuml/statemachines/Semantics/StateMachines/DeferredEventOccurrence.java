package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IDeferredEventOccurrence;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IStateActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.EventOccurrence;

public class DeferredEventOccurrence extends EventOccurrence implements IDeferredEventOccurrence{
	
	// The state activation that was constraint the event occurrence 
	// to remain deferred.
	public IStateActivation constrainingStateActivation;
	
	// The event occurrence that is actually deferred
	public IEventOccurrence deferredEventOccurrence;

	@Override
	public IStateActivation getConstrainingStateActivation() {
		return this.constrainingStateActivation;
	}

	@Override
	public void setConstrainingStateActivation(IStateActivation constrainingState) {
		this.constrainingStateActivation = constrainingState;
	}

	@Override
	public IEventOccurrence getDeferredEventOccurrence() {
		return this.deferredEventOccurrence;
	}

	@Override
	public void setDeferredEventOccurrence(IEventOccurrence deferredEvent) {
		this.deferredEventOccurrence = deferredEvent;
	}

}
