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

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.ActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;

public class MokaStackFrame_from_ActivityEdgeInstance extends MokaStackFrame_from_UMLModelElement {

	protected ActivityEdgeInstance activityEdgeInstance;

	public MokaStackFrame_from_ActivityEdgeInstance(ActivityEdgeInstance activityEdgeInstance) {
		super(activityEdgeInstance.edge);
		this.activityEdgeInstance = activityEdgeInstance;
	}

	// /////////////////////////////
	// Presentation
	// /////////////////////////////

	@Override
	public String getLabel() {
		String activityName = activityEdgeInstance.edge.getActivity().getName();
		String nodeName = activityEdgeInstance.edge.getName();
		if (nodeName == null || nodeName.length() == 0) {
			nodeName = "Anonymous edge";
		}
		return activityName + "(" + nodeName + ")";
	}

	// /////////////////////////////
	// Debug
	// /////////////////////////////

	@Override
	protected Object_ getContextObject() {
		return activityEdgeInstance.group.getActivityExecution().context;
	}



}
