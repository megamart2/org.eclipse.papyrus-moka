/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.moka.debug.model.data.presentations.providers;

import org.eclipse.papyrus.moka.debug.MokaDebugPlugin;
import org.eclipse.papyrus.moka.debug.model.data.presentations.MokaDebugLabelProvider;
import org.eclipse.swt.graphics.Image;

public class SignalEventOccurrenceVariableLabelProvider extends MokaDebugLabelProvider{

	public final static String SIGNAL_EVENT_ICON = "resources/icons/signal.png"; 
	
	private final static String SIGNAL_EVENT_TEXT = "Signal Event";
	
	@Override
	public String getText(Object element) {
		return SIGNAL_EVENT_TEXT;
	}
	
	@Override
	public Image getImage(Object element) {
		if(element != null){
			return MokaDebugPlugin.getDefault().getImageRegistry().get(SIGNAL_EVENT_ICON);
		}
		return null;
	}

}
