package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.TokensVariableAdapter;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.uml2.uml.NamedElement;

public abstract class ActivityVisitorValueAdapter extends VisitorValueAdapter {

	public ActivityVisitorValueAdapter(MokaDebugTarget debugTarget, ISemanticVisitor visitor) {
		super(debugTarget, visitor);
	}
	
	protected abstract NamedElement getNode();

	@Override
	public IVariable[] getVariables() throws DebugException {
		if (this.variables.isEmpty()) {
			this.variables.add(new TokensVariableAdapter(this.debugTarget, getTokens(this.visitor)));
		}
		return this.variables.toArray(new IVariable[0]);
	}
	
	protected static List<IToken> getTokens(ISemanticVisitor visitor){
		List<IToken> tokens = new ArrayList<>();
		if(visitor instanceof IActivityNodeActivation){
			tokens = ((IActivityNodeActivation)visitor).getTokens();
			if(tokens.isEmpty()){
				for(IActivityEdgeInstance edgeInstance :((IActivityNodeActivation)visitor).getIncomingEdges()){
					tokens.addAll(getTokens(edgeInstance));
				}
			}
		}else if(visitor instanceof IActivityEdgeInstance){
			tokens = ((IActivityEdgeInstance)visitor).getOfferedTokens();
			if(tokens.isEmpty()){
				tokens.addAll(getTokens(((IActivityEdgeInstance)visitor).getSource()));
			}
		}
		return tokens;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		NamedElement modelElement = this.getNode();
		if(modelElement != null){
			return modelElement.getQualifiedName();
		}
		return "<empty>";
	}

	@Override
	public String getValueString() throws DebugException {
		NamedElement modelElement = this.getNode();
		if(modelElement != null){
			return modelElement.getQualifiedName();
		}
		return "<empty>";
	}
}
