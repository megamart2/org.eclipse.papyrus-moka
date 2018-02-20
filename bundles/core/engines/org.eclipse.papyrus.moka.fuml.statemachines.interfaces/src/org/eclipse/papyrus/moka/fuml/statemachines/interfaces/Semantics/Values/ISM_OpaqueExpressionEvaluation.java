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
package org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.Values;

import org.eclipse.papyrus.moka.composites.interfaces.Semantics.Classes.Kernel.ICS_OpaqueExpressionEvaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.uml2.uml.Parameter;

public interface ISM_OpaqueExpressionEvaluation extends ICS_OpaqueExpressionEvaluation{

	public void initialize(IEventOccurrence eventOccurrence);
	
	public void setParameterValue(IParameterValue parameterValue);
	
	public IParameterValue getParameterValue(Parameter parameter);
	
	public void setContext(IObject_ context);
	
	public IObject_ getContext();
	
}
