/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Jeremie TATIBOUET (CEA LIST) - Animation refactoring and improvements
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1;

import org.eclipse.papyrus.moka.animation.engine.IAnimated;
import org.eclipse.papyrus.moka.animation.engine.IAnimationManager;

public abstract class SemanticVisitor implements IAnimated{

	protected IAnimationManager animationManager;
	
	public void _endIsolation() {
		// System.out.println("_endIsolation");
	}

	public void _beginIsolation() {
		// System.out.println("_beginIsolation");
	}
	
	public void animate(IAnimationManager animationManager){
		// Do nothing by default
		// Should be overridden in adequate visitors
	}
	
	public void notifyAnimationEnd(){
		// Do nothing by default
		// Should be overridden in adequate visitors
	}
}
