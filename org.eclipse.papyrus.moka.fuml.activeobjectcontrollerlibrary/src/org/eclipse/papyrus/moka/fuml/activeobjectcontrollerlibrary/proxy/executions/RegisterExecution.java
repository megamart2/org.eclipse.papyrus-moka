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

package org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.proxy.executions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.FeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.activeobjectcontrollerlibrary.ui.ActiveObjectControllerUI;
import org.eclipse.papyrus.moka.fuml.registry.SystemServicesRegistryUtils;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;

public class RegisterExecution extends ActiveObjectControlerUIProxyMethodExecution {

	protected static Behavior method;

	@Override
	public Behavior getBehavior() {
		if (method == null) {
			method = SystemServicesRegistryUtils.getInstance().generateOpaqueBehaviorSignature(this.operation);
		}
		return method;
	}

	public RegisterExecution(Operation operation, IObject_ context) {
		super(operation, context);
	}

	@Override
	public void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters) {

		/**
		 * Add register object in the UML property containing in the ActiveObjectController model,
		 * Then, register it in the UI.
		 */
		try {
			IValue v = inputParameters.get(0).values.get(0);
			IObject_ context = this.getContext();
			if (context.getFeatureValues().size() == 0) {
				context.createFeatureValues();
			}
			List<FeatureValue> featureValues = context.getFeatureValues();
			for (int i = 0; i < featureValues.size(); i++) {
				FeatureValue cddFeature = featureValues.get(i);
				if (cddFeature.feature instanceof Property) {
					if (cddFeature.feature.getName().equals("registered")) {
						IValue cddValue = v.copy();
						cddFeature.values.add(cddValue);
					}
				}
			}
			ActiveObjectControllerUI.getInstance().register(v);
		} catch (Exception e) {
			// TODO Put it on papyrus error log
			// Debug.println("An error occured during execution of " + this.operation.getQualifiedName());
			System.out.println(e);
		}
	}

	@Override
	public IValue new_() {
		return new RegisterExecution(this.operation, this.context);
	}

}
