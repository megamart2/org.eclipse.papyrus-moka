package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Actions.CompleteActions;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.SignalInstance;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;

public aspect AcceptEventActionActivationProfiler extends SemanticVisitorProfiler{
	
	pointcut accept(IAcceptEventActionActivation activation, SignalInstance signalInstance):
		target(activation) &&
		args(signalInstance) &&
		call(* IAcceptEventActionActivation.accept(SignalInstance));
	
	before(IAcceptEventActionActivation activation, SignalInstance signalInstance): accept(activation, signalInstance){
		this.fireNodeLeft(activation);
	}
}