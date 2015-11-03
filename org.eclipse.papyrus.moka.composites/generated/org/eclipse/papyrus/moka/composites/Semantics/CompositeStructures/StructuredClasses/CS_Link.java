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
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Link;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.uml2.uml.StructuralFeature;


public class CS_Link extends Link {

	public Boolean hasValueForAFeature(Value value) {
		// Returns true if the given value object is used as a value for a FeatureValue of this link
		return this.getFeature(value) != null;
	}

	public StructuralFeature getFeature(Value value) {
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
