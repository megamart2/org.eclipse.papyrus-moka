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

import org.eclipse.papyrus.moka.fmi.fmiprofile.ScalarVariable;
import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
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
	private boolean hasChanged;

	public Fmi2ScalarVariable(Fmu2ProxyService fmu, Property p, Stereotype st) {
		// TODO Auto-generated constructor stub
		
		ScalarVariable variable = (ScalarVariable) p.getStereotypeApplication(st);
		org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2ScalarVariable modelDescVariable = variable.getFmiVariable();
		
		this.fmu = fmu;
		this.name = p.getName();
		this.valueReference = modelDescVariable.getValueReference();
		this.variability = modelDescVariable.getVariability().getName();

		this.causality = modelDescVariable.getCausality().toString();
		System.out.println("P: " + p.getName());

		this.initial = modelDescVariable.getInitial().getName();
		this.setType(p.getType().getName());
		if (p.getDefaultValue() != null){
			switch (type){
			case Fmi2VariableType.fmi2Boolean :
				this.setValue(p.getDefaultValue().booleanValue());
				break;
			case Fmi2VariableType.fmi2Integer :
				this.setValue(p.getDefaultValue().integerValue());
				break;
			case Fmi2VariableType.fmi2Real :
				this.setValue(p.getDefaultValue().realValue());
				break;
			case Fmi2VariableType.fmi2String :
				this.setValue(p.getDefaultValue().stringValue());
				break;
			}
		}else {
			//TODO: should better handle default values?
			switch (type){
			case Fmi2VariableType.fmi2Boolean :
				this.setValue(false);
				break;
			case Fmi2VariableType.fmi2Integer :
				this.setValue(0);
				break;
			case Fmi2VariableType.fmi2Real :
				this.setValue(0.0);
				break;
			case Fmi2VariableType.fmi2String :
				this.setValue("MOKA_DEFAULT");
				break;
			}
		}
		
			
			
		
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

	public void setHasChanged(boolean hasChanged) {
		this.hasChanged =hasChanged; 
		
	}
	
	public boolean hasChanged(){
		return hasChanged;
	}



}
