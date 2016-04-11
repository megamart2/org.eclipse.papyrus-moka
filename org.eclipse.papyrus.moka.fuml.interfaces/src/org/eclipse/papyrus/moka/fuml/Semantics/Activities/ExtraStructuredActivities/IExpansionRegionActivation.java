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

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.IActionActivation;
import org.eclipse.uml2.uml.ExpansionNode;

public interface IExpansionRegionActivation extends IActionActivation {

	public void doStructuredActivity();

	public void runIterative();

	public void runParallel();

	public void doOutput();

	public void runGroup(IExpansionActivationGroup activationGroup);

	public void terminateGroup(IExpansionActivationGroup activationGroup);

	public IExpansionNodeActivation getExpansionNodeActivation(ExpansionNode node);

	public Integer numberOfValues();

	public Boolean isSuspended();

	public void resume(IExpansionActivationGroup activationGroup);

	public List<IExpansionActivationGroup> getExpansionActivationGroups();

}
