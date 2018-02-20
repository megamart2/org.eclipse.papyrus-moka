package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IObjectNodeActivation;
import org.eclipse.uml2.uml.ObjectNode;

public class ObjectNodeActivationVariableAdapter extends MokaVariableAdapter<IObjectNodeActivation> {

	public ObjectNodeActivationVariableAdapter(MokaDebugTarget debugTarget, IObjectNodeActivation adaptedVariable) {
		super(debugTarget, adaptedVariable);
	}
	
	@Override
	public String getName() throws DebugException {
		// The name of the variable is the name of the object node or 'empty'
		// if no node is attached to the node activation
		ObjectNode node = (ObjectNode) this.adaptedVariable.getNode();
		if (node != null) {
			return node.getName();
		}
		return "<empty>";
	}

}
