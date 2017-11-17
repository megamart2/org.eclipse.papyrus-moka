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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_InteractionPoint;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.MokaVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.presentations.MokaDebugLabelProvider;
import org.eclipse.swt.graphics.Image;

public class CS_InteractionPointVariableLabelProvider extends MokaDebugLabelProvider{

	@Override
	public Image getImage(Object element) {
		if(element != null){
			MokaVariableAdapter<?> variableAdapter = (MokaVariableAdapter<?>) element; 
			ICS_InteractionPoint interactionPoint = (ICS_InteractionPoint) variableAdapter.getAdapted();
			ILabelProvider provider = this.getPapyrusLabelProvider(interactionPoint.getDefiningPort());
			if(provider != null) {
				return provider.getImage(interactionPoint.getDefiningPort());
			}
		}
		return null;
	}

}
