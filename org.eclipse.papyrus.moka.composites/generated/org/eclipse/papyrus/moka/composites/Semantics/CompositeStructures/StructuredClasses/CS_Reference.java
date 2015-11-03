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
package org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses;

// Imports
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.SignalInstance;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;


public class CS_Reference extends Reference {

	/*
	 * The composite object referenced by this ReferenceToCompositeStructure. This property subsets Reference::referent.
	 */
	public CS_Object compositeReferent;

	public Execution dispatchIn(Operation operation, CS_InteractionPoint interactionPoint) {
		// Delegates dispatching to composite referent
		return this.compositeReferent.dispatchIn(operation, interactionPoint);
	}

	public void sendIn(SignalInstance signalInstance, CS_InteractionPoint interactionPoint) {
		// delegates sending to composite referent
		this.compositeReferent.sendIn(signalInstance, interactionPoint);
	}

	public void sendOut(SignalInstance signalInstance, Port onPort) {
		// delegates sending to composite referent
		this.compositeReferent.sendOut(signalInstance, onPort);
	}

	public Execution dispatchOut(Operation operation, Port onPort) {
		// delegates dispatching to composite referent
		return this.compositeReferent.dispatchOut(operation, onPort);
	}

	public Execution dispatchIn(Operation operation, Port onPort) {
		// delegates dispatching to composite referent
		return this.compositeReferent.dispatchIn(operation, onPort);
	}

	public void sendIn(SignalInstance signalInstance, Port onPort) {
		// delegates sending to composite referent
		this.compositeReferent.sendIn(signalInstance, onPort);
	}

	public Execution dispatchOut(Operation operation, CS_InteractionPoint interactionPoint) {
		// Delegates dispatching (through the interaction point, to the environment)
		// to compositeReferent
		return this.compositeReferent.dispatchOut(operation, interactionPoint);
	}

	public void sendOut(SignalInstance signalInstance, CS_InteractionPoint interactionPoint) {
		// Delegates sending (through the interaction point, to the environment)
		// to compositeReferent
		this.compositeReferent.sendOut(signalInstance, interactionPoint);
	}

	@Override
	public Value copy() {
		// Create a new reference with the same referent and composite referent as this reference.
		CS_Reference newValue = new CS_Reference();
		newValue.referent = this.referent;
		newValue.compositeReferent = this.compositeReferent;
		return newValue;
	}
}
