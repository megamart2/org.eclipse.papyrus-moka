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

import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StructuredClassifiers.SM_Object;

/**
 * @author ac221913
 *
 */
public class Timed_Object extends SM_Object {

	@Override
	public void startBehavior(Class classifier, List<IParameterValue> inputs) {
		// Overrides default startBehavior by instantiating a DE_ObjectActivation
		// Create an object activation for this object (if one does not already
		// exist) and start its behavior(s).
		// Debug.println("[startBehavior] On object...");
		if (this.objectActivation == null) {
			this.objectActivation = new Timed_ObjectActivation();
			this.objectActivation.setObject(this);
		}
		// Debug.println("[startBehavior] objectActivation = " +
		// objectActivation);
		this.objectActivation.startBehavior(classifier, inputs);
	}

}
