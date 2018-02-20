/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.composites.extensions.Semantics.InvocationActions;

import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.StructuredClasses.CS_Link;
import org.eclipse.papyrus.moka.composites.interfaces.extensions.Semantics.CompositeStructures.StructuredClasses.ICS_ConnectorLink;
import org.eclipse.uml2.uml.Connector;

public class CS_ConnectorLink extends CS_Link implements ICS_ConnectorLink{
	
	protected Connector connector;
	
	public void setConnector(Connector connector) {
		this.connector = connector;
	}
	
	public Connector getConnector() {
		return this.connector;
	}

}
