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
package org.eclipse.papyrus.moka.composites.Semantics.Actions.IntermediateActions;

// Imports
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions.CreateObjectActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions.CreateObjectActionStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.CreateObjectAction;


public class CS_CreateObjectActionActivation extends CreateObjectActionActivation {

	@Override
	public void doAction() {
		// Create an object with the given classifier (which must be a class) as
		// its type, at the same locus as the action activation.
		// Place a reference to the object on the result pin of the action.
		// Extends fUML semantics in the sense that the reference placed
		// on the result pin is a CS_Reference (in the case where the instantiated object
		// is a CS_Object) not a Reference
		// Note that Locus.instantiate(Class) is extended in this specification
		// to produce a CS_Object instead of an Object in the case where the class
		// to be instantiated is not a behavior

		CreateObjectAction action = (CreateObjectAction) (this.node);

		Reference reference;
		Object_ referent = ((CreateObjectActionStrategy) this.getExecutionLocus().factory.getStrategy("CreateObjectActionStrategy")).instantiate((Class) (action.getClassifier()), this.getExecutionLocus());
		if (referent instanceof CS_Object) {
			reference = new CS_Reference();
			((CS_Reference) reference).compositeReferent = (CS_Object) referent;
		} else {
			reference = new Reference();
		}
		reference.referent = referent;

		this.putToken(action.getResult(), reference);
	}
}
