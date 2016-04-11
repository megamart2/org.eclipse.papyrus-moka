/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Jeremie Tatibouet (CEA) - Apply Fix fUML12-35 Initial execution of an activity is not run to completion
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.CompleteActions;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.EventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.SignalEventOccurrence;

public class AcceptEventActionEventAccepter extends EventAccepter implements IAcceptEventActionEventAccepter {

	/*
	 * The accept event action activation on behalf of which this event accepter
	 * is waiting.
	 */
	public IAcceptEventActionActivation actionActivation;

	public void accept(IEventOccurrence eventOccurrence) {
		// Accept a signal event occurrence. Forward the signal instance to the action activation.

		// fUML12-35 Initial execution of an activity is not run to completion


		if (eventOccurrence instanceof SignalEventOccurrence) {
			this.actionActivation.accept(((SignalEventOccurrence) eventOccurrence).signalInstance);
		}
	}

	public Boolean match(IEventOccurrence eventOccurrence) {
		// Return true if the given event occurrence is a signal event occurrence and the
		// signal instance matches a trigger of the accept action of the action activation.

		// fUML12-35 Initial execution of an activity is not run to completion

		boolean matches = false;
		if (eventOccurrence instanceof SignalEventOccurrence) {
			matches = this.actionActivation.match(((SignalEventOccurrence) eventOccurrence).signalInstance);
		}
		return matches;
	}

	public IAcceptEventActionActivation getActionActivation() {
		return this.getActionActivation();
	}

	public void setAcceptEventActionActivation(IAcceptEventActionActivation actionActivation) {
		this.actionActivation = actionActivation;
	}
}
