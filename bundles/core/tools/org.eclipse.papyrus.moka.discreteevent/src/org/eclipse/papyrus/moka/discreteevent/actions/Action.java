package org.eclipse.papyrus.moka.discreteevent.actions;

import org.eclipse.papyrus.moka.discreteevent.Event;

public abstract class Action {

	protected Event event ;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public abstract void execute() ;
}
