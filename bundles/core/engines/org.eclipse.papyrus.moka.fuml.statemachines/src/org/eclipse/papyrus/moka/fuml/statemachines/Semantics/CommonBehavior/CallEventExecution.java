/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.CommonBehavior;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.CommonBehavior.ICallEventExecution;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.CommonBehavior.ICallEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;

public class CallEventExecution extends Execution implements ICallEventExecution {

	// Called operation
	public Operation operation;
	
	// Behavior signature associated with the operation
	public Behavior behavior;
	
	// Object from which the call was emitted (possibly blocked)
	public IObject_ callerContext;
	
	protected boolean callerSuspended;
	
	public CallEventExecution(){
		this.callerSuspended = false;
	}
	
	public Operation getOperation(){
		return this.operation;
	}
	
	public void setOperation(Operation operation){
		this.operation = operation;
	}
	
	private void _suspend(){
		while(this.callerSuspended);
	}
	
	@Override
	public void execute() {
		// The execution consists in placing a new CallEventOccurrence
		// within the event pool of the target active object. Not that
		// the call will not be executed if the target is not an active
		// object. This is due to the fact that the call event could
		// not placed anywhere since a passive object has no event pool.
		// The caller is expected to be suspended until the RTC step using
		// the event occurrence terminates.
		ICallEventOccurrence eventOccurrence = new CallEventOccurrence();
		eventOccurrence.setCallEventExecution(this);
		this.callerSuspended = true;
		this._send(eventOccurrence);
		this._suspend();
		
	}
	
	public void releaseCaller(){
		this.callerSuspended = false;
	}

	@Override
	public IValue new_() {
		return new CallEventExecution();
	}
	
	@Override
	public Behavior getBehavior() {
		// Compute a behavior signature corresponding to the operation
		if(this.behavior == null){
			this.behavior = UMLFactory.eINSTANCE.createFunctionBehavior();
			for(int i = 0; i < this.operation.getOwnedParameters().size(); i++){
				Parameter operationParameter = this.operation.getOwnedParameters().get(i);
				Parameter parameter = UMLFactory.eINSTANCE.createParameter();
				parameter.setName(operationParameter.getName());
				parameter.setType(operationParameter.getType());
				parameter.setLower(operationParameter.getLower());
				parameter.setUpper(operationParameter.getUpper());
				parameter.setDirection(operationParameter.getDirection());
				this.behavior.getOwnedParameters().add(parameter);
			}
		}
		return this.behavior;
	}
	
	protected void _send(ICallEventOccurrence eventOccurrence){
		// Place the call event occurrence within the event pool of
		// the target object
		if(this.context.getObjectActivation() != null){
			this.context.getObjectActivation().getEvents().add(eventOccurrence);
			this.context.getObjectActivation().notifyEventArrival();
		}
	}
	
	public List<IParameterValue> getInputParameterValues(){
		// Return input parameter values for this execution
		List<IParameterValue> parameterValues = new ArrayList<IParameterValue>();
		for(int i=0; i < this.parameterValues.size(); i++){
			IParameterValue parameterValue = this.parameterValues.get(i);
			if(parameterValue.getParameter().getDirection() == ParameterDirectionKind.IN_LITERAL
					| parameterValue.getParameter().getDirection() == ParameterDirectionKind.INOUT_LITERAL){
				parameterValues.add(parameterValue);
			}
		}
		return parameterValues;
	}

}
