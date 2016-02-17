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
package org.eclipse.papyrus.moka.composites.Semantics.impl.CommonBehaviors.Communications;

// Imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.ICS_Object;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.FeatureValue;
import org.eclipse.uml2.uml.StructuralFeature;

public class CS_NameBased_StructuralFeatureOfInterfaceAccessStrategy extends CS_StructuralFeatureOfInterfaceAccessStrategy {

	@Override
	public FeatureValue read(ICS_Object cs_Object, StructuralFeature feature) {
		// returns the a copy of the first feature value of cs_Object where the name of the corresponding feature
		// matches the name of the feature given as a parameter
		// Otherwise, returns an empty feature value
		List<FeatureValue> featureValues = cs_Object.getFeatureValues();
		FeatureValue matchingFeatureValue = null;
		for (int i = 0; i < featureValues.size() && matchingFeatureValue == null; i++) {
			FeatureValue featureValue = featureValues.get(i);
			if (featureValue.feature.getName().equals(feature.getName())) {
				matchingFeatureValue = featureValue;
			}
		}
		if (matchingFeatureValue != null) {
			matchingFeatureValue = matchingFeatureValue.copy();
			matchingFeatureValue.feature = feature;
		} else {
			matchingFeatureValue = new FeatureValue();
			matchingFeatureValue.feature = feature;
			matchingFeatureValue.values = new ArrayList<IValue>();
			matchingFeatureValue.position = 0;
		}

		return matchingFeatureValue;
	}

	@Override
	public void write(ICS_Object cs_Object, StructuralFeature feature, List<IValue> values, Integer position) {
		// Retrieves the first feature value of cs_Object where the name of the corresponding feature
		// matches the name of the feature given as a parameter
		// Then updates the values for this feature value
		List<FeatureValue> featureValues = cs_Object.getFeatureValues();
		FeatureValue matchingFeatureValue = null;
		for (int i = 0; i < featureValues.size() && matchingFeatureValue == null; i++) {
			FeatureValue featureValue = featureValues.get(i);
			if (featureValue.feature.getName().equals(feature.getName())) {
				matchingFeatureValue = featureValue;
			}
		}
		if (matchingFeatureValue != null) {
			cs_Object.setFeatureValue(matchingFeatureValue.feature, values, position);
		}
	}
}
