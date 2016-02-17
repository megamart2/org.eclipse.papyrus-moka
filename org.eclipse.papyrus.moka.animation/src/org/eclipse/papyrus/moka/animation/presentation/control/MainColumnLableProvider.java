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
package org.eclipse.papyrus.moka.animation.presentation.control;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.papyrus.moka.animation.presentation.data.AnimatingInstanceNode;
import org.eclipse.papyrus.moka.animation.presentation.data.AnimationTreeNode;
import org.eclipse.papyrus.moka.animation.presentation.data.DiagramAnimationNode;

public class MainColumnLableProvider extends ColumnLabelProvider {

	protected RootLabelProvider rootLabelProvider;
	
	protected AnimatingInstanceLabelProvider animatorLabelProvider;
	
	public MainColumnLableProvider() {
		this.rootLabelProvider = new RootLabelProvider();
		this.animatorLabelProvider = new AnimatingInstanceLabelProvider(); 
	}
	
	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		if(element instanceof AnimatingInstanceNode){
			  cell.setText(this.animatorLabelProvider.getText(element));
			  cell.setImage(this.animatorLabelProvider.getImage(element));
		  }else if(element instanceof DiagramAnimationNode){
			  DiagramAnimationNode node = (DiagramAnimationNode) element;
			  cell.setText(node.getAnimatedDiagram().getName());
		  }else if(element instanceof AnimationTreeNode){
			  cell.setText(this.rootLabelProvider.getText(element));
			  cell.setImage(this.rootLabelProvider.getImage(element));
		  }else{
			  cell.setText("Unknown");
		  }
	}

}
