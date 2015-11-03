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
package org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.InvocationActions;

// Imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.CallOperationActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Stereotype;

public class CS_CallOperationActionActivation extends CallOperationActionActivation {

	@Override
	public void doAction() {
		CallOperationAction action = (CallOperationAction) (this.node);
		// First determines if this is a call to a constructor and if a default
		// construction strategy needs to be applied.
		// This is a call to a constructor if the called operation has
		// stereotype <<Create>> applied.
		// The default construction strategy is used if no method is associated with the
		// <<Create>> operation.
		// Otherwise, behaves as in fUML.
		if (action.getOnPort() == null && this.isCreate(action.getOperation()) && action.getOperation().getMethods().size() == 0) {
			Locus locus = this.getExecutionLocus();
			CS_ConstructStrategy strategy = ((CS_ConstructStrategy) locus.factory.getStrategy("constructStrategy"));
			Value target = this.takeTokens(action.getTarget()).get(0);
			if (target instanceof CS_Reference) {
				strategy.construct(action.getOperation(), ((CS_Reference) target).compositeReferent);
				List<Parameter> parameters = action.getOperation().getOwnedParameters();
				List<OutputPin> resultPins = action.getResults();
				List<Value> values = new ArrayList<Value>();
				values.add(target);
				int i = 1;
				while (i <= parameters.size()) {
					Parameter parameter = parameters.get(i - 1);
					if (parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
						OutputPin resultPin = resultPins.get(0);
						this.putTokens(resultPin, values);
					}
					i = i + 1;
				}
			}
		} else {
			super.doAction();
		}
	}

	@Override
	public Execution getCallExecution() {
		// If onPort is not specified, behaves like in fUML
		// If onPort is specified, and if the value on the target input pin is a
		// reference, dispatch the operation
		// to it and return the resulting execution object.
		// As compared to fUML, instead of dispatching directly to target reference
		// by calling operation dispatch:
		// - If the invoked BehavioralFeature is on a provided Interface but not on any required Interface,
		// then, when the InvocationAction is executed, the invocation is made into the object given on
		// the target InputPin through the given Port
		// - If the invoked BehavioralFeature is on a required Interface but not on any provided Interface,
		// then, if the InvocationAction is being executed inside the object given on the target InputPin,
		// the invocation is forwarded out of the target object through the given Port.
		// - If the invoked BehavioralFeature is on both a provided and a required Interface,
		// then, if the InvocationAction is being executed inside the object given on the target InputPin,
		// the invocation is made out of the target object through the given Port.
		// Otherwise the invocation is made into the target object through the given Port.

		CallOperationAction action = (CallOperationAction) (this.node);
		Execution execution = null;
		if (action.getOnPort() == null) {
			execution = super.getCallExecution();
		} else {
			Value target = this.takeTokens(action.getTarget()).get(0);
			if (target instanceof CS_Reference) {
				// Tries to determine if the operation call has to be
				// dispatched to the environment or to the internals of
				// target, through onPort
				CS_Reference targetReference = (CS_Reference) target;
				Object_ executionContext = this.group.activityExecution.context;
				boolean operationIsOnProvidedInterface = this.isOperationProvided(action.getOnPort(), action.getOperation());
				boolean operationIsOnRequiredInterface = this.isOperationRequired(action.getOnPort(), action.getOperation());
				// Operation on a provided interface only
				if (operationIsOnProvidedInterface && !operationIsOnRequiredInterface) {
					execution = targetReference.dispatchIn(action.getOperation(), action.getOnPort());
				}
				// Operation is on a required interface only
				else if (!operationIsOnProvidedInterface && operationIsOnRequiredInterface) {
					// If not executing in the context of the target,
					// Semantics are undefined.
					// Otherwise, dispatch outside.
					if (executionContext == targetReference.referent || targetReference.compositeReferent.contains(executionContext)) {
						execution = targetReference.dispatchOut(action.getOperation(), action.getOnPort());
					}
				}
				// Operation is both on a provided and a required interface
				else if (operationIsOnProvidedInterface && operationIsOnRequiredInterface) {
					if (executionContext == targetReference.referent || targetReference.compositeReferent.contains(executionContext)) {
						execution = targetReference.dispatchOut(action.getOperation(), action.getOnPort());
					} else {
						execution = targetReference.dispatchIn(action.getOperation(), action.getOnPort());
					}
				}
			}
		}
		return execution;
	}

	public boolean isOperationProvided(Port port, Operation operation) {
		boolean isProvided = false;
		if (operation.getOwner() instanceof Interface) {
			// We have to look in provided interfaces of the port if
			// they define directly or indirectly the Operation
			Integer interfaceIndex = 1;
			// Iterates on provided interfaces of the port
			List<Interface> providedInterfaces = port.getProvideds();
			while (interfaceIndex <= providedInterfaces.size() && !isProvided) {
				Interface interface_ = providedInterfaces.get(interfaceIndex - 1);
				// Iterates on members of the current Interface
				Integer memberIndex = 1;
				while (memberIndex <= interface_.getMembers().size() && !isProvided) {
					NamedElement cddOperation = interface_.getMembers().get(memberIndex - 1);
					if (cddOperation instanceof Operation) {
						isProvided = operation == cddOperation;
					}
					memberIndex = memberIndex + 1;
				}
				interfaceIndex = interfaceIndex + 1;
			}
		}
		return isProvided;
	}

	public boolean isOperationRequired(Port port, Operation operation) {
		boolean isRequired = false;
		Integer interfaceIndex = 1;
		// Iterates on provided interfaces of the port
		List<Interface> requiredInterfaces = port.getRequireds();
		while (interfaceIndex <= requiredInterfaces.size() && !isRequired) {
			Interface interface_ = requiredInterfaces.get(interfaceIndex - 1);
			// Iterates on members of the current Interface
			Integer memberIndex = 1;
			while (memberIndex <= interface_.getMembers().size() && !isRequired) {
				NamedElement cddOperation = interface_.getMembers().get(memberIndex - 1);
				if (cddOperation instanceof Operation) {
					isRequired = operation == cddOperation;
				}
				memberIndex = memberIndex + 1;
			}
			interfaceIndex = interfaceIndex + 1;
		}
		return isRequired;
	}

	public boolean isCreate(Operation o) {
		// FIXME This code is Eclipse specific
		// How to handle this in the spec?
		List<Stereotype> appliedStereotypes = o.getAppliedStereotypes();
		int i = 0;
		boolean isCreate = false;
		while (i < appliedStereotypes.size() && !isCreate) {
			Stereotype s = appliedStereotypes.get(i);
			if (s.getName().equals("Create")) {
				isCreate = true;
			}
		}
		return isCreate;
	}
}
