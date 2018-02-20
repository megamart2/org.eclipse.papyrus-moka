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

import java.util.Iterator;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;

public class DiagramAnimationNode extends AnimationTreeNode {

	protected Diagram animatedDiagram;

	public DiagramAnimationNode() {
		super();
	}

	public Diagram getAnimatedDiagram() {
		return animatedDiagram;
	}

	public void setAnimatedDiagram(Diagram animatedDiagram) {
		this.animatedDiagram = animatedDiagram;
	}

	public boolean hasAnimator(IObject_ instance) {
		boolean found = false;
		Iterator<IAnimationTreeNode> childrenIterator = this.getChildren().iterator();
		while (!found && childrenIterator.hasNext()) {
			AnimatingInstanceNode node = (AnimatingInstanceNode) childrenIterator.next();
			if (node.getInstance() == instance) {
				found = true;
			}
		}
		return found;
	}

	public boolean isAnimatorAllowed(IObject_ instance) {
		boolean allowed = false;
		Iterator<IAnimationTreeNode> childrenIterator = this.getChildren().iterator();
		while (!allowed && childrenIterator.hasNext()) {
			AnimatingInstanceNode node = (AnimatingInstanceNode) childrenIterator.next();
			if (node.getInstance() == instance && node.isAllowed()) {
				allowed = true;
			}
		}
		return allowed;
	}

}
