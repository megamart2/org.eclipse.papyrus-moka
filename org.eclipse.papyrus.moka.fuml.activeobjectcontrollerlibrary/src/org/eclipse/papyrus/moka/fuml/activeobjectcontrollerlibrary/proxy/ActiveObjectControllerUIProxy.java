/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.proxy;

import org.eclipse.papyrus.moka.async.fuml.debug.TerminateSignalInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.SignalInstance;
import org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.proxy.executions.RegisterExecution;
import org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.ui.ActiveObjectControllerUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;

public class ActiveObjectControllerUIProxy extends Object_ {

	protected ActiveObjectControllerUI ui;

	public ActiveObjectControllerUIProxy(Class service) {
		super();
		this.types.add(service);

	}

	/**
	 * When a CallOperationAction is called, create a new RegisterExecution
	 *
	 */
	@Override
	public Execution dispatch(Operation operation) {
		if (operation.getName().equals("register")) {
			return new RegisterExecution(operation, this);
		}
		return null;
	}

	/**
	 * When a TerminateSignalInstance is send, call ActiveObjectControllerUI.terminate() to clear and disable the UI
	 *
	 */
	@Override
	public void send(SignalInstance signalInstance) {
		if (signalInstance instanceof TerminateSignalInstance) {
			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					ActiveObjectControllerUI.terminate();
				}
			});
		}
		super.send(signalInstance);
	}

}
