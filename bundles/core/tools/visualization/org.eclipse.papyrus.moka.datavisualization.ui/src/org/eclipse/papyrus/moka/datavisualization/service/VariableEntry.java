/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.datavisualization.service;

import org.eclipse.papyrus.moka.xygraph.mapping.common.Variable;

public class VariableEntry {
	private Variable variable;
	private long lastUpdated;
	
	public VariableEntry(Variable variable) {
		this.variable = variable;
		lastUpdated = -1;
	}
	
	public void markUpdate(){
		lastUpdated = System.nanoTime();
	}
	
	public Variable getVariable() {
		return variable;
	}

	public long getLastUpdate() {
		return lastUpdated;
	}
}
