package org.eclipse.papyrus.moka.utils.helper.semantics;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public class SemanticHelper {
	
	private static SemanticHelper INSTANCE = null;
	
	private SemanticHelper(){
		
	}
	
	public static SemanticHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE = new SemanticHelper();
		}
		return INSTANCE;
	}
	
	public EObject getModelElement(ISemanticVisitor visitor){
		// For a given type of visitor return the visited model element.
		// It returns null when the visited model element is null.
		EObject visitedModelElement = null;
		if(visitor instanceof IActivityNodeActivation){
			visitedModelElement = ((IActivityNodeActivation)visitor).getNode();
		}else if(visitor instanceof IActivityEdgeInstance){
			visitedModelElement = ((IActivityEdgeInstance)visitor).getEdge();
		}
		return visitedModelElement;
	}
	
}
