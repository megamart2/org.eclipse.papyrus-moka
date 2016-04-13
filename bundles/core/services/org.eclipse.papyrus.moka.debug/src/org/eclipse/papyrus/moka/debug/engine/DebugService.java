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
package org.eclipse.papyrus.moka.debug.engine;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.service.AbstractMokaService;

public class DebugService extends AbstractMokaService {

	// Debug target attached to the debug service
	protected IMokaDebugTarget debugTarget;

	@Override
	public void init(ILaunch launcher, EObject modelElement) {
		this.debugTarget = (IMokaDebugTarget) launcher.getDebugTarget();
	}

	@Override
	public void nodeVisited(ISemanticVisitor nodeVisitor) {
		if (!this.debugTarget.isDisconnected()) {
			if (nodeVisitor instanceof IActivityNodeActivation
					|| nodeVisitor instanceof IActivityEdgeInstance) {
				IObject_ object = null;
				if (nodeVisitor instanceof IActivityNodeActivation) {
					if (((IActivityNodeActivation) nodeVisitor).getGroup() != null) {
						object = ((IActivityNodeActivation) nodeVisitor).getExecutionContext();
					}
				} else {
					if (((IActivityEdgeInstance) nodeVisitor).getSource().getGroup() != null) {
						object = ((IActivityEdgeInstance) nodeVisitor).getSource().getExecutionContext();
					}
				}
				if (object != null) {
					this.debugTarget.update(object, nodeVisitor);
					if (this.debugTarget.isSuspensionRequired(object, nodeVisitor)) {
						this.debugTarget.suspend(object, nodeVisitor);
					}
				}
			}
		}
	}

	@Override
	public void nodeLeft(ISemanticVisitor nodeVisitor) {
		// Do nothing
	}

	@Override
	public void valueCreated(IValue value) {
		// When a new fUML value is instantiated within the fUML execution context
		// this value is registered by the debug target as being a logical thread under
		// the following conditions
		// 1 - The value is instance of a Class
		// 2 - The instantiated class is specified as being active
		// 3 - There is no thread already registered for this object within the debug target
		if (!this.debugTarget.isDisconnected()) {
			if (value instanceof IObject_) {
				IObject_ object = (IObject_) value;
				if (DebugServiceHelper.INSTANCE.isActive(object)
						&& this.debugTarget.isNewThread(object)) {
					this.debugTarget.registerThread(object);
				}
			}
		}
	}

	@Override
	public void valueDestroyed(IValue value) {
		// When a fUML value is destroyed within the fUML context, a thread known
		// by the debug target might be also be terminated. Such situation occurs
		// under the following conditions:
		// 1 - The destroyed value is an instance of for an active class
		// 2 - This instance matches a thread that is known by the debug target
		if (!this.debugTarget.isDisconnected()) {
			if (value instanceof IObject_) {
				IObject_ object = (IObject_) value;
				if (!this.debugTarget.isNewThread(object)) {
					this.debugTarget.unregisterThread(object);
				}
			}
		}
	}
}