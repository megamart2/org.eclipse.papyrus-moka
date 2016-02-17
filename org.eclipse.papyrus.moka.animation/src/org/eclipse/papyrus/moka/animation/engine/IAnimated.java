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
 *  Jeremie TATIBOUET (CEA LIST) - Animation refactoring and improvements
 *
 *****************************************************************************/

package org.eclipse.papyrus.moka.animation.engine;

public interface IAnimated {
	// An animated can be animated during a specific period of time. Note
	// that this period may also be unknown. Indeed when blocked on a signal
	// acceptance the accept event signal action must terminate its animation
	// period only when a signal is dispatched and match its related accepter
	public void animate(IRender animationManager);
	
	// Callback used by specific nodes such as CallBehaviorAction, CallOperationAction and
	// AcceptEventAction in order to notify the animation service that a specific
	// node terminated its activation period. 
	public void notifyAnimationEnd();
}
