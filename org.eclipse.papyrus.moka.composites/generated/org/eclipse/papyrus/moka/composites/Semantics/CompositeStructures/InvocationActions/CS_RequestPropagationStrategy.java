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
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticVisitor;


public abstract class CS_RequestPropagationStrategy extends SemanticStrategy {

	@Override
	public String getName() {
		// a CS_RequestPropagationStrategy are always named "requestPropagation"
		return "requestPropagation";
	}

	public abstract List<Reference> select(List<Reference> potentialTargets, SemanticVisitor context);
}
