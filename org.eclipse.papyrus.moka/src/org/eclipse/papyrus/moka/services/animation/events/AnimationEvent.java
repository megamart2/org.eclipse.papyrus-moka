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

package org.eclipse.papyrus.moka.services.animation.events;

import org.eclipse.papyrus.moka.services.MokaEvent;

/**
 * @author JT605650
 *
 */
@SuppressWarnings("serial")
public class AnimationEvent extends MokaEvent {

	protected AnimationKind command;
	
	public AnimationKind getCommand() {
		return command;
	}

	public void setCommand(AnimationKind command) {
		this.command = command;
	}

	public AnimationEvent(Object source) {
		super(source);
	}

}
