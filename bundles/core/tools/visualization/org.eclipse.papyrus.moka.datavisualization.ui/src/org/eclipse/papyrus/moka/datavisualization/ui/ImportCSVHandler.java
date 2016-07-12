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
package org.eclipse.papyrus.moka.datavisualization.ui;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.ui.command.AbstractCommandHandler;
import org.eclipse.uml2.uml.Package;

public class ImportCSVHandler extends AbstractCommandHandler{

	@Override
	protected Command getCommand(IEvaluationContext context) {
		EObject selectedElement = getSelectedElement();
		if (getSelectedElement() instanceof Package){
			return new LoadCSVCommand(getEditingDomain(context),(Package)selectedElement, context);
		}
		return null;
	}

	
}
