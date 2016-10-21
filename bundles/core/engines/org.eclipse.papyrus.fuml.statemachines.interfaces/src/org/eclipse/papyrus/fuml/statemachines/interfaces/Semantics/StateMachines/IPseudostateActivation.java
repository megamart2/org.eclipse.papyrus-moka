package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface IPseudostateActivation extends IVertexActivation{

	public void evaluateAllGuards(IEventOccurrence eventOccurrence);
	
}
