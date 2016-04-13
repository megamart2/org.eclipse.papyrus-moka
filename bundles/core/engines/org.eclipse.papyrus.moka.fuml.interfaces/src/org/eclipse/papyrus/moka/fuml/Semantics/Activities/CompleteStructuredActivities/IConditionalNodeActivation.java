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

import org.eclipse.uml2.uml.Clause;

public interface IConditionalNodeActivation extends IStructuredActivityNodeActivation {

	public void completeBody();

	public IClauseActivation getClauseActivation(Clause clause);

	public void runTest(Clause clause);

	public void selectBody(Clause clause);

}
