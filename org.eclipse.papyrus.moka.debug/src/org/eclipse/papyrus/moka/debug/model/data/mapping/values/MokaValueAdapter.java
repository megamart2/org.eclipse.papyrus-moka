package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugElement;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;

public abstract class MokaValueAdapter extends MokaDebugElement implements IValue {

	protected List<IVariable> variables; 
	
	public MokaValueAdapter(MokaDebugTarget debugTarget) {
		super(debugTarget);
		this.variables = new ArrayList<IVariable>();
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return this.getVariables().length > 0;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return this.variables.toArray(new IVariable[0]);
	}
}
