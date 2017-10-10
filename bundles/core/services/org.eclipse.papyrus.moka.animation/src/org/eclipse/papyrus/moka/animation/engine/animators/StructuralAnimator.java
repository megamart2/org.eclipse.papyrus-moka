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

import java.util.Iterator;
import java.util.List;

import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationKind;
import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_InteractionPoint;
import org.eclipse.papyrus.moka.composites.interfaces.extensions.Semantics.CompositeStructures.StructuredClasses.ICS_ConnectorLink;
import org.eclipse.papyrus.moka.fuml.Profiling.Semantics.Kernel.Classes.IFeatureValueWrapper;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.utils.constants.MokaConstants;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;

public class StructuralAnimator extends Animator {

	@Override
	public void nodeVisited_(ISemanticVisitor nodeVisitor) {
		if (nodeVisitor instanceof ICS_InteractionPoint) {
			// If the visited element is the runtime manifestation of a port then it is
			// animated and then after the specified delay marked as visited.
			this.engine.renderAs(((ICS_InteractionPoint) nodeVisitor).getDefiningPort(),
					((ICS_InteractionPoint) nodeVisitor).getOwner().getReferent(), AnimationKind.ANIMATED,
					AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
		} else if (nodeVisitor instanceof ICS_ConnectorLink) {
			// If the visited element is the runtime manifestation of a connector then it
			// animated and then after the specified delay marked as visited.
			this.engine.renderAs(((ICS_ConnectorLink) nodeVisitor).getConnector(), null, AnimationKind.ANIMATED,
					AnimationKind.VISITED, MokaConstants.MOKA_ANIMATION_DELAY);
		} else if (nodeVisitor instanceof IFeatureValueWrapper) {
			// If the visited element is a structural feature newly associated to values
			// then two different animation behavior can occur:
			// 1] The structural feature has a type that is an active class then the feature
			// is marked as being animated (i.e., it is currently executed).
			// 2] The structural feature is not typed by an active class then the feature is
			// marked as being visited (i.e., it is not currently executing)
			IFeatureValueWrapper featureValue = (IFeatureValueWrapper) nodeVisitor;
			Type type = featureValue.getFeature().getType();
			if (type instanceof Class && ((Class) type).isActive()) {
				this.engine.renderAs(featureValue.getFeature(), featureValue.getContext(), AnimationKind.ANIMATED);
			} else {
				this.engine.renderAs(featureValue.getFeature(), featureValue.getContext(), AnimationKind.VISITED);
			}
		}
	}

	@Override
	public void nodeLeft_(ISemanticVisitor nodeVisitor) {
		// Do nothing
	}

	@Override
	public void valueCreated(IValue value) {
		// Whenever an object is created, the classes typing this object are
		// animated. If a class is active then it remains in the ANIMATED state
		// while if it is passive it remains in the VISITED state
		IObject_ object = (IObject_) value;
		for (Classifier classifier : object.getTypes()) {
			Class clazz = (Class) classifier;
			if (clazz.isActive()) {
				this.engine.renderAs(clazz, object, AnimationKind.ANIMATED);
			} else {
				this.engine.renderAs(clazz, object, AnimationKind.VISITED);
			}
		}
	}

	@Override
	public void valueDestroyed(IValue value) {
		// Whenever an object is destroyed, if there are no other objects
		// in the locus that are classified under the same types are displayed
		// according to their original style
		IObject_ object = (IObject_) value;
		Iterator<Classifier> classifierIterator = object.getTypes().iterator();
		while (classifierIterator.hasNext()) {
			Classifier classifier = classifierIterator.next();
			List<IExtensionalValue> extensionalValues = object.getLocus().getExtent(classifierIterator.next());
			if(!extensionalValues.isEmpty()) {
				this.engine.removeRenderingRules(classifier);
			}
		}
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
		} else if (visitor instanceof IObject_) {
			return true;
		}
		return false;
	}

}
