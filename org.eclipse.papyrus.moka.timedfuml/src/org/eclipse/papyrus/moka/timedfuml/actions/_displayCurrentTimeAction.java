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

import java.io.IOException;

import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.discreteevent.actions.Action;
import org.eclipse.papyrus.moka.fuml.standardlibrary.library.io.StandardOutputChannelImpl;
import org.eclipse.ui.console.IOConsoleOutputStream;

/**
 * @author ac221913
 *
 */
public class _displayCurrentTimeAction extends Action {

	protected int step = 0 ;

	protected IOConsoleOutputStream out = null;
	
	/**
	 * @see org.eclipse.papyrus.moka.discreteevent.actions.Action#execute()
	 *
	 */
	@Override
	public void execute() {
		if (out == null) {
			this.out = StandardOutputChannelImpl.getConsole().newOutputStream() ;
		}
		String message = "Step: " + this.step + ", Current time: " + DEScheduler.getInstance().getCurrentTime() + "\n";
		try {
			out.write(message);
		} catch (IOException e) {
			
		}
		this.step ++ ;
	}

}
