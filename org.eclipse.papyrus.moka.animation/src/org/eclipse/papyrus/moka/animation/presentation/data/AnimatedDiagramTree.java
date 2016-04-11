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

public class AnimatedDiagramTree {

	protected IAnimationTreeNode root;

	public AnimatedDiagramTree() {
		this.root = AnimationTreeNodeFactory.getInstance().createAnimationTreeNode();
	}

	public IAnimationTreeNode getRoot() {
		return this.root;
	}

}
