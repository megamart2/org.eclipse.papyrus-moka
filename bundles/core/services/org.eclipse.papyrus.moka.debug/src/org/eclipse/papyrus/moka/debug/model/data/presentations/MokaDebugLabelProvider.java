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
package org.eclipse.papyrus.moka.debug.model.data.presentations;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.MokaVariableAdapter;

public abstract class MokaDebugLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// Do nothing
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// Do nothing
	}
	
	@Override
	public boolean isLabelProperty(Object element, String property) {
		// Do nothing
		return false;
	}
	
	@Override
	public void dispose() {
		// Do nothing
	}
	
	@Override
	public String getText(Object element) {
		if(element != null){
			try {
				return ((MokaVariableAdapter<?>)element).getName();
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
