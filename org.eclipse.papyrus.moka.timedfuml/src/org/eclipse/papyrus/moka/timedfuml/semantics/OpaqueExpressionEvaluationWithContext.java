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
package org.eclipse.papyrus.moka.timedfuml.semantics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.impl.Classes.Kernel.CS_OpaqueExpressionEvaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.OpaqueExpression;

public class OpaqueExpressionEvaluationWithContext extends CS_OpaqueExpressionEvaluation {

	public IObject_ context;

	@Override
	public List<IValue> executeExpressionBehavior() {
		// If a behavior is associated with the context OpaqueExpression,
		// then execute this behavior, and return computed values.
		// Otherwise, return an empty list of values.
		List<IValue> evaluation = new ArrayList<IValue>();
		OpaqueExpression expression = (OpaqueExpression) this.specification;
		Behavior behavior = expression.getBehavior();
		if (behavior != null) {
			List<IParameterValue> inputs = new ArrayList<IParameterValue>();
			List<IParameterValue> results = this.locus.getExecutor().execute(behavior, context, inputs);
			for (int i = 0; i < results.size(); i++) { // results.size should be 1
				IParameterValue parameterValue = results.get(i);
				List<IValue> values = parameterValue.getValues();
				for (int j = 0; j < values.size(); j++) {
					evaluation.add(values.get(j));
				}
			}
		}
		return evaluation;
	}
}
