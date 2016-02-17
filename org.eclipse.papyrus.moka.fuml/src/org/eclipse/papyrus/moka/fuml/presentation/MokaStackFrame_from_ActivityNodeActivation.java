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

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;

public class MokaStackFrame_from_ActivityNodeActivation extends MokaStackFrame_from_UMLModelElement {

	protected IActivityNodeActivation activityNodeActivation;

	public MokaStackFrame_from_ActivityNodeActivation(IActivityNodeActivation activityNodeActivation) {
		super(activityNodeActivation.getNode());
		this.activityNodeActivation = activityNodeActivation;
	}

	// /////////////////////////////
	// Presentation
	// /////////////////////////////

	@Override
	public String getLabel() {
		String activityName = activityNodeActivation.getActivityExecution().getBehavior().getName();
		String nodeName = activityNodeActivation.getNode().getName();
		if (nodeName == null || nodeName.length() == 0) {
			nodeName = "Anonymous node";
		}
		return activityName + "(" + nodeName + ")";
	}

	// /////////////////////////////
	// Debug
	// /////////////////////////////

	@Override
	protected IObject_ getContextObject() {
		return activityNodeActivation.getExecutionContext();
	}
}
