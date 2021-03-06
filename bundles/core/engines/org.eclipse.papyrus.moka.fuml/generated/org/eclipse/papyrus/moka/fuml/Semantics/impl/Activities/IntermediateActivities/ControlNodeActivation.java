/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IControlNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public abstract class ControlNodeActivation extends ActivityNodeActivation implements IControlNodeActivation {

	@Override
	public void fire(List<IToken> incomingTokens) {
		// By default, offer all tokens on all outgoing edges.
		if (this.node != null) {
			Debug.println("[fire] Control node " + this.node.getName() + "...");
		}
		this.sendOffers(incomingTokens);
	}
}
