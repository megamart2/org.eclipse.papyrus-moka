package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;

public class EventOccurrenceValueAdapter extends MokaValueAdapter {

	protected IEventOccurrence eventOccurrence;
	
	public EventOccurrenceValueAdapter(MokaDebugTarget debugTarget, IEventOccurrence eventOccurrence) {
		super(debugTarget);
		this.eventOccurrence = eventOccurrence;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return "";
	}

	@Override
	public String getValueString() throws DebugException {
		return this.eventOccurrence.toString();
	}
}
