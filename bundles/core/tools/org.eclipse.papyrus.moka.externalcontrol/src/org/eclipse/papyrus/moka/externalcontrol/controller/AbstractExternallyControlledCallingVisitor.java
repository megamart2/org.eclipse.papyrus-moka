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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.externalcontrol.advice.IControllerAdvice;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public abstract class AbstractExternallyControlledCallingVisitor<T extends ISemanticVisitor> extends AbstractExternallyControlledVisitor<T > {

	
	protected List<ISemanticVisitor> suspendedVisitors = new ArrayList<ISemanticVisitor>();
	
	public AbstractExternallyControlledCallingVisitor(T visitor, ExternalController controller, List<IControllerAdvice> advices) {
		super(visitor, controller, advices);
	}
	
	

}
