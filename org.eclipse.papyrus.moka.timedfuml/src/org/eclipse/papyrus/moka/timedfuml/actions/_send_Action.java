/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.moka.timedfuml.actions;

import org.eclipse.papyrus.moka.discreteevent.actions.Action;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IObjectActivation;

/**
 * @author ac221913
 *
 */
public class _send_Action extends Action {

	protected IObjectActivation objectActivation ;
	
	public _send_Action(IObjectActivation objectActivation) {
		super();
		this.objectActivation = objectActivation;
	}

	/**
	 * @see org.eclipse.papyrus.moka.discreteevent.actions.Action#execute()
	 *
	 */
	@Override
	public void execute() {
		if (! objectActivation.getWaitingEventAccepters().isEmpty()) {
			objectActivation._startObjectBehavior();
		}
	}

}
