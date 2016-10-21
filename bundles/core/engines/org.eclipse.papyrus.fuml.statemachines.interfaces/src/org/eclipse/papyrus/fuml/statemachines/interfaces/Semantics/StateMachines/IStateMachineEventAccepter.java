package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface IStateMachineEventAccepter extends IEventAccepter{

	public boolean isDeferred(IEventOccurrence eventOccurrence);
	
	public boolean isTriggering(IEventOccurrence eventOccurrence);
	
}
