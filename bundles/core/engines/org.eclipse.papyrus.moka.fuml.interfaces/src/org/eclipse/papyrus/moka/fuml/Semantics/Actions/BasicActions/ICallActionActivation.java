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

package org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;

public interface ICallActionActivation extends IInvocationActionActivation {

	public IExecution getCallExecution();

	public void removeCallExecution(IExecution execution);

}
