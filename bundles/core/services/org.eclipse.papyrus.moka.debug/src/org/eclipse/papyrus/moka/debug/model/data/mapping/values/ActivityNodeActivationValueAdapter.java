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
package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.TokensVariableAdapter;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;

public class ActivityNodeActivationValueAdapter extends MokaValueAdapter {

	protected IActivityNodeActivation nodeVisitor;

	public ActivityNodeActivationValueAdapter(MokaDebugTarget debugTarget, IActivityNodeActivation nodeVisitor) {
		super(debugTarget);
		this.nodeVisitor = nodeVisitor;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.nodeVisitor.getNode().getQualifiedName();
	}

	@Override
	public String getValueString() throws DebugException {
		return this.nodeVisitor.toString();
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if (this.variables.isEmpty()) {
			this.variables.add(new TokensVariableAdapter(this.debugTarget, this.nodeVisitor.getTokens()));
		}
		return this.variables.toArray(new IVariable[0]);
	}

}