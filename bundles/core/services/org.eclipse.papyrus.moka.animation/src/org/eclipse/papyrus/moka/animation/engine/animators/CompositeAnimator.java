/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.animation.engine.animators;

import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationKind;
import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.interfaces.extensions.Semantics.CompositeStructures.StructuredClasses.ICS_ConnectorLink;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.fuml.profiling.Semantics.Kernel.Classes.IFeatureValueWrapper;
import org.eclipse.papyrus.moka.utils.constants.MokaConstants;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Class;

public class CompositeAnimator extends Animator {

	@Override
	public void nodeVisited(ISemanticVisitor nodeVisitor) {
		if (nodeVisitor instanceof ICS_InteractionPoint) {
			// If the visited element is the runtime manifestation of a port then it is
			// animated and then after the specified delay marked as visited.
			this.renderAs(((ICS_InteractionPoint) nodeVisitor).getDefiningPort(),
					((ICS_InteractionPoint) nodeVisitor).getOwner().getReferent(), AnimationKind.ANIMATED,
					AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
		} else if (nodeVisitor instanceof ICS_ConnectorLink) {
			// If the visited element is the runtime manifestation of a connector then it
			// animated and then after the specified delay marked as visited.
			this.renderAs(((ICS_ConnectorLink) nodeVisitor).getConnector(), null, AnimationKind.ANIMATED,
					AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
		} else if(nodeVisitor instanceof IFeatureValueWrapper) {
			// If the visited element is a structural feature newly associated to values then
			// two different animation behavior can occur:
			// 1] The structural feature has a type that is an active class then the feature
			//    is marked as being animated (i.e., it is currently executed).
			// 2] The structural feature is not typed by an active class then the feature is
			//    marked as being visited (i.e., it is not currently executing)
			IFeatureValueWrapper featureValue = (IFeatureValueWrapper) nodeVisitor;
			Type type = featureValue.getFeature().getType();
			if(type instanceof Class && ((Class)type).isActive()) {
				this.renderAs(featureValue.getFeature(), featureValue.getContext(), AnimationKind.ANIMATED);
			} else {
				this.renderAs(featureValue.getFeature(), featureValue.getContext(), AnimationKind.VISITED);
			}
		} else if (nodeVisitor instanceof IActivityNodeActivation
				&& ((IActivityNodeActivation) nodeVisitor).getNode() instanceof SendSignalAction) {
			// If the visited element is a SendSignalAction with the property 'onPort' specified
			// then it is animated and after the certain delay marked as visited.
			SendSignalAction sendSignalAction = (SendSignalAction) ((IActivityNodeActivation) nodeVisitor).getNode();
			this.renderAs(sendSignalAction, ((IActivityNodeActivation) nodeVisitor).getExecutionContext(),
					AnimationKind.ANIMATED, AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
			this.renderAs(sendSignalAction.getOnPort(), ((IActivityNodeActivation) nodeVisitor).getExecutionContext(),
					AnimationKind.ANIMATED, AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
		}
	}

	@Override
	public void nodeLeft(ISemanticVisitor nodeVisitor) {
		// Do nothing
	}

	@Override
	public boolean accept(ISemanticVisitor visitor) {
		// The composite animator enables the following nodes to be animated:
		// any feature value with newly associated value, connector and send signal
		// action with the 'onPort' property specified.
		if (visitor instanceof ICS_InteractionPoint) {
			return true;
		} else if (visitor instanceof ICS_ConnectorLink) {
			return true;
		} else if (visitor instanceof IFeatureValue) {
			return true;
		} else if (visitor instanceof IActivityNodeActivation
				&& ((IActivityNodeActivation) visitor).getNode() instanceof SendSignalAction
				&& ((SendSignalAction) ((IActivityNodeActivation) visitor).getNode()).getOnPort() != null) {
			return true;
		}
		return false;
	}

}
