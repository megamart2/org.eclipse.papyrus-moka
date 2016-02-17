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
package org.eclipse.papyrus.moka.fuml.assertionlibrary.basic;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.assertionlibrary.reporting.Reporter;
import org.eclipse.papyrus.moka.fuml.assertionlibrary.reporting.TestDecision;
import org.eclipse.papyrus.moka.fuml.assertionlibrary.reporting.TestReport;
import org.eclipse.papyrus.moka.fuml.assertionlibrary.utils.AssertionExecutionContextHelper;
import org.eclipse.uml2.uml.Classifier;


public class AssertEquals extends OpaqueBehaviorExecution {

	@Override
	public void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters) {
		Classifier executionContext = AssertionExecutionContextHelper.getExecutionContext(this);
		StringValue label = (StringValue) inputParameters.get(0).values.get(0);
		boolean equal = true;
		if (inputParameters.get(1).values.size() == inputParameters.get(2).values.size()) {
			Integer i = 0;
			while (equal == true && i < inputParameters.get(1).values.size()) {
				equal = inputParameters.get(1).values.get(i).equals(inputParameters.get(2).values.get(i));
				i++;
			}
		} else {
			equal = false;
		}
		Reporter.INSTANCE.add(new TestReport(executionContext != null ? executionContext : null, label.value, equal ? TestDecision.SUCCESS : TestDecision.FAILED, getClass()));
	}

	@Override
	public Value new_() {
		return new AssertEquals();
	}

}
