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

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.ICS_Link;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Link;
import org.eclipse.uml2.uml.StructuralFeature;


public class CS_Link extends Link implements ICS_Link {

	public Boolean hasValueForAFeature(IValue value) {
		// Returns true if the given value object is used as a value for a FeatureValue of this link
		return this.getFeature(value) != null;
	}

	public StructuralFeature getFeature(IValue value) {
		List<FeatureValue> allFeatureValues = this.getFeatureValues();
		Integer i = 1;
		StructuralFeature feature = null;
		while (i <= allFeatureValues.size() && feature == null) {
			FeatureValue featureValue = allFeatureValues.get(i - 1);
			if (!featureValue.values.isEmpty() && featureValue.values.get(0).equals(value)) {
				feature = featureValue.feature;
			}
			i = i + 1;
		}
		return feature;
	}
}
