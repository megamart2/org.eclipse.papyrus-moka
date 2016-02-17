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

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.ICS_InteractionPoint;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.SignalInstance;


public class CS_SignalInstance extends SignalInstance {

	/*
	 * The InteractionPoint on which this signal instance occured.
	 */
	public ICS_InteractionPoint interactionPoint;

	@Override
	public Value copy() {
		// Create a new signal instance with the same type, interaction point and feature values as this signal instance.
		CS_SignalInstance newValue = (CS_SignalInstance) (super.copy());
		newValue.type = this.type;
		newValue.interactionPoint = this.interactionPoint;
		return newValue;
	}

	@Override
	public Value new_() {
		// Create a new signal instance with no type or feature values.
		return new CS_SignalInstance();
	}
}
