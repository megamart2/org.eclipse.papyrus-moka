/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.Semantics.adapters.Loci.LociL1;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.MokaObservable;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutionFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutor;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.services.IMokaService;
import org.eclipse.papyrus.moka.services.animation.IAnimatedModelListener;
import org.eclipse.papyrus.moka.services.animation.events.AnimationEvent;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;

public class LocusWrapper extends MokaObservable implements ILocus {

	protected ILocus locus;
	
	public LocusWrapper(ILocus locus){
		super();
		this.locus = locus;
	}
	
	public void setExecutor(IExecutor executor) {
		this.locus.setExecutor(executor);
		executor.setLocus(this);
	}

	public void setFactory(IExecutionFactory factory) {
		this.locus.setFactory(factory);
		factory.setLocus(this);
	}

	public List<IExtensionalValue> getExtent(Classifier classifier) {
		return this.locus.getExtent(classifier);
	}

	public void add(IExtensionalValue value) {
		this.locus.add(value);
		value.setLocus(this); // Ensure any value is still referencing the wrapper
		this.fireValueCreated(value);
	}

	public void remove(IExtensionalValue value) {
		this.locus.remove(value);
		this.fireValueDestroyed(value);
	}

	public IObject_ instantiate(Class type) {
		IObject_ instance = this.locus.instantiate(type); 
		this.fireValueCreated(instance);
		return instance;
	}

	public Boolean conforms(Classifier type, Classifier classifier) {
		return this.locus.conforms(type, classifier);
	}

	public String makeIdentifier(IExtensionalValue value) {
		return this.locus.makeIdentifier(value);
	}
	
	public IExecutor getExecutor() {
		return this.locus.getExecutor();
	}

	public IExecutionFactory getFactory() {
		return this.locus.getFactory();
	}

	public String getIdentifier() {
		return this.locus.getIdentifier();
	}

	public void setExecutedTarget(Behavior behavior) {
		this.locus.setExecutedTarget(behavior);
	}
	
	public List<IExtensionalValue> getExtensionalValues() {
		return this.locus.getExtensionalValues();
	}
	
	protected void fireValueCreated(final IValue value){
		for(int i=0; i < this.listeners.size(); i++){
			IMokaService service = this.listeners.get(i); 
			if(service instanceof IAnimatedModelListener){
				AnimationEvent event = new AnimationEvent(value);
				((IAnimatedModelListener)service).valueCreated(event);
			}
		}
	}
	
	protected void fireValueDestroyed(final IValue value){
		for(int i=0; i < this.listeners.size(); i++){
			IMokaService service = this.listeners.get(i); 
			if(service instanceof IAnimatedModelListener){
				AnimationEvent event = new AnimationEvent(value);
				((IAnimatedModelListener)service).valueDestroyed(event);
			}
		}
	}

}
