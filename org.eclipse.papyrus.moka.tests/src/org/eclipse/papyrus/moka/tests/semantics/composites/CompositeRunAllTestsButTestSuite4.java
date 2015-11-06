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

import org.eclipse.papyrus.moka.tests.AbstractMokaLaunchConfigurationDelegate;
import org.eclipse.papyrus.moka.tests.semantics.AbstractMokaTest;

/**
 * Tests execution of all normative PSCS Test suites but Test suite 4, with the Moka synchronous implementation
 * of the normative PSCS specification.
 *
 */
public class CompositeRunAllTestsButTestSuite4 extends AbstractMokaTest {

	public static final String ACTIVITY_NAME = "RunAllTests"; //$NON-NLS-1$
	
	public String RESOURCES_PATH = "/resources/Test Suite"; //$NON-NLS-1$

	public String MODEL_NAME = "/TestSuite"; //$NON-NLS-1$

	@Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	}
	
	@Override
	public String getResourcesPath() {
		return RESOURCES_PATH;
	}

	@Override
	public String getModelName() {
		return MODEL_NAME;
	}

	@Override
	public AbstractMokaLaunchConfigurationDelegate getLaunchDelegate() {
		return new CompositesEngineLaunchDelegate();
	}

}
