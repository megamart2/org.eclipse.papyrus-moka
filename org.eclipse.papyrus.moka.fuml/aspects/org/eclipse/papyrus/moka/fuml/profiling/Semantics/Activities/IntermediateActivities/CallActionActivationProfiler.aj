/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Activities.IntermediateActivities;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.ICallActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci.SemanticVisitorProfiler;
import org.eclipse.uml2.uml.CallAction;

public aspect CallActionActivationProfiler extends SemanticVisitorProfiler{
	
	public CallActionActivationProfiler(){
		super();
	}
	
	pointcut removeCallExecution(ICallActionActivation activation, IExecution execution): 
		target(activation) &&
		args(execution) &&
		call(* ICallActionActivation.removeCallExecution(IExecution));
	
	after(ICallActionActivation activation, IExecution execution) : removeCallExecution(activation, execution){
		CallAction callAction = (CallAction) activation.getNode();
		if(callAction.isSynchronous()){
			this.fireNodeLeft(activation);
		}
	}
}