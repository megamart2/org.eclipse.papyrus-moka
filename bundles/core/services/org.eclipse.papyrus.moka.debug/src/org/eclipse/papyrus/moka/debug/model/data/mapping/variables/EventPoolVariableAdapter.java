/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import java.util.Iterator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.values.MokaValueAdapterFactory;
import org.eclipse.papyrus.moka.debug.model.data.mapping.values.MokaValueList;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IObjectActivation;

public class EventPoolVariableAdapter extends MokaVariableAdapter {

	protected IObjectActivation objectActivation;

	protected final String NAME = "events";

	public EventPoolVariableAdapter(MokaDebugTarget debugTarget, IObjectActivation objectActivation) {
		super(debugTarget);
		this.objectActivation = objectActivation;
	}

	@Override
	public IValue getValue() throws DebugException {
		if (this.value == null) {
			this.value = new MokaValueList(this.debugTarget);
			Iterator<IEventOccurrence> eventsIterator = this.objectActivation.getEvents().iterator();
			while (eventsIterator.hasNext()) {
				((MokaValueList) this.value).add(MokaValueAdapterFactory.getInstance().instantiate(eventsIterator.next(), this.debugTarget));
			}
		}
		return this.value;
	}

	@Override
	public String getName() throws DebugException {
		return this.NAME;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.value.getReferenceTypeName();
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsValueModification() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

}
