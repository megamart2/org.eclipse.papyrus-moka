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
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.ValueSpecification;

public class StringValue extends PrimitiveValue {

	public String value;

	public Object getUnderlying() {
		return this.value;
	}

	@Override
	public ValueSpecification specify() {
		// Return a literal string with the value of this string value.
		LiteralString literal = UMLFactory.eINSTANCE.createLiteralString();
		literal.setType(this.type);
		literal.setValue(this.value);
		return literal;
	}

	@Override
	public Boolean equals(Value otherValue) {
		// Test if this string value is equal to the otherValue.
		// To be equal, the otherValue must have the same value as this string
		// value.
		boolean isEqual = false;
		if (otherValue instanceof StringValue) {
			isEqual = ((StringValue) otherValue).value.equals(this.value);
		}
		return isEqual;
	}

	@Override
	public Value copy() {
		// Create a new string value with the same value as this string value.
		StringValue newValue = (StringValue) (super.copy());
		newValue.value = this.value;
		return newValue;
	}

	@Override
	public Value new_() {
		// Create a new string value with no value.
		return new StringValue();
	}

	@Override
	public String toString() {
		return value;
	}

	public Object getValue() {
		return this.value;
	}
}
