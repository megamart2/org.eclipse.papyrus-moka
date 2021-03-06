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
package org.eclipse.papyrus.moka.fuml.standardlibrary.library.real;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.RealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.uml2.uml.PrimitiveType;

public class Div extends OpaqueBehaviorExecution {

	@Override
	public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {
		try {
			Double x = ((RealValue) inputParameters.get(0).getValues().get(0)).value;
			Double y = ((RealValue) inputParameters.get(1).getValues().get(0)).value;
			if(y != 0) {
				RealValue result = new RealValue();
				result.value = x / y;
				result.type = (PrimitiveType) this.locus.getFactory().getBuiltInType("Real");
				List<IValue> outputs = new ArrayList<IValue>();
				outputs.add(result);
				outputParameters.get(0).setValues(outputs);
			}
		} catch (Exception e) {
			Activator.log.error("An error occured during the execution of Div " + e.getMessage(), e);
		}
	}

	@Override
	public IValue new_() {
		return new Div();
	}
}
