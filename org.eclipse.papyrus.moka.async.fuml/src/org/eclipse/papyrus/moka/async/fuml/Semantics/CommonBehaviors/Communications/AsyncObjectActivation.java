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
package org.eclipse.papyrus.moka.async.fuml.Semantics.CommonBehaviors.Communications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.moka.MokaConstants;
import org.eclipse.papyrus.moka.async.fuml.debug.AsyncControlDelegate;
import org.eclipse.papyrus.moka.async.fuml.debug.AsyncDebug;
import org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.AcceptEventActionEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ClassifierBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.EventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ObjectActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.SignalInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ChoiceStrategy;
import org.eclipse.papyrus.moka.fuml.standardlibrary.library.io.StandardOutputChannelImpl;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Trigger;

// TODO: Auto-generated Javadoc
/**
 * Extend the original ObjectActivation class in order to support starting of execution
 * of the different classifiers behaviors over a new Java Thread.
 *
 * @author CEA LIST (JT605650)
 */
public class AsyncObjectActivation extends ObjectActivation implements Runnable {

	/* Thread State */
	/**
	 * The Enum ObjectActivationState.
	 */
	public enum ObjectActivationState {

		/** The running. */
		RUNNING,
		/** The stopped. */
		STOPPED,
		/** The waiting. */
		WAITING
	} // WAITING State added for connection with debug api

	/** The current state. */
	protected ObjectActivationState currentState = null;

	/* Arguments of the ObjectActivation */
	/** The classifier. */
	final protected Class classifier;

	/** The inputs. */
	final protected List<ParameterValue> inputs;

	/* The event pool handled by the ObjectActivation */
	/** The evt pool. */
	protected AsyncEventPool evtPool;

	/**
	 * Constructor of AsyncObjectActivation.
	 *
	 * @param classifier
	 *            that need to be executed on the current object activation
	 * @param inputs
	 *            parameters that are provided to the execution
	 */
	public AsyncObjectActivation(Class classifier, List<ParameterValue> inputs) {
		super();
		this.classifier = classifier;
		this.inputs = inputs;
		this.evtPool = new AsyncEventPool(this);
	}

	/**
	 * Implementation of the behavior of the current object activation.
	 */
	public void run() {
		/* 1. The current object activation is in the running state */
		this.currentState = ObjectActivationState.RUNNING;
		/* 2. Execute behavior(s) associated to the given classifier */
		try {
			this.startBehavior(this.classifier, this.inputs);
		} catch (Exception e) {
			Activator.log.error(e);
			if (!MokaConstants.SILENT_MODE) {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						MessageDialog.openError(Display.getDefault().getActiveShell(), "Moka", "An unexpected error occurred during execution. See error log for details.");
					}
				});
			}
			((AsyncControlDelegate) FUMLExecutionEngine.eInstance.getControlDelegate()).notifyThreadTermination(this); // Added for connection with debug api
		}
		/* 3. While current object activation is running then dispatch events */
		while (!FUMLExecutionEngine.eInstance.isTerminated() && this.currentState.equals(ObjectActivationState.RUNNING)) {
			try {
				this.dispatchNextEvent(); /* Dispatch is blocking if no SignalInstance available */
			} catch (Exception e) {
				Activator.log.error(e);
				if (!MokaConstants.SILENT_MODE) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(Display.getDefault().getActiveShell(), "Moka", "An unexpected error occurred during execution. See error log for details.");
						}
					});
				}
				((AsyncControlDelegate) FUMLExecutionEngine.eInstance.getControlDelegate()).notifyThreadTermination(this); // Added for connection with debug api
			}
			if (this.waitingEventAccepters.isEmpty()) {
				this.currentState = ObjectActivationState.STOPPED;
			}
		}
		((AsyncControlDelegate) FUMLExecutionEngine.eInstance.getControlDelegate()).notifyThreadTermination(this); // Added for connection with debug api
	}

	// Added for connection with debug API
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public ObjectActivationState getCurrentState() {
		return this.currentState;
	}

	/**
	 * Causality is broken here in order to let the thread that emitted
	 * the signal to continue its execution.
	 *
	 * @param signalInstance
	 *            the signal instance
	 */
	@Override
	public synchronized void send(SignalInstance signalInstance) {
		SignalInstance copy = (SignalInstance) signalInstance.copy();
		this.evtPool.send(copy);
		AsyncDebug.println("[SignalInstance sent] " + signalInstance.type.getName());
	}

	// Added for connection with debug API
	/** The has been waiting. */
	protected boolean hasBeenWaiting = false;

	/**
	 * Retrieve a SignalInstance stored in the event pool.
	 *
	 * @return the next event
	 */
	@Override
	public SignalInstance getNextEvent() {
		// Added for connection with debug API
		if (this.evtPool.isEmpty()) {
			this.currentState = ObjectActivationState.WAITING;
			this.hasBeenWaiting = true;
			((AsyncControlDelegate) FUMLExecutionEngine.eInstance.getControlDelegate()).notifyWaitingStateEntered(this);
		}
		SignalInstance signalInstance = this.evtPool.getNextEvent();
		this.currentState = ObjectActivationState.RUNNING;
		//

		if (signalInstance != null) {
			AsyncDebug.println("[consumed SignalInstance] " + signalInstance.type.getName());
		}
		return signalInstance;
	}

	/**
	 * Start the event dispatch loop for this object activation (if it has
	 * not already been started).
	 * If a classifier is given that is a type of the object of this object
	 * activation and there is not already a classifier behavior execution
	 * for it, then create a classifier behavior execution for it.
	 * Otherwise, create a classifier behavior execution for each of the
	 * types of the object of this object activation which has a classifier
	 * behavior or which is a behavior itself
	 * and for which there is not currently a classifier behavior execution.
	 * Start EventDispatchLoop
	 *
	 * @param classifier
	 *            the classifier
	 * @param inputs
	 *            the inputs
	 */
	@Override
	public void startBehavior(Class classifier, List<ParameterValue> inputs) {
		/* 1. Start behavior of the current classifier */
		if (classifier == null) {
			AsyncDebug.println("Starting behavior for all classifiers...");
			// *** Start all classifier behaviors concurrently. ***
			List<Class> types = this.object.types;
			for (Iterator<Class> i = types.iterator(); i.hasNext();) {
				Class type = i.next();
				if (type instanceof Behavior | type.getClassifierBehavior() != null) {
					this.startBehavior(type, new ArrayList<ParameterValue>());
				}
			}
		} else {
			AsyncDebug.println("Starting behavior for " + classifier.getName() + "...");
			boolean notYetStarted = true;
			int i = 1;
			while (notYetStarted & i <= this.classifierBehaviorExecutions.size()) {
				notYetStarted = (this.classifierBehaviorExecutions.get(i - 1).classifier != classifier);
				i = i + 1;
			}
			if (notYetStarted) {
				ClassifierBehaviorExecution newExecution = new ClassifierBehaviorExecution();
				newExecution.objectActivation = this;
				this.classifierBehaviorExecutions.add(newExecution);
				newExecution.execute(classifier, inputs);
			}
		}

	}

	/**
	 * Stop all the classifier behaviors. The current object activation enters the STOPPED state
	 */
	@Override
	public void stop() {
		/* 1. Terminate all of my classifier behaviors */
		super.stop();
		/* 2. Terminate my execution thread */
		this.currentState = ObjectActivationState.STOPPED;
	}

	/** The out. */
	protected IOConsoleOutputStream out;

	/**
	 * Get the next signal instance out of the event pool.
	 * If there is one or more waiting event accepters with triggers that
	 * match the signal instance, then dispatch it to exactly one of those
	 * waiting accepters
	 */
	@Override
	public void dispatchNextEvent() {
		/* 1. Get next event is blocking if used on a empty event pool */
		SignalInstance signalInstance = this.getNextEvent();
		AsyncDebug.println("[dispatchNextEvent] signalInstance = " + signalInstance);
		/* 2. Look for EventAccepter that match the selected SignalInstance */
		List<Integer> matchingEventAccepterIndexes = new ArrayList<Integer>();
		List<EventAccepter> waitingEventAccepters = this.waitingEventAccepters;
		for (int i = 0; i < waitingEventAccepters.size(); i++) {
			EventAccepter eventAccepter = waitingEventAccepters.get(i);
			if (eventAccepter.match(signalInstance)) {
				matchingEventAccepterIndexes.add(i);
			}
		}
		/* 3. Choose one matching event accepter non-deterministically */
		if (matchingEventAccepterIndexes.size() > 0) {
			int j = ((ChoiceStrategy) this.object.locus.factory.getStrategy("choice")).choose(matchingEventAccepterIndexes.size());
			EventAccepter selectedEventAccepter = this.waitingEventAccepters.get(matchingEventAccepterIndexes.get(j - 1));
			// this.waitingEventAccepters.remove(j - 1);
			this.waitingEventAccepters.remove(selectedEventAccepter);
			if (this.hasBeenWaiting) {
				this.hasBeenWaiting = false;
				if (selectedEventAccepter instanceof AcceptEventActionEventAccepter) {
					((AsyncControlDelegate) FUMLExecutionEngine.eInstance.getControlDelegate()).notifyWaitingStateExit(this, (AcceptEventActionEventAccepter) selectedEventAccepter);
				}
			}
			selectedEventAccepter.accept(signalInstance);
		} else {
			if (this.out == null) {
				this.out = StandardOutputChannelImpl.getConsole().newOutputStream();
			}

			String expectedSignals = "";
			for (EventAccepter eventAccepter : this.waitingEventAccepters) {
				if (eventAccepter instanceof AcceptEventActionEventAccepter) {
					AcceptEventActionEventAccepter acceptEventAccepter = (AcceptEventActionEventAccepter) eventAccepter;
					AcceptEventAction acceptEventAction = (AcceptEventAction) acceptEventAccepter.actionActivation.node;
					for (Trigger trigger : acceptEventAction.getTriggers()) {
						if (trigger.getEvent() instanceof SignalEvent) {
							SignalEvent signalEvent = (SignalEvent) trigger.getEvent();
							Signal signal = signalEvent.getSignal();
							if (!expectedSignals.isEmpty()) {
								expectedSignals += ", ";
							}
							expectedSignals += signal.getName();
						}
					}
				}
			}

			final SignalInstance finalSignalInstance = signalInstance;
			final String finalExpectedSignals = expectedSignals;
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					try {
						out.setColor(new Color(null, new RGB(200, 120, 10)));
						out.write("[ WARNING : Lost signal " + finalSignalInstance.getTypes().get(0).getName() + " (classifier behavior of " + classifier.getName() + " was waiting for " + finalExpectedSignals + ") ]\n");
						out.flush();
					} catch (IOException e) {
						Activator.log.error(e);
					}
				}
			});
		}


	}
}
