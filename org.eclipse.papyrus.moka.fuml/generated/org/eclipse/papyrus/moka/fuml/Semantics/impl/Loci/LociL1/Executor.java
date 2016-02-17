/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IReference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutor;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ValueSpecification;

public class Executor implements IExecutor {

	/*
	 * The locus at which this executor resides.
	 */
	public ILocus locus;

	public List<ParameterValue> execute(Behavior behavior, IObject_ context, List<ParameterValue> inputs) {
		// Execute the given behavior with the given input values in the given
		// context, producing the given output values.
		// There must be one input parameter value for each input (in or in-out)
		// parameter of the behavior.
		// The returned values include one parameter value for each output
		// (in-out, out or return) parameter of the behavior.
		// The execution instance is destroyed at completion.
		IExecution execution = this.locus.getFactory().createExecution(behavior, context);
		for (int i = 0; i < inputs.size(); i++) {
			execution.setParameterValue(inputs.get(i));
		}
		execution.execute();
		List<ParameterValue> outputValues = execution.getOutputParameterValues();
		execution.destroy();
		return outputValues;
	}

	public IValue evaluate(ValueSpecification specification) {
		// Evaluate the given value specification, returning the specified
		// value.
		// Debug.println("[evaluate] Start...");
		return this.locus.getFactory().createEvaluation(specification).evaluate();
	}

	public IReference start(Class type, List<ParameterValue> inputs) {
		// Instantiate the given class and start any behavior of the resulting
		// object.
		// (The behavior of an object includes any classifier behaviors for an
		// active object or the class of the object itself, if that is a
		// behavior.)
		Debug.println("[start] Starting " + type.getName() + "...");
		IObject_ object = this.locus.instantiate(type);
		Debug.println("[start] Object = " + object);
		object.startBehavior(type, inputs);
		IReference reference = new Reference();
		reference.setReferent(object);
		return reference;
	}

	public void setLocus(ILocus locus) {
		this.locus = locus;
	}

	public ILocus getLocus() {
		return this.locus;
	}
}
