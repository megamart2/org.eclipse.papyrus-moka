package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci;


import org.eclipse.papyrus.moka.fuml.profiling.MokaObservable;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.services.IMokaService;
import org.eclipse.papyrus.moka.services.animation.IAnimatedModelListener;
import org.eclipse.papyrus.moka.services.animation.events.AnimationEvent;
import org.eclipse.papyrus.moka.services.animation.events.AnimationKind;

public abstract aspect SemanticVisitorProfiler extends MokaObservable{
	
	public SemanticVisitorProfiler(){
		super();
	}
	
	pointcut beginIsolation(ISemanticVisitor visitor): target(visitor) && call(* ISemanticVisitor._beginIsolation());
	
	before(ISemanticVisitor visitor) : beginIsolation(visitor) {}
	
	pointcut endIsolation(ISemanticVisitor visitor): target(visitor) && call(void ISemanticVisitor._endIsolation());
	
	after(ISemanticVisitor visitor) : endIsolation(visitor) {} 
	
	public void fireNodeVisited(ISemanticVisitor visitor){
		for(IMokaService service: this.listeners){
			if(service instanceof IAnimatedModelListener){
				AnimationEvent event = new AnimationEvent(visitor);
				event.setCommand(AnimationKind.ANIMATED);
				((IAnimatedModelListener)service).nodeVisited(event);
			}
		}
	}
	
	public void fireNodeLeft(ISemanticVisitor visitor){
		for(IMokaService service: this.listeners){
			if(service instanceof IAnimatedModelListener){
				AnimationEvent event = new AnimationEvent(visitor);
				event.setCommand(AnimationKind.VISITED);
				((IAnimatedModelListener)service).nodeLeft(event);
			}
		}
	}
}