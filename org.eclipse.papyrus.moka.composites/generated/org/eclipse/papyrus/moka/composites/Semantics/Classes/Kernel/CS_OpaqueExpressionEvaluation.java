/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.moka.composites.Semantics.Classes.Kernel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Evaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.OpaqueExpression;

public class CS_OpaqueExpressionEvaluation extends Evaluation {

	@Override
	public Value evaluate() {
		// Execute the behavior associated with the context OpaqueExpression, if any.
		// If multiple return values are computed, then return the first one.
		// If no value are computed, return null
		List<Value> evaluation = this.executeExpressionBehavior();
		if (evaluation.size() > 0) {
			return evaluation.get(0);
		} else {
			return null;
		}
	}

	public List<Value> executeExpressionBehavior() {
		// If a behavior is associated with the context OpaqueExpression,
		// then execute this behavior, and return computed values.
		// Otherwise, return an empty list of values.
		List<Value> evaluation = new ArrayList<Value>();
		OpaqueExpression expression = (OpaqueExpression) this.specification;
		Behavior behavior = expression.getBehavior();
		if (behavior != null) {
			List<ParameterValue> inputs = new ArrayList<ParameterValue>();
			List<ParameterValue> results = this.locus.executor.execute(behavior, null, inputs);
			for (int i = 0; i < results.size(); i++) { // results.size should be 1
				ParameterValue parameterValue = results.get(i);
				List<Value> values = parameterValue.values;
				for (int j = 0; j < values.size(); j++) {
					evaluation.add(values.get(j));
				}
			}
		}
		return evaluation;
	}

}
