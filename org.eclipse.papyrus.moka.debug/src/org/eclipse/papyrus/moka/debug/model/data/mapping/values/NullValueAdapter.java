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

public class NullValueAdapter extends MokaValueAdapter {

	public NullValueAdapter(MokaDebugTarget debugTarget) {
		super(debugTarget);
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return "null (or not supported)";
	}

	@Override
	public String getValueString() throws DebugException {
		return "null (or not supported)";
	}
}
