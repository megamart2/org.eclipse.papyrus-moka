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

package org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public class CentralBufferNodeActivation extends ObjectNodeActivation{

	@Override
	public void fire(List<IToken> incomingTokens) {
		// Add all incoming tokens to the central buffer node.
		// Offer any tokens that have not yet been offered.
		this.addTokens(incomingTokens);
		this.sendUnofferedTokens();
	}

}
