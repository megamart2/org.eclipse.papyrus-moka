/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;

//EXTENSION
public class LimitedRedefinitionBasedDispatchStrategy extends RedefinitionBasedDispatchStrategy {

	@Override
	public String getName() {
		// Dispatch strategies are always named "dispatch".
		return "limited_dispatch";
	}

	@Override
	public IExecution dispatch(IObject_ object, Operation operation) {
		IExecution execution = null;
		Behavior behavior = this.getMethod(object, operation);
		if (behavior != null && !(behavior instanceof OpaqueBehavior)) {
			execution = object.getLocus().getFactory().createExecution(this.getMethod(object, operation), object);
		}
		return execution;
	}

}
