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
package org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.proxy.ActiveObjectControllerUIProxy;
import org.eclipse.papyrus.moka.fuml.registry.AbstractSystemServicesRegistry;
import org.eclipse.uml2.uml.Class;

public class ActiveObjectControllerServicesRegistry extends AbstractSystemServicesRegistry {

	protected final static String ACTIVE_OBJECT_CONTROLLER_LIBRARY_NAME = "ActiveObjectControllerModelLibrary";

	protected final static String ACTIVE_OBJECT_CONTROLLER_UI_SERVICE_NAME = "ActiveObjectController::ControllerUI";

	@Override
	protected List<Object_> instantiateServices() {
		List<String> serviceQualifiedNames = new ArrayList<String>();
		serviceQualifiedNames.add(ACTIVE_OBJECT_CONTROLLER_UI_SERVICE_NAME);
		return this.instantiateServices(ACTIVE_OBJECT_CONTROLLER_LIBRARY_NAME, serviceQualifiedNames);
	}

	@Override
	protected Object_ instantiateService(Class service) {
		if (service.getQualifiedName().equals(ACTIVE_OBJECT_CONTROLLER_UI_SERVICE_NAME)) {
			return new ActiveObjectControllerUIProxy(service);
		}
		return null;
	}

}
