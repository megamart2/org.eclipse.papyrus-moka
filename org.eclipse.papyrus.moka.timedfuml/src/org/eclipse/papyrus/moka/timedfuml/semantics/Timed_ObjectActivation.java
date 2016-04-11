/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.timedfuml.semantics;

import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.discreteevent.Event;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.ArrivalSignal;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.ObjectActivation;
import org.eclipse.papyrus.moka.timedfuml.actions._acceptAction;
import org.eclipse.papyrus.moka.timedfuml.actions._send_Action;

/**
 * @author ac221913
 *
 */
public class Timed_ObjectActivation extends ObjectActivation {

	@Override
	public void register(IEventAccepter accepter) {
		// Register the given event accepter to wait for a dispatched signal
		// event.
		this.waitingEventAccepters.add(accepter);
		if (!this.eventPool.isEmpty()) {
			_acceptAction acceptAction = new _acceptAction(this);
			DEScheduler.getInstance().pushEvent(new Event(0.0, acceptAction));
		}
	}

	@Override
	public void _send(ArrivalSignal signal) {
		// Signal the arrival of a new signal instance in the event pool.
		// *** This should send an ArrivalSignal to the EventDispatchLoop. ***
		_send_Action sendAction = new _send_Action(this);
		DEScheduler.getInstance().pushEvent(new Event(0.0, sendAction));
	} // _send

	@Override
	public void _startObjectBehavior() {
		// The integration with the DE engine makes the usage of
		// signalCount useless
		// while (this.signalCount > 0) {
		this.dispatchNextEvent();
		// }
	}

}
