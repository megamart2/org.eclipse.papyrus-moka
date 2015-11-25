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
 *  Jeremie Tatibouet (CEA LIST) - Alignment of the asynchronous implementation of fUML with the version 1.2 of the standard
 *  
 *****************************************************************************/
package org.eclipse.papyrus.moka.async.fuml.Semantics.CommonBehaviors.Communications;

import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.EventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ObjectActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticStrategy;

/**
 * The event pool provide a way for an object activation to communicate with others.
 * An instance of such class is always owned by an object activation
 * The only Java thread that can retrieve signal instances is the one linked to to the
 * object activation.
 * Every object activation that need to communicate with the one owning the event pool can
 * use the send method to offer a new SignalInstance
 */
@SuppressWarnings("serial")
class AsyncEventPool extends LinkedBlockingQueue<EventOccurrence> implements IAsyncEventPool {

	/* Event pool owner */
	/** The object activation. */
	private ObjectActivation objectActivation;

	/**
	 * Constructor.
	 *
	 * @param activation
	 *            that owns the event pool
	 */
	public AsyncEventPool(ObjectActivation activation) {
		super();
		this.objectActivation = activation;
	}

	/**
	 * Offers the given signal instance to the Queue. Since the eventPool is unbounded
	 * offer always deliver the SignalInstance to the event pool
	 *
	 * @param signalInstance
	 *            that need to be added to the event pool
	 * @return true, if successful
	 */
	public boolean send(EventOccurrence eventOccurrence) {
		return this.offer(eventOccurrence);
	}

	/**
	 * Call the getNextEvent strategy registered in the locus in order to take a signalInstance.
	 *
	 * @return the next event
	 */
	public EventOccurrence getNextEvent() {
		SemanticStrategy strategy = this.objectActivation.object.locus.factory.getStrategy("getNextEvent");
		return ((AsyncGetNextEventStrategy) strategy).getNextEvent(this);
	}

	/**
	 * Get the object activation that owns the current event pool.
	 *
	 * @return objectActivation owning the event pool
	 */
	public synchronized ObjectActivation getObjectActivation() {
		return objectActivation;
	}

	/**
	 * Modify the owner of the current event pool.
	 *
	 * @param objectActivation
	 *            new owner of the current event pool
	 */
	public synchronized void setObjectActivation(ObjectActivation objectActivation) {
		this.objectActivation = objectActivation;
	}
}
