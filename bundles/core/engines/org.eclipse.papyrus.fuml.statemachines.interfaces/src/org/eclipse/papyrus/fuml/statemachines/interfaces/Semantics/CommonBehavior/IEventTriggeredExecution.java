package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.CommonBehavior;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface IEventTriggeredExecution extends IExecution{

	public IEventOccurrence getTriggeringEventOccurrence();
	
	public void setTriggerringEventOccurrence(IEventOccurrence triggeringEventOccurrence);
	
	public IExecution getConcreteExecution();
	
	public void setConcreteExecution(IExecution execution);
	
	public void initialize();
	
	public void finalize();
	
}
