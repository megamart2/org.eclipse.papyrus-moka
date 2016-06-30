/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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

public class ExecutionContextVariableLabelProvider extends MokaDebugLabelProvider {
	
	public static final String CONTEXT_ICON = "resources/icons/context.gif";
	
	@Override
	public Image getImage(Object element) {
		if(element != null){
			return MokaDebugPlugin.getDefault().getImageRegistry().get(CONTEXT_ICON);
		}
		return null;
	}
}
