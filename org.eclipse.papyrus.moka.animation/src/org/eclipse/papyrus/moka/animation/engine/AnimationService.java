package org.eclipse.papyrus.moka.animation.engine;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nodeLeft(AnimationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueCreated(AnimationEvent event) {
		Object_ value = null;
		if(event.getSource() instanceof Object_){
			value = (Object_) event.getSource();
			DiagramHandler diagramHandler = this.engine.getDiagramHandler();
			if(!diagramHandler.isRenderable(value)){
				Set<Diagram> relatedDiagrams = diagramHandler.findDiagramsInvolved(value);
				for(Diagram diagram : relatedDiagrams){
					diagramHandler.addRenderable(value, diagram);
				}
			}
		}	
	}

	@Override
	public void valueDestroyed(AnimationEvent event) {
		//System.out.println("Value destroyed=["+event+"]");
	}
	
	public void dispose(){
		this.engine.clean();
	}

}
