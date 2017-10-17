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
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.FeatureValueVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.presentations.MokaDebugLabelProvider;
import org.eclipse.swt.graphics.Image;

public class FeatureValueVariableLabelProvider extends MokaDebugLabelProvider {

	public static final String PUBLIC_ICON = "resources/icons/public.gif";
	
	public static final String PROTECTED_ICON = "resources/icons/protected.gif";
	
	public static final String PRIVATE_ICON = "resources/icons/private.gif";
	
	@Override
	public Image getImage(Object element) {
		if(element != null){
			FeatureValueVariableAdapter variable = (FeatureValueVariableAdapter)element;
			if(variable.isFeaturePrivate()){
				return MokaDebugPlugin.getDefault().getImageRegistry().get(PRIVATE_ICON);
			}else if(variable.isFeatureProtected()){
				return MokaDebugPlugin.getDefault().getImageRegistry().get(PROTECTED_ICON);
			}else{
				return MokaDebugPlugin.getDefault().getImageRegistry().get(PUBLIC_ICON);
			}
		}
		return null;
	}

}
