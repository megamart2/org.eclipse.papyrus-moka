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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public class InitialNodeActivation extends ControlNodeActivation {

	@Override
	public void fire(List<IToken> incomingTokens) {
		// Create a single token and send offers for it.
		List<IToken> tokens = new ArrayList<IToken>();
		tokens.add(new ControlToken());
		this.addTokens(tokens);
		this.sendOffers(tokens);
	}
}
