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

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IPrimitiveValue;

public class PrimitiveValueAdapter extends MokaValueAdapter<IPrimitiveValue> {

	public PrimitiveValueAdapter(MokaDebugTarget debugTarget, IPrimitiveValue value) {
		super(debugTarget, value);
	}

	@Override
	public String getValueString() throws DebugException {
		// The string is the value represented by this primitive value.
		return this.adaptedObject.toString();
	}

}
