/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.ObjectTokenVariableValueAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.TokensVariableAdapter;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IForkedToken;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IObjectNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IObjectToken;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public class ObjectNodeActivationValueAdapter extends ActivityNodeActivationValueAdapter<IObjectNodeActivation>{

	public ObjectNodeActivationValueAdapter(MokaDebugTarget debugTarget, IObjectNodeActivation value) {
		super(debugTarget, value);
	}
	
	@Override
	public IVariable[] getVariables() throws DebugException {
		if(this.variables.isEmpty()) {
			 List<IToken> tokens = this.getTokens();
			 if(tokens.size() == 1) {
				 IToken token = tokens.iterator().next(); 
				 IObjectToken objectToken = null;
				 if(token instanceof IForkedToken) {
					objectToken = (IObjectToken)((IForkedToken)token).getBaseToken();
				 }else {
					objectToken = (IObjectToken) token;
				 }
				 this.variables.add(new ObjectTokenVariableValueAdapter(debugTarget, objectToken.getValue()));
			 }else {
				 this.variables.add(new TokensVariableAdapter(debugTarget, tokens));
			 }
		}
		return this.variables.toArray(new IVariable[0]);
	}
	
	public List<IToken> getTokens() {
		// By default try to get tokens from the offer attached to the
		// adapted activation
		return this.adaptedObject.getTokens();
	}

}
