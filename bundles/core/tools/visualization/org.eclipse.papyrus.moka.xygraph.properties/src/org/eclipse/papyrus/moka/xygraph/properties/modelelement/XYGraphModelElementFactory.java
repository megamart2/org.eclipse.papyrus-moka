/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.xygraph.properties.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

public class XYGraphModelElementFactory extends EMFModelElementFactory{

	@Override
	protected EMFModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		EObject obj = EMFHelper.getEObject(sourceElement);
		
		if( obj instanceof XYGraphDescriptor )
			return new XYGraphModelElement((XYGraphDescriptor) obj, EMFHelper.resolveEditingDomain(obj));
			
		return super.doCreateFromSource(sourceElement, context);
	}
}
