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

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.EventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticStrategy;


/**
 * The Class AsyncGetNextEventStrategy.
 */
public abstract class AsyncGetNextEventStrategy extends SemanticStrategy {

	@Override
	public String getName() {
		return "getNextEvent";
	}

	/**
	 * Gets the next event.
	 *
	 * @param eventPool
	 *            the event pool
	 * @return the next event
	 */
	public abstract EventOccurrence getNextEvent(AsyncEventPool eventPool);
}
