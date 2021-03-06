/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.moka.debug.engine;

import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.papyrus.moka.debug.model.data.presentations.MokaDebugModelPresentation;

public abstract class MokaDebugElement extends DebugElement {

	protected MokaDebugTarget debugTarget;

	public MokaDebugElement(MokaDebugTarget debugTarget) {
		super(debugTarget);
		this.debugTarget = debugTarget;
	}

	@Override
	public String getModelIdentifier() {
		return MokaDebugModelPresentation.ID;
	}
}
