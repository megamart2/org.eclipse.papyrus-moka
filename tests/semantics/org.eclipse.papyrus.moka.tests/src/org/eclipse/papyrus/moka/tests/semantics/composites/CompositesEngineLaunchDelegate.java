/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * (CEA LIST) - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.moka.tests.semantics.composites;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.moka.engine.IExecutionEngine;
import org.eclipse.papyrus.moka.tests.AbstractMokaLaunchConfigurationDelegate;
import org.eclipse.papyrus.moka.utils.constants.MokaConstants;

public class CompositesEngineLaunchDelegate extends AbstractMokaLaunchConfigurationDelegate {

	final protected static String selectedExecutionEngine = "org.eclipse.papyrus.moka.composites";

	/**
	 * Instantiate the PSCS execution engine.
	 *
	 * @return
	 */
	public IExecutionEngine instantiateExecutionEngine() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor(MokaConstants.MOKA_ENGINE_EXTENSION_POINT_ID);
		try {
			IConfigurationElement e = null;
			for(int i = 0; i < config.length; i++) {
				if(config[i].getNamespaceIdentifier().equals(selectedExecutionEngine)) {
					e = config[i];
				}
			}
			final Object o = e.createExecutableExtension("class");
			return (IExecutionEngine)o;
		} catch (CoreException ex) {
			org.eclipse.papyrus.infra.core.Activator.log.equals(ex);
		} catch (Exception ex) {
			org.eclipse.papyrus.infra.core.Activator.log.equals(ex);
		}
		// If null is returned, the calling method (launch) fires a CoreException
		return null;
	}

}
