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

package org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.uml2.uml.ValueSpecification;

public interface IDecisionNodeActivation extends IControlNodeActivation {

	public List<IValue> getDecisionValues(List<IToken> incomingTokens);

	public IValue executeDecisionInputBehavior(IValue inputValue, IValue decisionInputValue);

	public IValue getDecisionInputFlowValue();

	public IActivityEdgeInstance getDecisionInputFlowInstance();

	public Boolean test(ValueSpecification guard, IValue value);

	public List<IToken> removeJoinedControlTokens(List<IToken> incomingTokens);

	public Boolean hasObjectFlowInput();
}
