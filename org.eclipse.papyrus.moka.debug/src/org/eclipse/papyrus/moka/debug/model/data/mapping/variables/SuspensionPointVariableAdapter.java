package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.values.MokaValueAdapterFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public class SuspensionPointVariableAdapter extends MokaVariableAdapter {

	protected ISemanticVisitor suspensionPoint;
	
	private final String NAME = "breakpoint";
	
	private final String REFERENCE_TYPE_NAME = "";
	
	public SuspensionPointVariableAdapter(MokaDebugTarget debugTarget, ISemanticVisitor visitor) {
		super(debugTarget);
		this.suspensionPoint = visitor;
	}

	@Override
	public IValue getValue() throws DebugException {
		if(this.value == null){
			this.value = MokaValueAdapterFactory.getInstance().instantiate(this.suspensionPoint, this.debugTarget);
		}
		return this.value;
	}

	@Override
	public String getName() throws DebugException {
		return this.NAME;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.REFERENCE_TYPE_NAME;
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
