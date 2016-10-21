/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.CommonBehavior;

import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.CommonBehavior.ICallEventExecution;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.CommonBehavior.ICallEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.EventOccurrence;

public class CallEventOccurrence extends EventOccurrence implements ICallEventOccurrence{

	// Execution associated to the event occurrence
	public ICallEventExecution execution;

	@Override
	public ICallEventExecution getCallEventExecution() {
		return this.execution;
	}

	@Override
	public void setCallEventExecution(ICallEventExecution execution) {
		this.execution = execution;
	}
	
}
