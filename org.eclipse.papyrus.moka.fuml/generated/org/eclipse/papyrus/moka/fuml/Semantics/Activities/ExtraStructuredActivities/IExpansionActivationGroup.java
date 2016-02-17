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

package org.eclipse.papyrus.moka.fuml.Semantics.Activities.ExtraStructuredActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivationGroup;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.BasicActions.OutputPinActivation;

public interface IExpansionActivationGroup extends IActivityNodeActivationGroup{

	public List<OutputPinActivation> getRegionInputs();
	
	public List<OutputPinActivation> getGroupInputs();
	
	public List<OutputPinActivation> getGroupOutputs();
	
	public void setRegionActivation(IExpansionRegionActivation regionActivation);
	
	public IExpansionRegionActivation getRegionActivation();
	
	public void setIndex(Integer index);
	
	public Integer getIndex();
}