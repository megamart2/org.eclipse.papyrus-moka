package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.CommonBehavior;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface ICallEventOccurrence extends IEventOccurrence{

	public ICallEventExecution getCallEventExecution();
	
	public void setCallEventExecution(ICallEventExecution execution);
	
}
