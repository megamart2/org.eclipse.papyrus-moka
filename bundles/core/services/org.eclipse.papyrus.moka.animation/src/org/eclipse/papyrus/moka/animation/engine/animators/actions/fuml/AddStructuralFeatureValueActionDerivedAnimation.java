/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.moka.animation.engine.animators.actions.fuml;

import org.eclipse.papyrus.moka.animation.engine.animators.actions.DerivedAnimationAction;
import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationKind;
import org.eclipse.papyrus.moka.animation.engine.rendering.IRenderingEngine;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.IActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.utils.constants.MokaConstants;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;

public class AddStructuralFeatureValueActionDerivedAnimation extends DerivedAnimationAction {

	@Override
	public void postVisitAction(IRenderingEngine engine, ISemanticVisitor visitor) {
		// Enable the animation of the represented feature
		AddStructuralFeatureValueAction action = (AddStructuralFeatureValueAction) ((IActionActivation) visitor)
				.getNode();
		engine.renderAs(action.getStructuralFeature(), ((IActionActivation) visitor).getExecutionContext(),
				AnimationKind.ANIMATED, AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
	}

	@Override
	public boolean accept(ISemanticVisitor visitor) {
		// If the visitor is for an AddStructuralfeatureValueAction
		if (visitor instanceof IActionActivation
				&& ((IActionActivation) visitor).getNode() instanceof AddStructuralFeatureValueAction) {
			return true;
		}
		return false;
	}

}
