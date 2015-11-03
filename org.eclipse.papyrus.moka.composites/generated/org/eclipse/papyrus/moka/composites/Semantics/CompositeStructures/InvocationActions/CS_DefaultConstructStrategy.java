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
package org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.InvocationActions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.Classes.Kernel.CS_OpaqueExpressionEvaluation;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Link;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Evaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.ValueSpecification;


public class CS_DefaultConstructStrategy extends CS_ConstructStrategy {

	public Locus locus;

	@Override
	public Object_ construct(Operation constructor, CS_Object context) {
		this.locus = context.locus;
		return this.constructObject(context, (Class) constructor.getType());
	}

	public Object_ constructObject(CS_Object context, Class type) {
		CS_Reference referenceToContext = new CS_Reference();
		referenceToContext.referent = context;
		referenceToContext.compositeReferent = context;
		// FIXME detect infinite recursive instantiation
		List<Property> allAttributes = type.getAllAttributes();
		int i = 1;
		// Instantiate ports and parts
		while (i <= allAttributes.size()) {
			Property p = allAttributes.get(i - 1);
			if (p.getDefaultValue() != null) {
				ValueSpecification defaultValueSpecification = p.getDefaultValue();
				Evaluation evaluation = (Evaluation) context.locus.factory.instantiateVisitor(defaultValueSpecification);
				evaluation.specification = defaultValueSpecification;
				evaluation.locus = context.locus;
				if (evaluation instanceof CS_OpaqueExpressionEvaluation) {
					List<Value> evaluations = ((CS_OpaqueExpressionEvaluation) evaluation).executeExpressionBehavior();
					for (int j = 0; j < evaluations.size(); j++) {
						this.addStructuralFeatureValue(referenceToContext, p, evaluations.get(j));
					}
				} else {
					Value defaultValue = evaluation.evaluate();
					this.addStructuralFeatureValue(referenceToContext, p, defaultValue);
				}
			} else if (this.canInstantiate(p)) {
				int j = 1;
				while (j <= p.getLower()) {
					Object_ value;
					// if p is a Port typed by an Interface
					// creates an Object without type, but with FeatureValues corresponding to
					// structural features of the interface.
					if (p instanceof Port && p.getType() instanceof Interface) {
						value = this.instantiateInterface((Interface) p.getType(), this.locus);
						this.addStructuralFeatureValue(referenceToContext, p, value);
					} else {
						value = context.locus.instantiate((Class) p.getType());
						// TODO account for existing constructors
						value = this.constructObject((CS_Object) value, (Class) p.getType());
						this.addStructuralFeatureValue(referenceToContext, p, value);
						if (((Class) p.getType()).isActive()) {
							value.startBehavior((Class) p.getType(), new ArrayList<ParameterValue>());
						}
					}
					j = j + 1;
				}
			}
			i = i + 1;
		}
		// Instantiate connectors
		List<NamedElement> allMembers = type.getMembers();
		i = 1;
		while (i <= allMembers.size()) {
			NamedElement member = allMembers.get(i - 1);
			if (member instanceof Connector) {
				Connector connector = (Connector) member;
				if (this.isArrayPattern(connector)) {
					this.generateArrayPattern(referenceToContext, connector);
				} else if (this.isStarPattern(connector)) {
					this.generateStarPattern(referenceToContext, connector);
				}
			}
			i = i + 1;
		}
		return referenceToContext.referent;
	}

	public void addStructuralFeatureValue(CS_Reference context, Property feature, Value value) {
		FeatureValue featureValue = context.getFeatureValue(feature);
		if (featureValue != null) {
			List<Value> values = featureValue.values;
			if (feature instanceof Port) {
				// insert an interaction point
				CS_InteractionPoint interactionPoint = new CS_InteractionPoint();
				interactionPoint.definingPort = (Port) feature;
				interactionPoint.referent = (CS_Object) value;
				interactionPoint.owner = context;
				values.add(interactionPoint);
			} else if (value instanceof CS_Object) {
				// insert a reference
				CS_Reference reference = new CS_Reference();
				reference.compositeReferent = (CS_Object) value;
				reference.referent = (CS_Object) value;
				values.add(reference);
			} else {
				values.add(value);
			}
		}
	}

	public void generateArrayPattern(CS_Reference context, Connector connector) {
		ConnectorEnd end1 = connector.getEnds().get(0);
		ConnectorEnd end2 = connector.getEnds().get(1);
		List<Reference> end1Values = this.getValuesFromConnectorEnd(context, end1);
		List<Reference> end2Values = this.getValuesFromConnectorEnd(context, end2);
		for (int i = 0; i < end1Values.size(); i++) {
			CS_Link link = new CS_Link();
			if (connector.getType() == null) {
				link.type = this.getDefaultAssociation();
			} else {
				link.type = connector.getType();
			}
			List<Value> valuesForEnd1 = new ArrayList<Value>();
			valuesForEnd1.add(end1Values.get(i));
			List<Value> valuesForEnd2 = new ArrayList<Value>();
			valuesForEnd2.add(end2Values.get(i));
			link.setFeatureValue(link.type.getOwnedEnds().get(0), valuesForEnd1, -1);
			link.setFeatureValue(link.type.getOwnedEnds().get(1), valuesForEnd2, -1);
			link.addTo(context.referent.locus);
		}
	}

	public List<Reference> getValuesFromConnectorEnd(CS_Reference context, ConnectorEnd end) {
		List<Reference> endValues = new ArrayList<Reference>();
		if (end.getPartWithPort() != null) {
			FeatureValue valueForPart = context.getFeatureValue(end.getPartWithPort());
			if (valueForPart != null) {
				for (int i = 0; i < valueForPart.values.size(); i++) {
					Reference reference = (Reference) valueForPart.values.get(i);
					FeatureValue valueForPort = reference.getFeatureValue((Port) end.getRole());
					if (valueForPort != null) {
						for (int j = 0; j < valueForPort.values.size(); j++) {
							endValues.add((Reference) valueForPort.values.get(j));
						}
					}
				}
			}
		} else {
			FeatureValue valueForRole = context.getFeatureValue((Property) end.getRole());
			if (valueForRole != null) {
				for (int i = 0; i < valueForRole.values.size(); i++) {
					endValues.add((Reference) valueForRole.values.get(i));
				}
			}
		}
		return endValues;
	}

	public void generateStarPattern(CS_Reference context, Connector connector) {
		ConnectorEnd end1 = connector.getEnds().get(0);
		ConnectorEnd end2 = connector.getEnds().get(1);
		List<Reference> end1Values = this.getValuesFromConnectorEnd(context, end1);
		List<Reference> end2Values = this.getValuesFromConnectorEnd(context, end2);
		for (int i = 0; i < end1Values.size(); i++) {
			for (int j = 0; j < end2Values.size(); j++) {
				CS_Link link = new CS_Link();
				if (connector.getType() == null) {
					link.type = this.getDefaultAssociation();
				} else {
					link.type = connector.getType();
				}
				List<Value> valuesForEnd1 = new ArrayList<Value>();
				valuesForEnd1.add(end1Values.get(i));
				List<Value> valuesForEnd2 = new ArrayList<Value>();
				valuesForEnd2.add(end2Values.get(j));
				link.setFeatureValue(link.type.getOwnedEnds().get(0), valuesForEnd1, -1);
				link.setFeatureValue(link.type.getOwnedEnds().get(1), valuesForEnd2, -1);
				link.addTo(context.referent.locus);
			}
		}
	}

	public boolean canInstantiate(Property p) {
		// Instantiate is possible if:
		// - p is composite
		// - p is typed
		// - This type is a Class and it is not abstract
		// - Or p is a Port and the type is an Interface
		if (p.isComposite()) {
			if (p.getType() != null) {
				if (p.getType() instanceof Class) {
					return !((Class) p.getType()).isAbstract();
				} else if (p.getType() instanceof Interface) {
					return p instanceof Port;
				}
			}
		}
		return false;
	}

	public int getCardinality(ConnectorEnd end) {
		int lowerOfRole = ((MultiplicityElement) end.getRole()).getLower();
		if (lowerOfRole == 0) {
			return 0;
		} else if (end.getPartWithPort() == null) {
			return lowerOfRole;
		} else {
			int lowerOfPart = end.getPartWithPort().getLower();
			return lowerOfRole * lowerOfPart;
		}
	}


	public boolean isArrayPattern(Connector c) {
		// This is an array pattern if:
		// - c is binary
		// - lower bound of the two connector ends is 1
		// - Cardinality of ends are equals
		if (c.getEnds().size() == 2) {
			if (c.getEnds().get(0).getLower() == 1) {
				if (c.getEnds().get(1).getLower() == 1) {
					if (this.canInstantiate((Property) c.getEnds().get(0).getRole()) && this.canInstantiate((Property) c.getEnds().get(1).getRole())) {
						int cardinality1 = this.getCardinality(c.getEnds().get(0));
						int cardinality2 = this.getCardinality(c.getEnds().get(1));
						return cardinality1 == cardinality2;
					}
				}
			}
		}
		return false;
	}


	public boolean isStarPattern(Connector c) {
		// This is an array pattern if:
		// - c is binary
		// - lower bound of end1 equals cardinality of end1
		// - lower bound of end2 equals cardinality of end2
		if (c.getEnds().size() == 2) {
			if (this.canInstantiate((Property) c.getEnds().get(0).getRole()) && this.canInstantiate((Property) c.getEnds().get(1).getRole())) {
				int cardinalityOfEnd1 = this.getCardinality(c.getEnds().get(0));
				int lowerBoundofEnd1 = c.getEnds().get(0).getLower();
				if (cardinalityOfEnd1 == lowerBoundofEnd1) {
					int cardinalityOfEnd2 = this.getCardinality(c.getEnds().get(1));
					int lowerBoundofEnd2 = c.getEnds().get(1).getLower();
					return cardinalityOfEnd2 == lowerBoundofEnd2;
				}
			}
		}
		return false;
	}

	public Association defaultAssociation;

	public Association getDefaultAssociation() {
		// Computes an returns an Association with two untyped owned ends,
		// with multiplicity [*].
		// This association can be used to type links instantiated from untyped connectors
		if (defaultAssociation == null) {
			defaultAssociation = UMLFactory.eINSTANCE.createAssociation();
			defaultAssociation.setName("DefaultGeneratedAssociation");
			Property end1 = defaultAssociation.createOwnedEnd("x", null);
			end1.setLower(0);
			end1.setUpper(-1);
			end1.setIsOrdered(true);
			end1.setIsUnique(true);
			Property end2 = defaultAssociation.createOwnedEnd("y", null);
			end2.setLower(0);
			end2.setUpper(-1);
			end2.setIsOrdered(true);
			end2.setIsUnique(true);
		}
		return defaultAssociation;
	}

	public Object_ instantiateInterface(Interface interface_, Locus locus) {
		Class realizingClass = this.getRealizingClass(interface_);
		Object_ object = locus.instantiate(realizingClass);
		return object;
	}

	public List<Class> generatedRealizingClasses = new ArrayList<Class>();

	public Class getRealizingClass(Interface interface_) {
		Class realizingClass = null;
		// TODO For cached RealizingClasses, search based on InterfaceRealizations rather than name
		String realizingClassName = interface_.getQualifiedName() + "GeneratedRealizingClass";
		int i = 1;
		while (i <= generatedRealizingClasses.size() && realizingClass == null) {
			Class cddRealizingClass = generatedRealizingClasses.get(i - 1);
			if (cddRealizingClass.getName().equals(realizingClassName)) {
				realizingClass = cddRealizingClass;
			}
			i = i + 1;
		}
		if (realizingClass == null) {
			realizingClass = this.generateRealizingClass(interface_, realizingClassName);
			generatedRealizingClasses.add(realizingClass);
		}
		return realizingClass;
	}

	public Class generateRealizingClass(Interface interface_, String className) {
		Class realizingClass = UMLFactory.eINSTANCE.createClass();
		realizingClass.setName(className);
		realizingClass.createInterfaceRealization("", interface_);
		// TODO Deal with structural features of the interface
		// TODO Make a test case for reading/writing structural features of an interface
		return realizingClass;
	}

}
