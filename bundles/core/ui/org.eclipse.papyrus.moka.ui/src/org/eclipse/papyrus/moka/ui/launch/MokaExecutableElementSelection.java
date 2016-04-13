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
package org.eclipse.papyrus.moka.ui.launch;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MokaExecutableElementSelection extends SelectionAdapter {

	protected transient MokaRunConfigurationTab launchConfigTab;

	public MokaExecutableElementSelection(MokaRunConfigurationTab tab) {
		this.launchConfigTab = tab;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		this.launchConfigTab.updateLaunchConfigurationDialog();
	}
}
