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
package org.eclipse.papyrus.moka.xygraph.properties.constraints;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

public class IsXYGraphConstraint extends AbstractConstraint{

	@Override
	protected boolean equivalent(Constraint constraint) {
		return constraint == this || constraint instanceof IsXYGraphConstraint;
	}

	@Override
	protected boolean match(Object selection) {
		final boolean expectedValue = Boolean.parseBoolean(getValue("expectedValue")); //$NON-NLS-1$
		final EObject obj = EMFHelper.getEObject(selection);
		return (obj instanceof XYGraphDescriptor) == expectedValue;
	}

}
