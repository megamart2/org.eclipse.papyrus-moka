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

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticStrategy;
import org.eclipse.uml2.uml.Class;

public abstract class CreateObjectActionStrategy extends SemanticStrategy {

	public abstract Object_ instantiate(Class type, Locus locus);

	@Override
	public String getName() {
		return "CreateObjectActionStrategy";
	}
}
