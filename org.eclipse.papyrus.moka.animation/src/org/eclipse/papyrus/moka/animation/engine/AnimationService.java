package org.eclipse.papyrus.moka.animation.engine;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.moka.MokaConstants;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.ICallActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.services.animation.AbstractAnimationService;
import org.eclipse.papyrus.moka.services.animation.events.AnimationEvent;

public class AnimationService extends AbstractAnimationService{

	// The handler responsible for markers application
	protected RenderHandler engine;
	
	public AnimationService(){
		this.engine = new RenderHandler();
	}
	
	public void init(EObject modelElement){
		this.engine.init(modelElement);
	}

	@Override
	public void nodeVisited(AnimationEvent event) {
		Object eventSource = event.getSource();
		if(eventSource instanceof IActivityNodeActivation){
			IActivityNodeActivation activation = (IActivityNodeActivation) eventSource;
			if(activation.getNode()!=null){
				if(activation instanceof IAcceptEventActionActivation | activation instanceof ICallActionActivation){
					this.engine.startRendering(activation.getNode(), activation.getExecutionContext(), event.getCommand()); 
				}else{
					this.engine.render(activation.getNode(), activation.getExecutionContext(), event.getCommand(), MokaConstants.MOKA_ANIMATION_DELAY);
				}
			}
		}else if(eventSource instanceof IActivityEdgeInstance){
			IActivityEdgeInstance edgeInstance = (IActivityEdgeInstance) eventSource;
			if(edgeInstance.getEdge()!=null){
				this.engine.startRendering(edgeInstance.getEdge(), edgeInstance.getGroup().getActivityExecution().context, event.getCommand());
			}
		}
	}

	@Override
	public void nodeLeft(AnimationEvent event) {
		Object eventSource = event.getSource();
		if(eventSource instanceof IActivityNodeActivation){
			IActivityNodeActivation activation = (IActivityNodeActivation) eventSource;
			if(activation.getNode()!=null && (activation instanceof IAcceptEventActionActivation | activation instanceof ICallActionActivation)){
				this.engine.stopRendering(activation.getNode(), activation.getExecutionContext(), event.getCommand()); 
			}
		}else if(eventSource instanceof IActivityEdgeInstance){
			IActivityEdgeInstance edgeInstance = (IActivityEdgeInstance) eventSource;
			if(edgeInstance.getEdge() != null){
				this.engine.stopRendering(edgeInstance.getEdge(), edgeInstance.getGroup().getActivityExecution().context, event.getCommand());
			}
		}
	}

	@Override
	public void valueCreated(AnimationEvent event) {
		Object_ value = null;
		if(event.getSource() instanceof Object_){
			value = (Object_) event.getSource();
			DiagramHandler diagramHandler = this.engine.getDiagramHandler();
			if(!diagramHandler.isRegistered(value)){
				Set<Diagram> relatedDiagrams = diagramHandler.findDiagramsInvolved(value);
				for(Diagram diagram : relatedDiagrams){
					diagramHandler.addRenderable(value, diagram);
				}
			}
		}	
	}

	@Override
	public void valueDestroyed(AnimationEvent event) {
		if(event.getSource() instanceof Object_){
			this.engine.getDiagramHandler().deleteRenderable((Object_)event.getSource());
		}
	}
	
	public void dispose(){
		this.engine.clean();
	}

}
