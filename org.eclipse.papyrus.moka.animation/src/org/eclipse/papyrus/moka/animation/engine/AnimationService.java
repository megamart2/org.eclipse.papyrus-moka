package org.eclipse.papyrus.moka.animation.engine;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.ICallActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.service.AbstractMokaService;
import org.eclipse.papyrus.moka.utils.constants.MokaConstants;

public class AnimationService extends AbstractMokaService{

	// The handler responsible for markers application
	protected RenderHandler engine;
	
	public AnimationService(){
		this.engine = new RenderHandler();
	}
	
	public void init(EObject modelElement){
		this.engine.init(modelElement);
	}

	@Override
	public void nodeVisited(ISemanticVisitor nodeVisitor) {
		if(nodeVisitor instanceof IActivityNodeActivation){
			IActivityNodeActivation activation = (IActivityNodeActivation) nodeVisitor;
			if(activation.getNode()!=null){
				if(activation instanceof IAcceptEventActionActivation | activation instanceof ICallActionActivation){
					this.engine.startRendering(activation.getNode(), activation.getExecutionContext(), AnimationKind.ANIMATED); 
				}else{
					this.engine.render(activation.getNode(), activation.getExecutionContext(), AnimationKind.ANIMATED, MokaConstants.MOKA_ANIMATION_DELAY);
				}
			}
		}else if(nodeVisitor instanceof IActivityEdgeInstance){
			IActivityEdgeInstance edgeInstance = (IActivityEdgeInstance) nodeVisitor;
			if(edgeInstance.getEdge()!=null){
				this.engine.startRendering(edgeInstance.getEdge(), edgeInstance.getGroup().getActivityExecution().getContext(), AnimationKind.ANIMATED);
			}
		}
	}

	@Override
	public void nodeLeft(ISemanticVisitor nodeVisitor) {
		if(nodeVisitor instanceof IActivityNodeActivation){
			IActivityNodeActivation activation = (IActivityNodeActivation) nodeVisitor;
			if(activation.getNode()!=null && (activation instanceof IAcceptEventActionActivation | activation instanceof ICallActionActivation)){
				this.engine.stopRendering(activation.getNode(), activation.getExecutionContext(), AnimationKind.VISITED); 
			}
		}else if(nodeVisitor instanceof IActivityEdgeInstance){
			IActivityEdgeInstance edgeInstance = (IActivityEdgeInstance) nodeVisitor;
			if(edgeInstance.getEdge() != null){
				this.engine.stopRendering(edgeInstance.getEdge(), edgeInstance.getGroup().getActivityExecution().getContext(), AnimationKind.VISITED);
			}
		}
	}

	@Override
	public void valueCreated(IValue value) {
		if(value instanceof IObject_){
			DiagramHandler diagramHandler = this.engine.getDiagramHandler();
			if(!diagramHandler.isRegistered((IObject_)value)){
				Set<Diagram> relatedDiagrams = diagramHandler.findDiagramsInvolved((IObject_)value);
				for(Diagram diagram : relatedDiagrams){
					diagramHandler.addRenderable((IObject_)value, diagram);
				}
			}
		}	
	}

	@Override
	public void valueDestroyed(IValue value) {
		if(value instanceof IObject_){
			this.engine.getDiagramHandler().deleteRenderable((IObject_)value);
		}
	}
	
	public void dispose(){
		this.engine.clean();
	}

}
