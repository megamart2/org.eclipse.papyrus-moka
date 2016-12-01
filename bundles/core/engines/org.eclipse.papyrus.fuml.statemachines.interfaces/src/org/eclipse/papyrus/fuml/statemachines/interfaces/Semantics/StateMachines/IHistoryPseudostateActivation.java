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
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/

package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public interface IHistoryPseudostateActivation extends IPseudostateActivation{

	public boolean hasDefaultTransition();
	
	public void restore(IStateActivation stateActivation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence);
	
	public abstract void restore(IRegionActivation regionActivation, ITransitionActivation enteringTransition, IEventOccurrence eventOccurrence);
	
}
