package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.papyrus.moka.engine.MokaExecutionEngineJob;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IRegionActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.ITransitionActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IVertexActivation;

public aspect VertexActivationProfiler extends SemanticVisitorProfiler{

	public VertexActivationProfiler(){
		super();
	}
	
	// Define a point cut for each class that implements the enter operation declared
	// by the IVertexActivation interface
	pointcut enter(IVertexActivation activation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence,  IRegionActivation leastCommonAncestor):
		target(activation) &&
		args(enteringTransition, eventOccurrence, leastCommonAncestor) &&
		call(* IVertexActivation.enter(ITransitionActivation, IEventOccurrence, IRegionActivation));
	
	//
	after(IVertexActivation activation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence,  IRegionActivation leastCommonAncestor):
		enter(activation, enteringTransition, eventOccurrence, leastCommonAncestor){
		IProgressMonitor monitor = MokaExecutionEngineJob.getInstance().getMonitor();
		if(monitor!=null && monitor.isCanceled()){
			throw new OperationCanceledException();
		}
		this.fireNodeVisited(activation);
	}
	
	// Define a point cut for each class that implements the exit operation declared
	// by the IVertexActivation interface
	pointcut exit(IVertexActivation activation, ITransitionActivation exitingTransition, IEventOccurrence eventOccurrence,  IRegionActivation leastCommonAncestor):
		target(activation) &&
		args(exitingTransition, eventOccurrence, leastCommonAncestor) &&
		call(* IVertexActivation.exit(ITransitionActivation, IEventOccurrence, IRegionActivation));
	
	//
	after(IVertexActivation activation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence,  IRegionActivation leastCommonAncestor):
		exit(activation, enteringTransition, eventOccurrence, leastCommonAncestor){
		IProgressMonitor monitor = MokaExecutionEngineJob.getInstance().getMonitor();
		if(monitor!=null && monitor.isCanceled()){
			throw new OperationCanceledException();
		}
		this.fireNodeLeft(activation);
	}
	
}
