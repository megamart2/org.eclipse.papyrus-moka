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
package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.uml2.uml.NamedElement;

public class ActivityNodeActivationValueAdapter extends ActivityVisitorValueAdapter{

	public ActivityNodeActivationValueAdapter(MokaDebugTarget debugTarget, IActivityNodeActivation visitor) {
		super(debugTarget, visitor);
	}
	@Override
	protected NamedElement getNode() {
		return ((IActivityNodeActivation)this.adaptedObject).getNode();
	}
}
