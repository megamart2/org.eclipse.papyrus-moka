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

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;

public interface IExtensionalValue extends ICompoundValue {

	public void destroy();
	
	public void setLocus(ILocus locus);
	
	public ILocus getLocus();
	
	public void setIdentifier(String identifier);
	
	public String getIdentifier();
}