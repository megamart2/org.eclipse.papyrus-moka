/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.master.masterproxy;

import org.eclipse.papyrus.moka.fuml.registry.service.framework.AbstractServicesRegistery;

public class Master2ProxyServiceRegistery extends AbstractServicesRegistery {

	public Master2ProxyServiceRegistery() {
		super();
	}

	@Override
	public void initServiceQualifiedNames() {
		this.serviceQualifiedNames.add(Master2ProxyServiceFactory.SERVICE_NAME);
	}

	@Override
	public void initServiceFactory() {
		this.serviceFactory = new Master2ProxyServiceFactory();

	}

	@Override
	public void initLibraryName() {
		this.libraryName = "Fmi2Master";
	}

}
