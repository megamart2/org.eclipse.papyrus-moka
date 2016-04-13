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
 * Drop strategy to create a SendSignalAction from a Signal drop and to update 
 * the "signal" reference.
 * @author SR246418
 *
 */
public class SignalToSendSignalActionStrategy extends AbstractDropInActivityStrategy {

	public SignalToSendSignalActionStrategy() {
		super(UMLPackage.eINSTANCE.getSignal(), UMLElementTypes.SEND_SIGNAL_ACTION, UMLPackage.eINSTANCE.getSendSignalAction_Signal());
		setNamePrefix("send");
	}

}
