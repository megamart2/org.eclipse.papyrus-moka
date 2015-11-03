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
package org.eclipse.papyrus.moka.fuml.registry.service.framework;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.registry.AbstractSystemServicesRegistry;
import org.eclipse.uml2.uml.Class;

public abstract class AbstractServicesRegistery extends AbstractSystemServicesRegistry {

	protected IServiceFactory serviceFactory;

	protected List<String> serviceQualifiedNames;

	protected String libraryName;

	public abstract void initServiceQualifiedNames();

	public abstract void initServiceFactory();

	public abstract void initLibraryName();

	public AbstractServicesRegistery() {
		this.initLibraryName();
		this.initServiceFactory();
		this.serviceQualifiedNames = new ArrayList<String>();
		this.initServiceQualifiedNames();
	}

	@Override
	public List<Object_> instantiateServices() {
		return this.instantiateServices(this.libraryName, this.serviceQualifiedNames);
	}

	@Override
	protected Object_ instantiateService(Class service) {
		return this.serviceFactory.instantiate(service);
	}
}
