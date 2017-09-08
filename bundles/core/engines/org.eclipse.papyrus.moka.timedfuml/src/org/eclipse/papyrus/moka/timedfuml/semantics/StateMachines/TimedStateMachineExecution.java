/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.moka.timedfuml.semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.StateMachineExecution;

public class TimedStateMachineExecution extends StateMachineExecution{

	public TimedStateMachineExecution() {
		super();
		this.configuration = new TimedStateMachineConfiguration(this);
	}
	
	public TimedStateMachineExecution(IObject_ context) {
		super();
		this.configuration = new TimedStateMachineConfiguration(this);
	}
}
