/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  (CEA LIST) - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.moka.tests;

import org.eclipse.papyrus.moka.tests.semantics.async.fuml.AsyncRunAllTestsButTestSuite4;
import org.eclipse.papyrus.moka.tests.semantics.async.fuml.AsyncRunTestSuite4;
import org.eclipse.papyrus.moka.tests.semantics.composites.CompositeRunAllTestsButTestSuite4;
import org.eclipse.papyrus.moka.tests.semantics.composites.CompositeRunTestSuite4;
import org.eclipse.papyrus.moka.tests.semantics.composites.modeling.utils.RunGenerateFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CompositeRunAllTestsButTestSuite4.class, CompositeRunTestSuite4.class, AsyncRunAllTestsButTestSuite4.class, AsyncRunTestSuite4.class, RunGenerateFactoryTest.class })
public class AllTests {

	public AllTests() {
		super();
	}
}
