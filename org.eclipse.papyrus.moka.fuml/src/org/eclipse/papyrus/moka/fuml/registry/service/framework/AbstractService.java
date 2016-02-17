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
 *  Jeremie Tatibouet (CEA LIST) - Refine service code to avoid deadlocks when model execution starts
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.registry.service.framework;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.registry.SystemServicesRegistryUtils;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;

public abstract class AbstractService extends Object_ {

	protected HashMap<Operation, Execution> operationExecution;

	public AbstractService(Class service) {
		super();
		this.types.add(service);
		this.operationExecution = new HashMap<Operation, Execution>();
		this.doOperationExecutionMapping();
	}
	
	@Override
	public Execution dispatch(Operation operation) {
		if (this.operationExecution.containsKey(operation)) {
			return this.operationExecution.get(operation);
		}
		throw new NotImplementedException("Not any ServiceOperationExecution implemented/registered for this operation");
	}
	
	public abstract void doOperationExecutionMapping();

	public abstract class ServiceOperationExecution extends OpaqueBehaviorExecution {

		protected Operation operation;

		public ServiceOperationExecution(Operation operation) {
			this.operation = operation;
		}

		@Override
		public Behavior getBehavior() {
			return SystemServicesRegistryUtils.getInstance().generateOpaqueBehaviorSignature(operation);
		}

		@Override
		public abstract void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters);

		@Override
		public abstract Value new_();
	}
}
