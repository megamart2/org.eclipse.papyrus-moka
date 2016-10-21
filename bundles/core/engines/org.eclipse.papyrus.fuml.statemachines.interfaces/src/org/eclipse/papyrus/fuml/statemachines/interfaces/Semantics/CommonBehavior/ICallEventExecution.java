package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.CommonBehavior;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.uml2.uml.Operation;

public interface ICallEventExecution extends IExecution{
	
	public Operation getOperation();
	
	public void setOperation(Operation operation);
	
	public void releaseCaller();
	
	public List<IParameterValue> getInputParameterValues();
	
}
