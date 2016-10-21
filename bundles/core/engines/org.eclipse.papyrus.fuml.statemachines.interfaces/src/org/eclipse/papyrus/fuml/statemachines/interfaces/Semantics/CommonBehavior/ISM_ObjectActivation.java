package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.CommonBehavior;

import java.util.List;

import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.ICompletionEventOccurrence;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IDeferredEventOccurrence;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IStateActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IObjectActivation;

public interface ISM_ObjectActivation extends IObjectActivation{

	public ICompletionEventOccurrence getNextCompletionEvent();
	
	public int getDeferredEventInsertionIndex();
	
	public void registerCompletionEvent(IStateActivation stateActivation);
	
	public void registerDeferredEvent(IEventOccurrence eventOccurrence, IStateActivation deferringState);
	
	public void releaseDeferredEvents(IStateActivation deferringState);
	
	public List<IDeferredEventOccurrence> getDeferredEvents();
	
}
