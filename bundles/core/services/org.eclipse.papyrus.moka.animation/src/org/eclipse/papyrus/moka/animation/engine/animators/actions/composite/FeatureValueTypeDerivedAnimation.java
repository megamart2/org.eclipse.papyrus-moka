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

package org.eclipse.papyrus.moka.animation.engine.animators.actions.composite;

import org.eclipse.papyrus.moka.animation.engine.animators.actions.DerivedAnimationAction;
import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationKind;
import org.eclipse.papyrus.moka.animation.engine.rendering.IRenderingEngine;
import org.eclipse.papyrus.moka.fuml.Profiling.Semantics.Kernel.Classes.IFeatureValueWrapper;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Class;

public class FeatureValueTypeDerivedAnimation extends DerivedAnimationAction{

	public void postVisitAction(final IRenderingEngine engine, ISemanticVisitor visitor) {
		// Enable the animation of the type of the new value
		org.eclipse.papyrus.moka.fuml.Profiling.Semantics.Kernel.Classes.IFeatureValueWrapper featureValue = (IFeatureValueWrapper) visitor;
		Type type = featureValue.getFeature().getType();
		if(type instanceof Class) {
			engine.renderAs(type, featureValue.getContext(), AnimationKind.VISITED);
		}
	}
	
	@Override
	public boolean accept(ISemanticVisitor visitor) {
		// If the visitor is for a FeatureValue with new values 
		if(visitor instanceof IFeatureValueWrapper) {
			return true;
		}
		return false;
	}

}
