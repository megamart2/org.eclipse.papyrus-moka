/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.presentation;

import org.eclipse.papyrus.moka.debug.MokaVariable;
import org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine;
import org.eclipse.papyrus.moka.ui.presentation.IPresentation;
import org.eclipse.swt.graphics.Image;

public abstract class MokaVariable_for_fUML extends MokaVariable implements IPresentation {

	public MokaVariable_for_fUML() {
		super(FUMLExecutionEngine.eInstance.getDebugTarget());
	}

	public String getLabel() {
		// Not used
		return "";
	}

	public String getDetails() {
		// Not used
		return "";
	}

	public abstract Image getImage();

}
