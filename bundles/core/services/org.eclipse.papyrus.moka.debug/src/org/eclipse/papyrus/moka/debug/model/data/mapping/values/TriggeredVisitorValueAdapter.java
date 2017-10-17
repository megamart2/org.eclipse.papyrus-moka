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

package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.EventOccurrenceVariableAdapter;
import org.eclipse.papyrus.moka.fuml.Profiling.ITriggeredVisitor;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public class TriggeredVisitorValueAdapter extends VisitorValueAdapter{

	public TriggeredVisitorValueAdapter(MokaDebugTarget debugTarget, ISemanticVisitor visitor) {
		super(debugTarget, visitor);
	}

	@Override
	public String getValueString() throws DebugException {
		// Returns this value as a String.
		// The string representation of this value is given by the toString
		// operation implemented by a semantic visitor.
		if(this.adaptedObject != null){
			return this.adaptedObject.toString();
		}
		return "<empty>";
	}
	
	@Override
	public IVariable[] getVariables() throws DebugException {
		if (this.variables.isEmpty()) {
			this.variables.add(new EventOccurrenceVariableAdapter(debugTarget,
					((ITriggeredVisitor) this.adaptedObject).getTriggeringEventOccurrence()));
		}
		return this.variables.toArray(new IVariable[0]);
	}

}
