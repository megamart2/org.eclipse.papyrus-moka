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
package org.eclipse.papyrus.moka.composites.Semantics.Actions.CompleteActions;

// Imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.ReadExtentActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.uml2.uml.ReadExtentAction;

public class CS_ReadExtentActionActivation extends ReadExtentActionActivation {

	@Override
	public void doAction() {
		// Get the extent, at the current execution locus, of the classifier
		// (which must be a class) identified in the action.
		// Place references to the resulting set of objects on the result pin.
		// Extends default fUML semantics in the sense that produced tokens contain
		// CS_References instead of References, in the case where the object is a
		// CS_Object

		ReadExtentAction action = (ReadExtentAction) (this.node);
		List<ExtensionalValue> objects = this.getExecutionLocus().getExtent(action.getClassifier());

		// Debug.println("[doAction] " + action.classifier.name + " has " +
		// objects.size() + " instance(s).");

		List<Value> references = new ArrayList<Value>();
		for (int i = 0; i < objects.size(); i++) {
			Value object = objects.get(i);
			Reference reference = null;
			if (object instanceof CS_Object) {
				reference = new CS_Reference();
				((CS_Reference) reference).compositeReferent = (CS_Object) object;
			} else {
				reference = new Reference();
			}
			reference.referent = (Object_) object;
			references.add(reference);
		}

		this.putTokens(action.getResult(), references);
	}
}
