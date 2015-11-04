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
package org.eclipse.papyrus.moka.fuml.registry.service.framework;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.registry.SystemServicesRegistryUtils;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;

public abstract class AbstractService extends Object_ {

	protected HashMap<Operation, OpaqueBehavior> ownedOperation;

	protected HashMap<Operation, Execution> operationExecution;

	private void initializeOperations(Class classifier) {
		this.ownedOperation = new HashMap<Operation, OpaqueBehavior>();
		for (Operation op : classifier.getAllOperations()) {
			this.ownedOperation.put(op, SystemServicesRegistryUtils.getInstance().generateOpaqueBehaviorSignature(op));
		}
	}

	public AbstractService(Class service) {
		super();
		this.types.add(service);
		this.initializeOperations(service);
		this.operationExecution = new HashMap<Operation, Execution>();
	}

	@Override
	public Execution dispatch(Operation operation) {
		if (this.operationExecution.containsKey(operation)) {
			return this.operationExecution.get(operation);
		}
		throw new NotImplementedException("Not any ServiceOperationExecution implemented/registered for this operation");
	}

	public abstract class ServiceOperationExecution extends OpaqueBehaviorExecution {

		protected Operation operation;

		public ServiceOperationExecution(Operation operation) {
			this.operation = operation;
		}

		@Override
		public Behavior getBehavior() {
			return AbstractService.this.ownedOperation.get(this.operation);
		}

		@Override
		public abstract void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters);

		@Override
		public abstract Value new_();
	}
}
