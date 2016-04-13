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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public abstract class MokaGroupComponent extends Composite {

	public Group group;

	public MokaGroupComponent(Composite parent, int style, String name, int columns) {
		super(parent, style);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.group = new Group(this, SWT.NONE);
		this.initializeGroup(name, columns);
	}

	protected void initializeGroup(String name, int columns) {
		GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		this.group.setText(name);
		this.group.setLayout(layout);
		this.group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}
}
