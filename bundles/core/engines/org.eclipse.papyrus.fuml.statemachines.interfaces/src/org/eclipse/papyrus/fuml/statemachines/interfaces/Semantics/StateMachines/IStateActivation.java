package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Vertex;

public interface IStateActivation extends IVertexActivation{

	public void setEntryCompletion(boolean completed);
	
	public void setExitCompletion(boolean completed);
	
	public void setDoActivityCompletion(boolean completed);
	
	public boolean hasCompleted();
	
	public void notifyCompletion();
	
	public List<IPseudostateActivation> getConnectionPointActivation();
	
	public IPseudostateActivation getConnectionPointActivation(Vertex vertex);
	
	public Behavior getEntry();
	
	public Behavior getExit();
	
	public Behavior getdoActivity();
	
	public void tryExecuteEntry(IEventOccurrence eventOccurrence);
	
	public void tryInvokeDoActivity(IEventOccurrence eventOccurrence);
	
	public void tryExecuteExit(IEventOccurrence eventOccurrence);
	
	public void enterRegions(ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence);
	
	public List<ITransitionActivation> getFireableTransitions(IEventOccurrence eventOccurrence);
	
	public boolean canDefer(IEventOccurrence eventOccurrence);
	
	public void defer(IEventOccurrence eventOccurrence);
	
	public void releaseDeferredEvents();
	
	public List<IRegionActivation> getRegionActivation();
	
}
