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
 *  Jeremie TATIBOUET (CEA LIST) - Animation refactoring and improvements
 *  Jeremie Tatibouet (CEA LIST) - Apply fix for Issue FUML12-20 Feature values need to be created for private structural features of parent classifiers
 *  Jeremie Tatibouet (CEA LIST) - Apply fix for Issue FUML12-10 certain boolean flags are not properly initialized in come cases 
 *  Jeremie Tatibouet (CEA LIST) - Apply fix for Issue FUML12-34 AcceptEventActionActivation::match should match instances of descendants of a trigger's signal
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.CompleteActions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.IAcceptEventActionEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivationGroup;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ISignalInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.BasicActions.ActionActivation;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Trigger;

public class AcceptEventActionActivation extends ActionActivation implements IAcceptEventActionActivation {

	/*
	 * If the accept event action activation is waiting for an event, then this
	 * is the accepter it has registered for the event.
	 */
	public IAcceptEventActionEventAccepter eventAccepter;

	public Boolean waiting;

	@Override
	public void run() {
		// Create an event accepter and initialize waiting to false.
		super.run();
		this.eventAccepter = new AcceptEventActionEventAccepter();
		this.eventAccepter.setAcceptEventActionActivation(this);
		this.waiting = false;
	}
	
	public void initialize(ActivityNode node, IActivityNodeActivationGroup group){
		// Initialize this accept event action activation to be not waiting for an event.
			
		// FUML12-10 certain boolean flags are not properly initialized in come cases
			
		super.initialize(node, group);
		this.waiting = false;
	}

	@Override
	public void fire(List<IToken> incomingTokens) {
		// Register the event accepter for this accept event action activation
		// with the context object of the enclosing activity execution
		// and wait for an event to be accepted.
		Debug.println("[fire] Action " + this.node.getName() + "...");
		this.getExecutionContext().register(this.eventAccepter);
		this.waiting = true;
		this.firing = false;
		this.suspend();
	}

	@Override
	public Boolean isReady() {
		// An accept event action activiation is ready to fire only if it is not
		// already waiting for an event.
		boolean ready;
		if (this.isWaiting()) { // CHANGED "this.waiting" to "this.isWaiting()".
			ready = false;
		} else {
			ready = super.isReady();
		}
		return ready;
	}

	// ADDED:
	public Boolean isWaiting() {
		return this.waiting == null ? false : this.waiting;
	}

	//

	@Override
	public void doAction() {
		// Do nothing. [This will never be called.]
		return;
	}

	public void accept(ISignalInstance signalInstance) {
		// Accept a signal occurance for the given signal instance.
		// If the action does not unmarshall, then place the signal instance on
		// the result pin, if any.
		// If the action does unmarshall, then get the feature values of the
		// signal instance, and place the values for each feature on the
		// corresponding output pin.
		// Concurrently fire all output pins while offering a single control
		// token.
		// If there are no incoming edges, then re-register this accept event
		// action execution with the context object.
		AcceptEventAction action = (AcceptEventAction) (this.node);
		List<OutputPin> resultPins = action.getResults();
		Debug.println("[accept] action = " + action.getName() + ", signalinstance = " + signalInstance);
		if (this.running) {
			if (!action.isUnmarshall()) {
				List<IValue> result = new ArrayList<IValue>();
				result.add(signalInstance);
				if (resultPins.size() > 0) {
					this.putTokens(resultPins.get(0), result);
				}
			} else {
				List<IFeatureValue> featureValues = signalInstance.getMemberValues(); // Issue FUML12-20 Feature values need to be created for private structural features of parent classifiers
				for (int i = 0; i < featureValues.size(); i++) {
					IFeatureValue featureValue = featureValues.get(i);
					OutputPin resultPin = resultPins.get(i);
					this.putTokens(resultPin, featureValue.getValues());
				}
			}
			this.sendOffers();
			this.waiting = false;
			Debug.println("[fire] Checking if " + this.node.getName() + " should fire again...");
			this.receiveOffer();
			this.resume();
		}
	}

	public Boolean match(ISignalInstance signalInstance) {
		// Return true if the given signal instance matches a trigger of the
		// accept action of this activation.
		
		// FUML12-34 AcceptEventActionActivation::match should match instances of descendants of a trigger's signal
		
		AcceptEventAction action = (AcceptEventAction) (this.node);
		List<Trigger> triggers = action.getTriggers();
		Signal signal = signalInstance.getType();
		boolean matches = false;
		int i = 1;
		while (!matches & i <= triggers.size()) {
			Signal triggerSignal = ((SignalEvent) (triggers.get(i - 1).getEvent())).getSignal();
			if (triggerSignal == signal) {
				matches = true;
			}else{
				matches = this.checkAllParents(signal, triggerSignal);
			}			i = i + 1;
		}
		return matches;
	}

	@Override
	public void terminate() {
		// Terminate this action and unregister its event accepter.
		super.terminate();
		if (this.isWaiting()) { // CHANGED "this.waiting" to "this.isWaiting()".
			this.getExecutionContext().unregister(this.eventAccepter);
			this.waiting = false;
		}
	}

	public IAcceptEventActionEventAccepter getEventAccepter() {
		return this.eventAccepter;
	}
	
}
