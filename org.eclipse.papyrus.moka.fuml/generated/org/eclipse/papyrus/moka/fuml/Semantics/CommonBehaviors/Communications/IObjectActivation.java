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

package org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.EventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.SignalInstance;

import org.eclipse.uml2.uml.Class;

public interface IObjectActivation {

	public void stop();
	
	public void register(IEventAccepter accepter);
	
	public void unregister(IEventAccepter accepter);
	
	public void dispatchNextEvent();
	
	public EventOccurrence getNextEvent();
	
	public void send(SignalInstance signalInstance);
	
	public void startBehavior(Class classifier, List<ParameterValue> inputs);
	
	public void setObject(IObject_ context);
	
	public IObject_ getObject();
	
	public List<IClassifierBehaviorInvocationEventAccepter> getClassifierBehaviorInvocations();
}
