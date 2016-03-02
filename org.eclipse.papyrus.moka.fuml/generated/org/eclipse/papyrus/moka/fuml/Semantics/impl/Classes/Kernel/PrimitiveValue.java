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
package org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IPrimitiveValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;

public abstract class PrimitiveValue extends Value implements IPrimitiveValue{

	public PrimitiveType type;

	@Override
	public IValue copy() {
		// Create a new value that is equal to this primitive value.
		PrimitiveValue newValue = (PrimitiveValue) (super.copy());
		newValue.type = this.type;
		return newValue;
	}

	@Override
	public List<Classifier> getTypes() {
		// Return the single primitive type of this value.
		List<Classifier> types = new ArrayList<Classifier>();
		types.add(this.type);
		return types;
	}
	
	public void setType(PrimitiveType type){
		this.type = type;
	}
}
