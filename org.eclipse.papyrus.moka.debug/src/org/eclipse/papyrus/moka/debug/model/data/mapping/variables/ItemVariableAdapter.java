package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;

public class ItemVariableAdapter extends MokaVariableAdapter {

	protected int index;
	
	public ItemVariableAdapter(MokaDebugTarget debugTarget, int index, IValue value) {
		super(debugTarget);
		this.index = index;
		this.value = value;
	}

	@Override
	public IValue getValue() throws DebugException {
		return this.value;
	}

	@Override
	public String getName() throws DebugException {
		return "["+index+"]";
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.value.getReferenceTypeName();
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsValueModification() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

}
