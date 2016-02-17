/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.services.animation;

import org.eclipse.papyrus.moka.services.animation.events.AnimationEvent;

public interface IAnimatedModelListener {

	public void nodeVisited(AnimationEvent event);
	
	public void nodeLeft(AnimationEvent event);
	
	public void valueCreated(AnimationEvent event);
	
	public void valueDestroyed(AnimationEvent event); 
	
}
