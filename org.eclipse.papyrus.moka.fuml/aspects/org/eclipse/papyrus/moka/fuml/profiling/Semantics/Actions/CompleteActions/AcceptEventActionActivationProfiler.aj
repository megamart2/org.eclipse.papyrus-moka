package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Actions.CompleteActions;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ISignalInstance;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;

public aspect AcceptEventActionActivationProfiler extends SemanticVisitorProfiler{
	
	pointcut accept(IAcceptEventActionActivation activation, ISignalInstance signalInstance):
		target(activation) &&
		args(signalInstance) &&
		call(* IAcceptEventActionActivation.accept(ISignalInstance));
	
	before(IAcceptEventActionActivation activation, ISignalInstance signalInstance): accept(activation, signalInstance){
		this.fireNodeLeft(activation);
	}
}