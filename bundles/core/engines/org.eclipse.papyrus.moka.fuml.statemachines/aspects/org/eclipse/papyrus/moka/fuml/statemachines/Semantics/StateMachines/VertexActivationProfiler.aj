package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IPseudostateActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IRegionActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IStateActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.ITransitionActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IVertexActivation;

public aspect VertexActivationProfiler extends SemanticVisitorProfiler {

	public VertexActivationProfiler() {
		super();
	}

	// Intercept all pseudo state entry
	pointcut enterPseudostate(IPseudostateActivation activation, ITransitionActivation enteringTransition,
			IEventOccurrence eventOccurrence, IRegionActivation leastCommonAncestor):
		target(activation) &&
		args(enteringTransition, eventOccurrence, leastCommonAncestor) &&
		call(* IVertexActivation.enter(ITransitionActivation, IEventOccurrence, IRegionActivation));

	// Intercept all state entry
	pointcut enterState(IStateActivation activation, ITransitionActivation enteringTransition,
			IEventOccurrence eventOccurrence, IRegionActivation leastCommonAncestor):
		target(activation) &&
		args(enteringTransition, eventOccurrence, leastCommonAncestor) &&
		call(* IVertexActivation.enter(ITransitionActivation, IEventOccurrence, IRegionActivation));

	// Executed after a state entry is observed
	after(IStateActivation activation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence,
			IRegionActivation leastCommonAncestor): enterState(activation, enteringTransition, eventOccurrence, leastCommonAncestor){
		this.visit(activation);
	}

	// Executed before a pseudo state entry is observed
	before(IPseudostateActivation activation, ITransitionActivation enteringTransition,
			IEventOccurrence eventOccurrence, IRegionActivation leastCommonAncestor):
		enterPseudostate(activation, enteringTransition, eventOccurrence, leastCommonAncestor){
		this.visit(activation);
	}

	// Intercept each call to IVertexActivation.exit(...)
	pointcut exit(IVertexActivation activation, ITransitionActivation exitingTransition,
			IEventOccurrence eventOccurrence, IRegionActivation leastCommonAncestor):
		target(activation) &&
		args(exitingTransition, eventOccurrence, leastCommonAncestor) &&
		call(* IVertexActivation.exit(ITransitionActivation, IEventOccurrence, IRegionActivation));

	// Executed after vertex is exited
	after(IVertexActivation activation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence,
			IRegionActivation leastCommonAncestor):
		exit(activation, enteringTransition, eventOccurrence, leastCommonAncestor){
		this.leave(activation);
	}

}
