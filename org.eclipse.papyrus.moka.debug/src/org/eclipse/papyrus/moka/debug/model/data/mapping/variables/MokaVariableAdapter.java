package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugElement;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;

public abstract class MokaVariableAdapter extends MokaDebugElement implements IVariable {
	
	// The value that is currently associated to this variable
	protected IValue value;
	
	public MokaVariableAdapter(MokaDebugTarget debugTarget) {
		super(debugTarget);
	}
}
