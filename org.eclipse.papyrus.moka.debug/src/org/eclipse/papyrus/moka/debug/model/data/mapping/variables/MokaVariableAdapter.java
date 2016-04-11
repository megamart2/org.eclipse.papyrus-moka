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

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugElement;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;

public abstract class MokaVariableAdapter extends MokaDebugElement implements IVariable {

	// The value that is currently associated to this variable
	protected IValue value;

	public MokaVariableAdapter(MokaDebugTarget debugTarget) {
		super(debugTarget);
	}
}
