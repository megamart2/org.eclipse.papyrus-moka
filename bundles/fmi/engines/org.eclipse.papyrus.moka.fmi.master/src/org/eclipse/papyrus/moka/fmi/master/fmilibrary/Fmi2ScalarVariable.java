/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

public class Fmi2ScalarVariable {
	private Fmu2ProxyService fmu;
	private String name;
	private long valueReference;
	private String variability;
	private String causality;
	private String initial;
	private String type;
	private Object value;

	public Fmi2ScalarVariable(Fmu2ProxyService fmu, Property p, Stereotype st) {
		// TODO Auto-generated constructor stub
		this.fmu = fmu;
		this.name = p.getName();
		this.valueReference = (Long) p.getValue(st, "valueReference");
		this.variability = ((EnumerationLiteral) p.getValue(st, "variability")).getName();
		this.causality = p.getValue(st, "causality").toString();
		System.out.println("P: " + p.getName());
		EnumerationLiteral literalValue = ((EnumerationLiteral) p.getValue(st, "initial"));
		if (literalValue == null) {
			EObject stApp = p.getStereotypeApplication(st);
			System.out.println("Stooooooooooooooooooop!");
		}
		this.initial = literalValue.getName();
		this.setType(p.getType().getName());
		this.setValue(p.getDefaultValue());
	}

	public Fmu2ProxyService getFmu() {
		return fmu;
	}

	public void setFmu(Fmu2ProxyService fmu) {
		this.fmu = fmu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValueReference() {
		return valueReference;
	}

	public void setValueReference(long valueReference) {
		this.valueReference = valueReference;
	}

	public String getVariability() {
		return variability;
	}

	public void setVariability(String variability) {
		this.variability = variability;
	}

	public String getCausality() {
		return causality;
	}

	public void setCausality(String causality) {
		this.causality = causality;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
