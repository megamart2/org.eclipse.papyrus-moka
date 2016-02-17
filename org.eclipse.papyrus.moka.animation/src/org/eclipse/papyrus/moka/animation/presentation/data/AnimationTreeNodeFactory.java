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

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.ExtensionalValue;

public final class AnimationTreeNodeFactory {

	public static AnimationTreeNodeFactory INSTANCE;
	
	private AnimationTreeNodeFactory(){}
	
	public static AnimationTreeNodeFactory getInstance(){
		if(INSTANCE==null){
			INSTANCE = new AnimationTreeNodeFactory();
		}
		return INSTANCE;
	}
	
	public IAnimationTreeNode createNode(Object input){
		IAnimationTreeNode node = null; 
		if(input == null){
			node = this.createAnimationTreeNode();
		}else if(input instanceof Diagram){
			node = this.createDiagramAnimationNode((Diagram)input);
		}else if(input instanceof ExtensionalValue){
			node = this.createAnimatingInstanceNode((ExtensionalValue)input);
		}
		return node;
	}
	
	public IAnimationTreeNode createAnimationTreeNode(){
		return new AnimationTreeNode();
	}
	
	public IAnimationTreeNode createAnimatingInstanceNode(ExtensionalValue value){
		AnimatingInstanceNode node = new AnimatingInstanceNode();
		node.setInstance(value);
		return node;
	}
	
	public IAnimationTreeNode createDiagramAnimationNode(Diagram diagram){
		DiagramAnimationNode node = new DiagramAnimationNode();
		node.setAnimatedDiagram(diagram);
		return node;
	}
	
}
