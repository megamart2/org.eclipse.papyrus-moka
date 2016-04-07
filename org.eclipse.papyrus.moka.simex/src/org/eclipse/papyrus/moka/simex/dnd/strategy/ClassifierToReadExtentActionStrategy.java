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
 * Drop strategy to create a ReadExtentAction from a Classifier drop and to update
 * the "classifier" reference.
 * 
 * @author SR246418
 *
 */
public class ClassifierToReadExtentActionStrategy extends AbstractDropInActivityStrategy {

	public ClassifierToReadExtentActionStrategy() {
		super(UMLPackage.eINSTANCE.getClassifier(), UMLElementTypes.READ_EXTENT_ACTION, UMLPackage.eINSTANCE.getReadExtentAction_Classifier());
		setNamePrefix("readExtentOf");
	}

}
