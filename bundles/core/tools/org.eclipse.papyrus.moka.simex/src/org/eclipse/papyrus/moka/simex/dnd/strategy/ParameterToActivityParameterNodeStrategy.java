/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/


package org.eclipse.papyrus.moka.simex.dnd.strategy;

import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy to create a ActivityParameterNode from a Parameter drop and to update
 * the "parameter" reference.
 * 
 * @author SR246418
 *
 */
public class ParameterToActivityParameterNodeStrategy extends AbstractDropInActivityStrategy {

	public ParameterToActivityParameterNodeStrategy() {
		//FIXME Deal with compartments
		//FIXME Deal with the type, multiplicity, etc. of the ActivityParameterNode
		super(UMLPackage.eINSTANCE.getParameter(), UMLElementTypes.ACTIVITY_PARAMETER_NODE, UMLPackage.eINSTANCE.getActivityParameterNode_Parameter());
		setNamePrefix("");
	}

}
