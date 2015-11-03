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
package org.eclipse.papyrus.moka.composites.Semantics.Loci.LociL3;

// Imports
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.Class;


public class CS_Executor extends Executor {

	@Override
	public Reference start(Class type, List<ParameterValue> inputs) {
		// Instantiate the given class and start any behavior of the resulting
		// object.
		// (The behavior of an object includes any classifier behaviors for an
		// active object or the class of the object itself, if that is a
		// behavior.)
		// fUML semantics is extended in the sense that when the instantiated object
		// is a CS_Object, a CS_Reference is returned (instead of a Reference)

		Debug.println("[start] Starting " + type.getName() + "...");

		Object_ object = this.locus.instantiate(type);

		Debug.println("[start] Object = " + object);
		object.startBehavior(type, inputs);

		Reference reference;
		if (object instanceof CS_Object) {
			reference = new CS_Reference();
			((CS_Reference) reference).compositeReferent = (CS_Object) object;
		} else {
			reference = new Reference();
		}
		reference.referent = object;

		return reference;
	}
}
