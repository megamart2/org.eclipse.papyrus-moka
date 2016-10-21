/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

public abstract class ConnectionPointActivation extends PseudostateActivation{
	
	public VertexActivation getParentVertexActivation(){
		// The parent state of a connection point reference is its owning state
		VertexActivation vertexActivation = null;
		if(this.getParent() != null){
			vertexActivation = (VertexActivation)this.getParent();
		}
		return vertexActivation;
	}
	
	@Override
	public RegionActivation getOwningRegionActivation() {
		// Return the owning region activation of the state activation
		// on which this point activation is placed.
		RegionActivation regionActivation = null;
		if(this.getParentVertexActivation() != null){
			regionActivation = (RegionActivation) this.getParentVertexActivation().getParent();
		}
		return regionActivation;
	}
}
