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

import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy to create a CallBehavior from a Behavior drop and to update
 * the "behavior" reference.
 * 
 * @author SR246418
 *
 */
public class BehaviorToCallBehaviorActionStrategy extends AbstractDropInActivityStrategy {

	public BehaviorToCallBehaviorActionStrategy() {
		//we call super constructor with the EClass parameter (we don't use the ElementType) to avoid to trigger
		//the creation advice that will prompt user for the selection of a behavior. In our case the behavior 
		//is already known.
		super(UMLPackage.eINSTANCE.getBehavior(), UMLPackage.eINSTANCE.getCallBehaviorAction(), UMLPackage.eINSTANCE.getCallBehaviorAction_Behavior());
		setNamePrefix("call");
	}

}
