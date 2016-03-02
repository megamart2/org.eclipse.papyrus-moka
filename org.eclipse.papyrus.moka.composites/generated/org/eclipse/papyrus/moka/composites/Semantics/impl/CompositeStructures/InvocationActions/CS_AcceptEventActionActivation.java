/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.InvocationActions;

// Imports
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ISignalInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.CompleteActions.AcceptEventActionActivation;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Trigger;


public class CS_AcceptEventActionActivation extends AcceptEventActionActivation {

	@Override
	public Boolean match(ISignalInstance signalInstance) {
		// Return true if the given signal instance matches a trigger of the accept
		// event action of this activation.
		// Matching implies that the type of the signalInstance matches the Signal
		// of one of the triggers.
		// When the type matches with the Signal, and if the trigger specifies a
		// list of ports,
		// the signalInstance matches the trigger only if it occurred on a port
		// identified in the list.

		AcceptEventAction action = (AcceptEventAction) (this.node);
		List<Trigger> triggers = action.getTriggers();
		Signal signal = signalInstance.getType();

		Boolean matches = false;
		Integer i = 1;
		while (!matches & i <= triggers.size()) {
			Trigger t = triggers.get(i - 1);
			matches = ((SignalEvent) t.getEvent()).getSignal() == signal;
			if (matches && t.getPorts().size() > 0) {
				List<Port> portsOfTrigger = t.getPorts();
				Port onPort = ((CS_SignalInstance) signalInstance).interactionPoint.getDefiningPort();
				Boolean portMatches = false;
				Integer j = 1;
				while (!portMatches & j <= portsOfTrigger.size()) {
					portMatches = onPort == portsOfTrigger.get(j - 1);
					j = j + 1;
				}
				matches = portMatches;
			}
			i = i + 1;
		}

		return matches;
	}
}
