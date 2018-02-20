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

package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;

public class ObjectTokenVariableValueAdapter extends MokaVariableAdapter<IValue> {

	private final String NAME = "value";
	
	public ObjectTokenVariableValueAdapter(MokaDebugTarget debugTarget, IValue heldValue) {
		super(debugTarget, heldValue);
	}

	@Override
	public String getName() throws DebugException {
		// Return the variable name
		return NAME;
	}

}
