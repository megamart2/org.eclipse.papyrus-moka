package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.uml2.uml.Vertex;

public interface IStateMachineExecution extends IExecution{

	public IStateMachineConfiguration getConfiguration();
	
	public IVertexActivation getVertexActivation(Vertex vertex);
	
}
