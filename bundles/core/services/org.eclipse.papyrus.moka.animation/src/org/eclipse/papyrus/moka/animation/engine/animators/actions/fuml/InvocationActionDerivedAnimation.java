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
import org.eclipse.uml2.uml.InvocationAction;

public final class InvocationActionDerivedAnimation extends DerivedAnimationAction {

	@Override
	public void postVisitAction(final IRenderingEngine engine, final ISemanticVisitor visitor) {
		// After the InvocationAction was visited then animate the
		// referenced port
		InvocationAction action = (InvocationAction) ((IActionActivation) visitor).getNode();
		engine.renderAs(action.getOnPort(), ((IActionActivation) visitor).getExecutionContext(),
				AnimationKind.ANIMATED, AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
	}

	@Override
	public boolean accept(final ISemanticVisitor visitor) {
		// If the source model element is an InvocationAction and its
		// 'onPort' property is set the return true otherwise return
		// false
		if (visitor instanceof IActionActivation && ((IActionActivation) visitor).getNode() instanceof InvocationAction
				&& ((InvocationAction) ((IActionActivation) visitor).getNode()).getOnPort() != null) {
			return true;
		}
		return false;
	}

}
