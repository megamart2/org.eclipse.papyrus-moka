/*****************************************************************************
 * 
 * Copyright (c) 2017 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.moka.externalcontrol.controller;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public interface IControlledVisitorPushPullStrategy {

	public IExternallyControlledVisitor<? extends ISemanticVisitor> pullEnabledVisitor();
	public void pushVisitor(IExternallyControlledVisitor<? extends ISemanticVisitor> visitor);
	
	public boolean hasEnabledVisitors();
	
}
