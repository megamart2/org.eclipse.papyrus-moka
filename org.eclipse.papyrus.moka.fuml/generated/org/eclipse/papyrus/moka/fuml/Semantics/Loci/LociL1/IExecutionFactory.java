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

package org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IEvaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IOpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.SemanticStrategy;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecification;

public interface IExecutionFactory {

	public IExecution createExecution(Behavior behavior, IObject_ context);
	
	public IEvaluation createEvaluation(ValueSpecification specification);
	
	public ISemanticVisitor instantiateVisitor(Element element);
	
	public IOpaqueBehaviorExecution instantiateOpaqueBehaviorExecution(OpaqueBehavior behavior);
	
	public void addPrimitiveBehaviorPrototype(IOpaqueBehaviorExecution execution);
	
	public void setStrategy(SemanticStrategy strategy);
	
	public SemanticStrategy getStrategy(String name);
	
	public Integer getStrategyIndex(String name);
	
	public void setLocus(ILocus locus);
	
	public ILocus getLocus();
	
	public Type getBuiltInType(String name);
	
	public void addBuiltInType(Type type);
}
