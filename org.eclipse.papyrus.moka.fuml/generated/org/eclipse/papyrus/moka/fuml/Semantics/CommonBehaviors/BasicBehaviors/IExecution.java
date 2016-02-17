/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Parameter;

public interface IExecution extends IObject_ {

	public void execute();
	
	public void setParameterValue(ParameterValue parameterValue);
	
	public ParameterValue getReturnParameterValue();
	
	public ParameterValue getParameterValue(Parameter parameter);
	
	public List<ParameterValue> getOutputParameterValues();
	
	public Behavior getBehavior();
	
	public void terminate();
	
	public void setContext(IObject_ context);
	
	public IObject_ getContext();
	
	public List<ParameterValue> getParameterValues();
	
}
