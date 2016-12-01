/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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
package org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines;

import java.util.List;

public interface IStateConfiguration {
	
	public int getLevel();
	
	public IStateConfiguration getParent();
	
	public void setParent(IStateConfiguration configuration);
	
	public IVertexActivation getVertexActivation();
	
	public List<IStateConfiguration> getChildren();
	
	public boolean addChild(IVertexActivation activation);
	
	public boolean removeChild(IVertexActivation activation);
	
	public boolean add(IVertexActivation activation, List<IVertexActivation> context);
	
	public boolean remove(IVertexActivation activation, List<IVertexActivation> context);
}
