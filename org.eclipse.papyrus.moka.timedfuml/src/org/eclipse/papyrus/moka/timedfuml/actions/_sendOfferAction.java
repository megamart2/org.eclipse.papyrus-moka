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
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_OpaqueActionActivation;

/**
 * @author ac221913
 *
 */
public class _sendOfferAction extends Action {

	protected Timed_OpaqueActionActivation actionActivation;

	public _sendOfferAction(Timed_OpaqueActionActivation actionActivation) {
		super();
		this.actionActivation = actionActivation;
	}

	/**
	 * @see org.eclipse.papyrus.moka.discreteevent.actions.Action#execute()
	 *
	 */
	@Override
	public void execute() {
		// System.out.println(DEScheduler.getInstance().getCurrentTime() + " : sending offer - " + this.actionActivation);
		this.actionActivation.sendOffersDefault();
	}

}
