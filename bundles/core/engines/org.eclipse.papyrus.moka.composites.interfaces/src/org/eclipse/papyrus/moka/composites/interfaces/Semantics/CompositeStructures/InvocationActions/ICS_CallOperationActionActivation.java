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

package org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.InvocationActions;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.ICallActionActivation;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;

public interface ICS_CallOperationActionActivation extends ICallActionActivation {

	public boolean isOperationProvided(Port port, Operation operation);

	public boolean isOperationRequired(Port port, Operation operation);

	public boolean isCreate(Operation o);

}
