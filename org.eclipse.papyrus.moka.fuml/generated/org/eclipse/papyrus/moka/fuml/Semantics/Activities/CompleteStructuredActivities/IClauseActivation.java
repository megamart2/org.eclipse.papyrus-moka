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

package org.eclipse.papyrus.moka.fuml.Semantics.Activities.CompleteStructuredActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.CompleteStructuredActivities.ClauseActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;

public interface IClauseActivation {
	
	public void receiveControl();
	
	public Boolean isReady();
	
	public void runTest();
	
	public void selectBody();
	
	public BooleanValue getDecision();
	
	public List<ClauseActivation> getPredecessors();
	
	public List<ClauseActivation> getSuccessors();
}
