package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci;

import org.eclipse.papyrus.moka.fuml.profiling.MokaObservable;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.service.IMokaExecutionListener;
import org.eclipse.papyrus.moka.service.IMokaService;
import org.eclipse.uml2.uml.Class;

public aspect LocusProfiler extends MokaObservable{
	
	pointcut instantiate(ILocus locus, Class type): 
		target(locus) &&
		args(type) &&
		call(* ILocus.instantiate(Class));

	after(ILocus locus, Class type) returning(IObject_ instance) : instantiate(locus, type){
		this.fireValueCreated(instance);
	}
	
	pointcut destroy(ILocus locus, IExtensionalValue value): 
		target(locus) &&
		args(value) &&
		call(* ILocus.remove(IExtensionalValue));
	
	after(ILocus locus, IExtensionalValue value): destroy(locus, value){
		this.fireValueDestroyed(value);
	}
	
	public void fireValueCreated(final IValue value){
		for(int i=0; i < this.listeners.size(); i++){
			IMokaService service = this.listeners.get(i); 
			if(service instanceof IMokaExecutionListener){
				((IMokaExecutionListener)service).valueCreated(value);
			}
		}
	}
	
	public void fireValueDestroyed(final IValue value){
		for(int i=0; i < this.listeners.size(); i++){
			IMokaService service = this.listeners.get(i); 
			if(service instanceof IMokaExecutionListener){
				((IMokaExecutionListener)service).valueDestroyed(value);
			}
		}
	} 
}