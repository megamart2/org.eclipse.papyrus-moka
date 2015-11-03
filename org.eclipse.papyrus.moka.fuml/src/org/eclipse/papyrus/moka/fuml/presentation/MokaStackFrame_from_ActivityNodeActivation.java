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

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.ActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;

public class MokaStackFrame_from_ActivityNodeActivation extends MokaStackFrame_from_UMLModelElement {

	protected ActivityNodeActivation activityNodeActivation;

	public MokaStackFrame_from_ActivityNodeActivation(ActivityNodeActivation activityNodeActivation) {
		super(activityNodeActivation.node);
		this.activityNodeActivation = activityNodeActivation;
	}

	// /////////////////////////////
	// Presentation
	// /////////////////////////////

	@Override
	public String getLabel() {
		String activityName = activityNodeActivation.getActivityExecution().getBehavior().getName();
		String nodeName = activityNodeActivation.node.getName();
		if (nodeName == null || nodeName.length() == 0) {
			nodeName = "Anonymous node";
		}
		return activityName + "(" + nodeName + ")";
	}

	// /////////////////////////////
	// Debug
	// /////////////////////////////

	@Override
	protected Object_ getContextObject() {
		return activityNodeActivation.getExecutionContext();
	}
}
