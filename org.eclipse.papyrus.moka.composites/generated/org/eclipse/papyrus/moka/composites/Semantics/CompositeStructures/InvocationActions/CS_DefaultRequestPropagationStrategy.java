/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.InvocationActions;

// Imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.SendSignalActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticVisitor;


public class CS_DefaultRequestPropagationStrategy extends CS_RequestPropagationStrategy {

	@Override
	public List<Reference> select(List<Reference> potentialTargets, SemanticVisitor context) {
		// returns all potential targets in the case where the context is a SendSignalActionActivation
		// returns the first potential target in the case where the context is anything else
		List<Reference> selectedTargets = new ArrayList<Reference>();
		if (context instanceof SendSignalActionActivation) {
			for (int i = 0; i < potentialTargets.size(); i++) {
				selectedTargets.add(potentialTargets.get(i));
			}
		} else {
			if (potentialTargets.size() >= 1) {
				selectedTargets.add(potentialTargets.get(0));
			}
		}
		return selectedTargets;
	}
}
