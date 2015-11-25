/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Jeremie Tatibouet (CEA LIST) - Issue FUML12-23 RemoveStructuralFeatureValueAction: Removal of links does not consider provided input value
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.ActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Link;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuralFeature;

public abstract class StructuralFeatureActionActivation extends ActionActivation {

	public Association getAssociation(StructuralFeature feature) {
		return ((GetAssociationStrategy) this.getExecutionLocus().factory.getStrategy("GetAssociationStrategy")).getAssociation(feature);
	}

	public List<Link> getMatchingLinks(Association association, StructuralFeature end, Value oppositeValue) {
		// Get the links of the given binary association whose end opposite
		// to the given end has the given value
		
		// FUML12-23 RemoveStructuralFeatureValueAction: Removal of links does not consider provided input value
		
		return this.getMatchingLinksForEndValue(association, end, oppositeValue, null);
	}

	public List<Link> getMatchingLinksForEndValue(Association association, StructuralFeature end, Value oppositeValue, Value endValue) {
		// Get the links of the given binary association whose end opposite
		// to the given end has the given opposite value and, optionally, that
		// has a given end value for the given end.
		
		// FUML12-23 RemoveStructuralFeatureValueAction: Removal of links does not consider provided input value
		
		Property oppositeEnd = this.getOppositeEnd(association, end);
		List<ExtensionalValue> extent = this.getExecutionLocus().getExtent(association);
		List<Link> links = new ArrayList<Link>();
		for (int i = 0; i<extent.size(); i++) {
			ExtensionalValue link = extent.get(i);
			if (link.getFeatureValue(oppositeEnd).values.get(0).equals(oppositeValue)) {
				boolean matches = true;
				if (endValue != null) {
					matches = link.getFeatureValue(end).values.get(0).equals(endValue);
				}
				if (matches) {
					if (!end.isOrdered() | links.size() == 0) {
						links.add((Link)link);
					} else {
						int n = link.getFeatureValue(end).position;
						boolean continueSearching = true;
						int j = 0;
						while (continueSearching & j < links.size()) {
							j = j + 1;
							continueSearching = links.get(j-1).getFeatureValue(end).position < n;
						}
						if (continueSearching) {
							links.add((Link)link);
						} else {
							links.add(j-1, (Link)link);
						}
					}
				}
			}
		}
		return links;
	}
	
	public Property getOppositeEnd(Association association, StructuralFeature end) {
		// Get the end of a binary association opposite to the given end.
		Property oppositeEnd = association.getMemberEnds().get(0);
		if (oppositeEnd == end) {
			oppositeEnd = association.getMemberEnds().get(1);
		}
		return oppositeEnd;
	}
}
