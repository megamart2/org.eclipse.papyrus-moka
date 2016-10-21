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
 *   Jeremie Tatibouet (CEA) - Apply Fix fUML12-35 Initial execution of an activity is not run to completion
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IInvocationEventOccurrence;

/**
 * An invocation event occurrence represents a signal occurrence of the event of the asynchronous
 * invocation of a specific behavior execution.
 */
public class InvocationEventOccurrence extends EventOccurrence implements IInvocationEventOccurrence{

	/*
	 * The execution being asynchronously invoked.
	 */
	public IExecution execution;

	@Override
	public IExecution getExecution() {
		return this.execution;
	}
	
	@Override
	public void setExecution(IExecution execution) {
		this.execution = execution;
	}

}
