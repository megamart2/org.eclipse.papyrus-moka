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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.animation.presentation.data;

import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.ExtensionalValue;

public class AnimatingInstanceNode extends AnimationTreeNode {
	
	public ExtensionalValue instance;

	public boolean allowed;

	public AnimatingInstanceNode(){
		super();
		this.allowed = true;
	}
	
	public ExtensionalValue getInstance() {
		return instance;
	}

	public void setInstance(ExtensionalValue instance) {
		this.instance = instance;
	}
	
	@Override
	public boolean addChild(IAnimationTreeNode node) {
		// This node is not intended to have children
		return false;
	}

	@Override
	public boolean removeChild(IAnimationTreeNode node) {
		// This node is not intended to have children
		return false;
	}
	
	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}
}
