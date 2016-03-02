package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Activities.IntermediateActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ObjectNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public aspect ActivityNodeActivationProfiler extends SemanticVisitorProfiler{

	public ActivityNodeActivationProfiler() {
		super();
	}
	
	pointcut fire(IActivityNodeActivation activation, List<IToken> tokens):
		!within(ObjectNodeActivation+) &&
		target(activation) && 
		args(tokens) && 
		call(* IActivityNodeActivation.fire(List<IToken>));
	
	before(IActivityNodeActivation activation, List<IToken> tokens): fire(activation, tokens) {
		this.fireNodeVisited(activation);
	}
	
}