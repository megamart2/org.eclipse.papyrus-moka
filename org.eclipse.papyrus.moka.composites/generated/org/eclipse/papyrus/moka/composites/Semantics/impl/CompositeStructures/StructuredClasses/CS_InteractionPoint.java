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
package org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.StructuredClasses;

// Imports
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.ICS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.ICS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.SignalInstance;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;


public class CS_InteractionPoint extends Reference implements ICS_InteractionPoint{

	/*
	 * Represents the Reference to the CompositeObject owning this InteractionPort. NOTE: This is introduced to address requirement R3 (It represents
	 * the "link from that instance to the instance of the owning classifier [...] through which communication is forwarded to the instance of the
	 * owning classifier or through which the owning classifier communicates)
	 */
	public ICS_Reference owner;

	/*
	 * The Port for which this InteractionPoint is a runtime manifestation
	 */
	public Port definingPort;

	@Override
	public void startBehavior(Class classifier, List<ParameterValue> inputs) {
		// Overriden to do nothing
	}

	@Override
	public IExecution dispatch(Operation operation) {
		// Delegates dispatching to the owning object
		return this.owner.dispatchIn(operation, this);
	}

	@Override
	public void send(SignalInstance signalInstance) {
		// Delegates sending to the owning object
		this.owner.sendIn(signalInstance, this);
	}

	@Override
	public IValue copy() {
		// Create a new interaction point with the same referent as this interaction point.
		ICS_InteractionPoint newValue = (ICS_InteractionPoint) (super.copy());
		newValue.setReferent(this.referent);
		return newValue;
	}

	@Override
	public IValue new_() {
		return new CS_InteractionPoint();
	}

	@Override
	public ICS_Reference getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(ICS_Reference owner) {
		this.owner = owner;
	}

	public Port getDefiningPort() {
		return this.definingPort;
	}

	public void setDefiningPort(Port definingPort) {
		this.definingPort = definingPort;
	}
}
