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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Link;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions.ClearStructuralFeatureActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Link;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StructuredValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.ClearStructuralFeatureAction;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.StructuralFeature;

public class CS_ClearStructuralFeatureValueActionActivation extends ClearStructuralFeatureActionActivation {

	@Override
	public void doAction() {
		// Get the value of the object input pin.
		// If the given feature is an association end, then
		// destroy all links that have the object input on the opposite end.
		// Otherwise, if the object input is a structured value, then
		// set the appropriate feature of the input value to be empty.
		ClearStructuralFeatureAction action = (ClearStructuralFeatureAction) (this.node);
		StructuralFeature feature = action.getStructuralFeature();
		Association association = this.getAssociation(feature);
		Value value = this.takeTokens(action.getObject()).get(0);
		if (association != null) {
			List<Link> links = this.getMatchingLinks(association, feature, value);
			for (int i = 0; i < links.size(); i++) {
				Link link = links.get(i);
				link.destroy();
			}
		} else if (value instanceof StructuredValue) {
			// If the value is a data value, then it must be copied before
			// any change is made.
			if (!(value instanceof Reference)) {
				value = value.copy();
			} else {
				// extension to fUML
				List<CS_Link> linksToDestroy = this.getLinksToDestroy((StructuredValue) value, feature);
				for (int i = 0; i < linksToDestroy.size(); i++) {
					linksToDestroy.get(i).destroy();
				}
				//
			}
			((StructuredValue) value).setFeatureValue(action.getStructuralFeature(), new ArrayList<Value>(), 0);
		}
		if (action.getResult() != null) {
			this.putToken(action.getResult(), value);
		}
	}

	public List<CS_Link> getLinksToDestroy(StructuredValue value, StructuralFeature feature) {
		List<CS_Link> linksToDestroy = new ArrayList<CS_Link>();
		if (value instanceof CS_Reference) {
			CS_Reference context = (CS_Reference) value;
			// Retrieves the feature values for the structural feature associated with this action,
			// in the context of this reference
			FeatureValue featureValue = context.getFeatureValue(feature);
			if (feature instanceof Port) {
				// all values are interaction points
				// any link targeting this interaction point must be destroyed
				for (int i = 0; i < featureValue.values.size(); i++) {
					CS_InteractionPoint interactionPoint = (CS_InteractionPoint) featureValue.values.get(i);
					List<CS_Link> connectorInstances = context.compositeReferent.getLinks(interactionPoint);
					for (int j = 0; j < connectorInstances.size(); j++) {
						CS_Link link = connectorInstances.get(j);
						linksToDestroy.add(link);
					}
				}
			} else { // feature is an attribute
						// Retrieve all potential link ends,
						// separating potential link ends corresponding to the given feature,
						// and potential link ends corresponding to other features.
						// By "potential link ends", we refer to the values of a given feature,
						// as well as interaction points associated with this value, if any.
				List<Value> allValuesForFeature = new ArrayList<Value>();
				List<Value> allOtherValues = new ArrayList<Value>();
				for (int i = 0; i < context.referent.featureValues.size(); i++) {
					StructuralFeature currentFeature = context.referent.featureValues.get(i).feature;
					List<Value> values = this.getPotentialLinkEnds(context, currentFeature);
					for (int j = 0; j < values.size(); j++) {
						Value v = values.get(j);
						if (currentFeature != feature) {
							allOtherValues.add(v);
						} else {
							allValuesForFeature.add(v);
						}
					}
				}
				// Retrieves all links available at the locus
				List<ExtensionalValue> extensionalValues = this.getExecutionLocus().extensionalValues;
				List<CS_Link> allLinks = new ArrayList<CS_Link>();
				for (int i = 0; i < extensionalValues.size(); i++) {
					ExtensionalValue extensionalValue = extensionalValues.get(i);
					if (extensionalValue instanceof CS_Link) {
						allLinks.add((CS_Link) extensionalValue);
					}
				}
				// Retrieves links representing connector instances in the context object
				for (int i = 0; i < allLinks.size(); i++) {
					CS_Link link = allLinks.get(i);
					boolean linkHasToBeDestroyed = false;
					for (int j = 0; j < allValuesForFeature.size() && !linkHasToBeDestroyed; j++) {
						Value v = allValuesForFeature.get(j);
						StructuralFeature featureForV = link.getFeature(v);
						if (featureForV != null) {
							// Check if feature values of this link for other features
							// contains elements identified in allOtherValue
							for (int k = 0; k < link.featureValues.size() && !linkHasToBeDestroyed; k++) {
								FeatureValue otherFeatureValue = link.featureValues.get(k);
								if (otherFeatureValue.feature != featureForV) {
									for (int l = 0; l < otherFeatureValue.values.size() && !linkHasToBeDestroyed; l++) {
										for (int m = 0; m < allOtherValues.size() && !linkHasToBeDestroyed; m++) {
											if (otherFeatureValue.values.get(l) == allOtherValues.get(m)) {
												linkHasToBeDestroyed = true;
											}
										}
									}
								}
							}
						}
					}
					if (linkHasToBeDestroyed) {
						linksToDestroy.add(link);
					}
				}
			}
		}
		return linksToDestroy;
	}

	public List<Value> getPotentialLinkEnds(CS_Reference context, StructuralFeature feature) {
		// Retrieves all feature values for the context object for the given feature,
		// as well as all interaction point for these values
		List<Value> potentialLinkEnds = new ArrayList<Value>();
		FeatureValue featureValue = context.getFeatureValue(feature);
		for (int i = 0; i < featureValue.values.size(); i++) {
			Value v = featureValue.values.get(i);
			potentialLinkEnds.add(v);
			if (v instanceof CS_Reference) {
				// add all interaction points associated with v
				for (int j = 0; j < ((CS_Reference) v).referent.featureValues.size(); j++) {
					if (((CS_Reference) v).referent.featureValues.get(j).feature instanceof Port) {
						List<Value> interactionPoints = (((CS_Reference) v).referent.featureValues.get(j)).values;
						for (int k = 0; k < interactionPoints.size(); k++) {
							potentialLinkEnds.add(interactionPoints.get(k));
						}
					}
				}
			}
		}
		return potentialLinkEnds;
	}
}
