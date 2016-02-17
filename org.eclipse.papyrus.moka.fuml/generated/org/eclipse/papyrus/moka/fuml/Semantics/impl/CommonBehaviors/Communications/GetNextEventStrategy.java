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
 *   Jeremie Tatibouet (CEA) - Apply Fix fUML12-35 Initial execution of an activity is not run to completion
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.SemanticStrategy;

public abstract class GetNextEventStrategy extends SemanticStrategy {

	@Override
	public String getName() {
		// Get next event strategies are always named "getNextEvent".
		return "getNextEvent";
	}

	public abstract EventOccurrence getNextEvent(ObjectActivation objectActivation); // fUML12-35 Initial execution of an activity is not run to completion
}
