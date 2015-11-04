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
package org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticStrategy;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.StructuralFeature;

public abstract class GetAssociationStrategy extends SemanticStrategy {

	public abstract Association getAssociation(StructuralFeature feature);

	@Override
	public String getName() {
		return "GetAssociationStrategy";
	}
}
