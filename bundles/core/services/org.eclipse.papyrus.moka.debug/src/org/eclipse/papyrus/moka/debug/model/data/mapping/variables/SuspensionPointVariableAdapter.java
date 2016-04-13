/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
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
		if (this.value == null) {
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
