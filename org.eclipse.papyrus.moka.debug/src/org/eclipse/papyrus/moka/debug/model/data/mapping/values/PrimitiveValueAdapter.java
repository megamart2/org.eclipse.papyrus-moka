package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IPrimitiveValue;

public class PrimitiveValueAdapter extends MokaValueAdapter {

	protected IPrimitiveValue value;
	
	public PrimitiveValueAdapter(MokaDebugTarget debugTarget, IPrimitiveValue value) {
		super(debugTarget);
		this.value = value;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.value.getTypes().iterator().next().getName();
	}

	@Override
	public String getValueString() throws DebugException {
		return this.value.toString();
	}

}
