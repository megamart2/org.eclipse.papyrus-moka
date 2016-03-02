package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci;


import org.eclipse.papyrus.moka.fuml.profiling.MokaObservable;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.service.IMokaExecutionListener;
import org.eclipse.papyrus.moka.service.IMokaService;

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
			if(service instanceof IMokaExecutionListener){
				((IMokaExecutionListener)service).nodeVisited(visitor);
			}
		}
	}
	
	public void fireNodeLeft(ISemanticVisitor visitor){
		for(IMokaService service: this.listeners){
			if(service instanceof IMokaExecutionListener){
				((IMokaExecutionListener)service).nodeLeft(visitor);
			}
		}
	}
}