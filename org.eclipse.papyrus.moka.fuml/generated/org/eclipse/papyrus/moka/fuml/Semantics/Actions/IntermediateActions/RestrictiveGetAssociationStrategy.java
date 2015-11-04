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
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuralFeature;

public class RestrictiveGetAssociationStrategy extends DefaultGetAssociationStrategy {

	@Override
	public Association getAssociation(StructuralFeature feature) {
		// If the structural feature for the action of this activation is an
		// association end,
		// then get the associated association,
		// ONLY IF THIS FEATURE IS OWNED BY THE ASSOCIATION
		Association association = null;
		if (feature instanceof Property) {
			association = ((Property) feature).getAssociation();
		}
		if (association != null && !association.getOwnedEnds().contains(feature)) {
			association = null;
		}
		return association;
	}
}
