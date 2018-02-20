/*****************************************************************************
 * 
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmu.engine.semantics;

import org.eclipse.papyrus.moka.composites.Semantics.impl.Actions.IntermediateActions.CS_AddStructuralFeatureValueActionActivation;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.StructuralFeature;

public class FMUAddStructuralFeatureValueAction extends CS_AddStructuralFeatureValueActionActivation {

	@Override
	public void doAction() {
		AddStructuralFeatureValueAction action = (AddStructuralFeatureValueAction) (this.node);
		StructuralFeature feature = action.getStructuralFeature();
		if (FMIProfileUtil.isFMUPort(feature)) {
			// Behaves like in fUML for regular properties (i.e., does not deal with interaction points)
			this.doActionDefault();
		}
		else {
			super.doAction();
		}
	}
	
}
