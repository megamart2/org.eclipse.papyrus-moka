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

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.FeatureValue;
import org.eclipse.swt.graphics.Image;

public class MokaVariable_from_FeatureValue extends MokaVariable_for_fUML {

	protected FeatureValue featureValue;

	protected IValue value;

	public MokaVariable_from_FeatureValue(FeatureValue featureValue) {
		super();
		this.featureValue = featureValue;
		this.name = featureValue.feature.getName();
	}

	// //////////////////////
	// Presentation
	// //////////////////////

	@Override
	public Image getImage() {
		return FUMLPresentationUtils.getImage(featureValue.feature);
	}

	// //////////////////////
	// Debug
	// //////////////////////

	@Override
	public IValue getValue() throws DebugException {
		if (this.value == null) {
			this.value = new MokaValue_from_FeatureValue(featureValue);
		}
		return this.value;
	}

}
