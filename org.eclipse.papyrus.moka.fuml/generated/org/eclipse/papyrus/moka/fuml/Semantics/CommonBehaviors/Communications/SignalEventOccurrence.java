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

package org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications;

/**
 * A signal event occurrence represents the occurrence of a signal event due to the receipt
 * of a specific signal instance.
 */
public class SignalEventOccurrence extends EventOccurrence {

	/*
	 * The signal instance whose receipt caused this signal event occurrence.
	 */
	public SignalInstance signalInstance;
}
