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
package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugElement;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;

public abstract class MokaValueAdapter extends MokaDebugElement implements IValue {

	protected List<IVariable> variables;

	public MokaValueAdapter(MokaDebugTarget debugTarget) {
		super(debugTarget);
		this.variables = new ArrayList<IVariable>();
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return this.getVariables().length > 0;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return this.variables.toArray(new IVariable[0]);
	}
}
