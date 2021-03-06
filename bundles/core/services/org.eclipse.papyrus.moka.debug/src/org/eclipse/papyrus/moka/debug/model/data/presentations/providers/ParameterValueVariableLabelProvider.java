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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.MokaVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.presentations.MokaDebugLabelProvider;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.swt.graphics.Image;

public class ParameterValueVariableLabelProvider extends MokaDebugLabelProvider{

	@Override
	public Image getImage(Object element) {
		if(element != null) {
			MokaVariableAdapter<?> variableAdapter = (MokaVariableAdapter<?>) element; 
			IParameterValue parameterValue = (IParameterValue) variableAdapter.getAdapted();
			ILabelProvider provider = this.getPapyrusLabelProvider(parameterValue.getParameter());
			if(provider != null) {
				return provider.getImage(parameterValue.getParameter());
			}
		}
		return null;
	}

}
