package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;

public class NullValueAdapter extends MokaValueAdapter {

	public NullValueAdapter(MokaDebugTarget debugTarget) {
		super(debugTarget);
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return "null";
	}

	@Override
	public String getValueString() throws DebugException {
		return "null";
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return this.variables.toArray(new IVariable[0]);
	}
}
