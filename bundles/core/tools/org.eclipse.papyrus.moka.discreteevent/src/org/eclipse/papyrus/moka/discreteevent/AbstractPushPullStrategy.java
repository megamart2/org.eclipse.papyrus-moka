package org.eclipse.papyrus.moka.discreteevent;

import java.util.List;

public abstract class AbstractPushPullStrategy {

	protected DEScheduler scheduler ;
	
	public DEScheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(DEScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public abstract List<Event> pullEvents(double date) ;
	
	public abstract void pushEvent(Event event) ;
	
	public abstract void pushEvent(Event event, double absoluteDate) ;
	
}
