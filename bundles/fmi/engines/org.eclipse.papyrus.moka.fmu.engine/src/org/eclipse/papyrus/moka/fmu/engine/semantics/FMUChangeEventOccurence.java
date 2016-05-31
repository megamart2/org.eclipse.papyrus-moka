package org.eclipse.papyrus.moka.fmu.engine.semantics;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.EventOccurrence;
import org.eclipse.uml2.uml.Property;

public class FMUChangeEventOccurence extends EventOccurrence {
	
	protected Property changedProperty ;
	protected IValue formerValue ;
	protected IValue newValue ;
	
	public FMUChangeEventOccurence(Property changedProperty, IValue formerValue, IValue newValue) {
		super();
		this.changedProperty = changedProperty;
		this.formerValue = formerValue;
		this.newValue = newValue;
	}

	public Property getChangedProperty() {
		return changedProperty;
	}

	public void setChangedProperty(Property changedProperty) {
		this.changedProperty = changedProperty;
	}

	public IValue getFormerValue() {
		return formerValue;
	}

	public void setFormerValue(Value formerValue) {
		this.formerValue = formerValue;
	}

	public IValue getNewValue() {
		return newValue;
	}

	public void setNewValue(Value newValue) {
		this.newValue = newValue;
	}
	
	
}
