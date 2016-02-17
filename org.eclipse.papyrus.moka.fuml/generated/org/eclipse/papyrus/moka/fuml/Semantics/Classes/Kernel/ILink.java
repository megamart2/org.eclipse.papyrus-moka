/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.FeatureValue;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

public interface ILink extends IExtensionalValue {
	
	public Boolean isMatchingLink(IExtensionalValue link, Property end);
	
	public List<FeatureValue> getOtherFeatureValues(List<IExtensionalValue> extent, Property end);
	
	public void addTo(ILocus locus);
	
	public void setType(Association association);
	
	public Association getType();
}
