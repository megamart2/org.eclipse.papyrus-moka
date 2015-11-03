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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Link;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions.AddStructuralFeatureValueActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Link;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StructuredValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ChoiceStrategy;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuralFeature;


public class CS_AddStructuralFeatureValueActionActivation extends AddStructuralFeatureValueActionActivation {

	@Override
	public void doAction() {
		// If the feature is a port and the input value to be added is a
		// Reference,
		// Replaces this Reference by an InteractionPoint, and then behaves
		// as usual.
		// If the feature is not a port, behaves as usual

		AddStructuralFeatureValueAction action = (AddStructuralFeatureValueAction) (this.node);
		StructuralFeature feature = action.getStructuralFeature();

		if (!(feature instanceof Port)) {
			// Behaves as usual
			this.doActionDefault();
		} else {
			List<Value> inputValues = this.takeTokens(action.getValue());
			// NOTE: Multiplicity of the value input pin is required to be 1..1.
			Value inputValue = inputValues.get(0);
			if (inputValue instanceof Reference) {
				// First constructs an InteractionPoint from the inputValue
				Reference reference = (Reference) inputValue;
				CS_InteractionPoint interactionPoint = new CS_InteractionPoint();
				interactionPoint.referent = reference.referent;
				interactionPoint.definingPort = (Port) feature;
				// The value on action.object is necessarily instanceof
				// ReferenceToCompositeStructure (otherwise, the feature cannot
				// be a port)
				CS_Reference owner = (CS_Reference) this.takeTokens(action.getObject()).get(0);
				interactionPoint.owner = owner;
				// Then replaces the Reference by an InteractionPoint
				// in the inputValues
				inputValues.remove(0);
				inputValues.add(0, interactionPoint);
				// Finally concludes with usual fUML behavior of
				// AddStructuralFeatureValueAction (i.e., the usual behavior
				// when
				// the value on action.object pin is a StructuredValue)
				Integer insertAt = 0;
				if (action.getInsertAt() != null) {
					insertAt = ((UnlimitedNaturalValue) this.takeTokens(action.getInsertAt()).get(0)).value;
				}
				if (action.isReplaceAll()) {
					owner.setFeatureValue(feature, inputValues, 0);
				} else {
					FeatureValue featureValue = owner.getFeatureValue(feature);

					if (featureValue.values.size() > 0 & insertAt == 0) {
						// If there is no insertAt pin, then the structural
						// feature must
						// be unordered, and the insertion position is
						// immaterial.
						insertAt = ((ChoiceStrategy) this.getExecutionLocus().factory.getStrategy("choice")).choose(featureValue.values.size());
					}
					if (feature.isUnique()) {
						// Remove any existing value that duplicates the input
						// value
						Integer j = position(interactionPoint, featureValue.values, 1);
						if (j > 0) {
							featureValue.values.remove(j - 1);
							if (insertAt > 0 & j < insertAt) {
								insertAt = insertAt - 1;
							}
						}
					}

					if (insertAt <= 0) {
						// Note: insertAt = -1 indicates an unlimited value of
						// "*"
						featureValue.values.add(interactionPoint);
					} else {
						featureValue.values.add(insertAt - 1, interactionPoint);
					}
				}
				if (action.getResult() != null) {
					this.putToken(action.getResult(), owner);
				}
			} else {
				// behaves as usual
				this.doActionDefault();
			}
		}
	}

	public void doActionDefault() {
		// Get the values of the object and value input pins.
		// If the given feature is an association end, then create a link
		// between the object and value inputs.
		// Otherwise, if the object input is a structural value, then add a
		// value to the values for the feature.
		// If isReplaceAll is true, first remove all current matching links or
		// feature values.
		// If isReplaceAll is false and there is an insertAt pin, insert the
		// value at the appropriate position.
		// This operation captures same semantics as fUML
		// AddStructuralFeatureValueActionActivation.doAction(), except that
		// when the feature is an association end, a CS_Link will be created instead
		// of a Link

		AddStructuralFeatureValueAction action = (AddStructuralFeatureValueAction) (this.node);
		StructuralFeature feature = action.getStructuralFeature();
		Association association = this.getAssociation(feature);

		Value value = this.takeTokens(action.getObject()).get(0);
		List<Value> inputValues = this.takeTokens(action.getValue());

		// NOTE: Multiplicity of the value input pin is required to be 1..1.
		Value inputValue = inputValues.get(0);

		int insertAt = 0;
		if (action.getInsertAt() != null) {
			insertAt = ((UnlimitedNaturalValue) this.takeTokens(action.getInsertAt()).get(0)).value;
		}

		if (association != null) {
			List<Link> links = this.getMatchingLinks(association, feature, value);

			Property oppositeEnd = this.getOppositeEnd(association, feature);
			int position = 0;
			if (oppositeEnd.isOrdered()) {
				position = -1;
			}

			if (action.isReplaceAll()) {
				for (int i = 0; i < links.size(); i++) {
					Link link = links.get(i);
					link.destroy();
				}
			} else if (feature.isUnique()) {
				for (int i = 0; i < links.size(); i++) {
					Link link = links.get(i);
					FeatureValue featureValue = link.getFeatureValue(feature);
					if (featureValue.values.get(0).equals(inputValue)) {
						position = link.getFeatureValue(oppositeEnd).position;
						if (insertAt > 0 & featureValue.position < insertAt) {
							insertAt = insertAt - 1;
						}
						link.destroy();
					}
				}
			}

			CS_Link newLink = new CS_Link();
			newLink.type = association;

			// This necessary when setting a feature value with an insertAt
			// position
			newLink.locus = this.getExecutionLocus();

			newLink.setFeatureValue(feature, inputValues, insertAt);

			List<Value> oppositeValues = new ArrayList<Value>();
			oppositeValues.add(value);
			newLink.setFeatureValue(oppositeEnd, oppositeValues, position);

			newLink.locus.add(newLink);

		} else if (value instanceof StructuredValue) {
			StructuredValue structuredValue = (StructuredValue) value;

			if (action.isReplaceAll()) {
				structuredValue.setFeatureValue(feature, inputValues, 0);
			} else {
				FeatureValue featureValue = structuredValue.getFeatureValue(feature);

				if (featureValue.values.size() > 0 & insertAt == 0) {
					// *** If there is no insertAt pin, then the structural
					// feature must be unordered, and the insertion position is
					// immaterial. ***
					insertAt = ((ChoiceStrategy) this.getExecutionLocus().factory.getStrategy("choice")).choose(featureValue.values.size());
				}

				if (feature.isUnique()) {
					// Remove any existing value that duplicates the input value
					int j = position(inputValue, featureValue.values, 1);
					if (j > 0) {
						featureValue.values.remove(j - 1);
						if (insertAt > 0 & j < insertAt) {
							insertAt = insertAt - 1;
						}
					}
				}

				if (insertAt <= 0) { // Note: insertAt = -1 indicates an
					// unlimited value of "*"
					featureValue.values.add(inputValue);
				} else {
					featureValue.values.add(insertAt - 1, inputValue);
				}
			}
		}

		if (action.getResult() != null) {
			this.putToken(action.getResult(), value);
		}
	}
}
