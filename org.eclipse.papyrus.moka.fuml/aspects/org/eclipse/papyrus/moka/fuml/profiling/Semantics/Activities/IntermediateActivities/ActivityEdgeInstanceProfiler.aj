package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Activities.IntermediateActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public aspect ActivityEdgeInstanceProfiler extends SemanticVisitorProfiler{
	
	public ActivityEdgeInstanceProfiler(){
		super();
	}
	
	pointcut sendOffer(IActivityEdgeInstance edgeInstance, List<IToken> tokens) : 
		target(edgeInstance) &&
		args(tokens) &&
		call(* IActivityEdgeInstance.sendOffer(List<IToken>));
	
	before(IActivityEdgeInstance edgeInstance, List<IToken> tokens): sendOffer(edgeInstance, tokens){
		this.fireNodeVisited(edgeInstance);
	}
	
	pointcut takeOfferedTokens(IActivityEdgeInstance edgeInstance):
		target(edgeInstance) &&
		call(List<IToken> IActivityEdgeInstance.takeOfferedTokens());
	
	after(IActivityEdgeInstance edgeInstance) returning(List<IToken> tokens): takeOfferedTokens(edgeInstance){
		if(!tokens.isEmpty()){
			this.fireNodeLeft(edgeInstance);
		}
	}
	
	pointcut takeOfferedTokensMax(IActivityEdgeInstance edgeInstance, Integer maxCount):
		target(edgeInstance) &&
		args(maxCount) &&
		call(List<IToken> IActivityEdgeInstance.takeOfferedTokens(Integer));
	
	after(IActivityEdgeInstance edgeInstance, Integer maxCount) returning(List<IToken> tokens): takeOfferedTokensMax(edgeInstance, maxCount){
		if(!edgeInstance.hasOffer()){
			this.fireNodeLeft(edgeInstance);
		}
	}
}