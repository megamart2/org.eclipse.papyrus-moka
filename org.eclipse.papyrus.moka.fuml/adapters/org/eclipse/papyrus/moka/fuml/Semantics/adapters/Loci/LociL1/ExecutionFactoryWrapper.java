/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.IPinActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IEvaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IOpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutionFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.fuml.Semantics.adapters.Actions.BasicActions.PinActivationWrapper;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.SemanticStrategy;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecification;

public class ExecutionFactoryWrapper implements IExecutionFactory {

	protected IExecutionFactory factory;
	
	public ExecutionFactoryWrapper(IExecutionFactory factory){
		this.factory = factory;
	}
	
	public IExecution createExecution(Behavior behavior, IObject_ context) {
		IExecution execution = null;
		if(this.factory!=null){
			execution = this.factory.createExecution(behavior, context);
		}
		return execution;
	}

	public IEvaluation createEvaluation(ValueSpecification specification) {
		IEvaluation evaluation = null;
		if(this.factory!=null){
			evaluation = this.factory.createEvaluation(specification);
		}
		return evaluation;
	}

	public ISemanticVisitor instantiateVisitor(Element element) {
		ISemanticVisitor visitor = null;
		if(this.factory!=null){
			visitor = this.factory.instantiateVisitor(element);
		}
		if(visitor instanceof IPinActivation){
			visitor = new PinActivationWrapper((IPinActivation)visitor);
		}
		return visitor;
	}

	public IOpaqueBehaviorExecution instantiateOpaqueBehaviorExecution(OpaqueBehavior behavior) {
		IOpaqueBehaviorExecution execution = null;
		if(this.factory!=null){
			execution = this.factory.instantiateOpaqueBehaviorExecution(behavior);
		}
		return execution;
	}

	public void addPrimitiveBehaviorPrototype(IOpaqueBehaviorExecution execution) {
		if(this.factory!=null){
			this.factory.addPrimitiveBehaviorPrototype(execution);
		}
	}

	public void setStrategy(SemanticStrategy strategy) {
		if(this.factory!=null){
			this.factory.setStrategy(strategy);
		}
	}

	public SemanticStrategy getStrategy(String name) {
		SemanticStrategy strategy = null;
		if(this.factory!=null){
			strategy = this.factory.getStrategy(name);
		}
		return strategy;
	}

	public Integer getStrategyIndex(String name) {
		Integer index = null; 
		if(this.factory!=null){
			index = this.factory.getStrategyIndex(name);
		}
		return index;
	}

	public void setLocus(ILocus locus) {
		if(this.factory!=null){
			this.factory.setLocus(locus);
		}
	}

	public ILocus getLocus() {
		ILocus locus = null;
		if(this.factory!=null){
			locus = this.factory.getLocus();
		}
		return locus;
	}

	public Type getBuiltInType(String name) {
		Type type = null;
		if(this.factory!=null){
			type = this.factory.getBuiltInType(name);
		}
		return type;
	}

	public void addBuiltInType(Type type) {
		if(this.factory!=null){
			this.factory.addBuiltInType(type);
		}
	}
}
