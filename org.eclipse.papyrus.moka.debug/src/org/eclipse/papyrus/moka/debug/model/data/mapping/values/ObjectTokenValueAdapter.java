package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public class ObjectTokenValueAdapter extends TokenValueAdapter {

	public ObjectTokenValueAdapter(MokaDebugTarget debugTarget, IToken token) {
		super(debugTarget, token);
	}
	
	@Override
	public IVariable[] getVariables() throws DebugException {
		if(this.variables.isEmpty()){
			//this.variables.add(e);
		}
		return this.variables.toArray(new IVariable[0]);
	}
}
