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
package org.eclipse.papyrus.moka.composites.Semantics.CommonBehaviors.Communications;

// Imports
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticStrategy;
import org.eclipse.uml2.uml.StructuralFeature;

public abstract class CS_StructuralFeatureOfInterfaceAccessStrategy extends SemanticStrategy {

	@Override
	public String getName() {
		// StructuralFeatureAccessStrategy are always named "structuralFeature"
		return "structuralFeature";
	}

	public abstract FeatureValue read(CS_Object cs_Object, StructuralFeature feature);

	public abstract void write(CS_Object cs_Object, StructuralFeature feature, List<Value> values, Integer position);
}
