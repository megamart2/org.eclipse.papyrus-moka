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
package org.eclipse.papyrus.moka.xygraph.modelexplorer.queries;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

public class IsXYGraphContainer implements IJavaQuery2<EObject, Boolean>{

	@Override
	public Boolean evaluate(EObject source, IParameterValueList2 parameterValues, IFacetManager facetManager)
			throws DerivedTypedElementException {
		
		Collection<Setting> settings = EMFHelper.getUsages(source);
		if (settings != null) {
			for (Setting setting : settings) {
				EObject usingElement = setting.getEObject();
				if (usingElement instanceof XYGraphDescriptor) {
					XYGraphDescriptor table = (XYGraphDescriptor) usingElement;
					EObject parent = table.getContext();
					if (parent == source) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
