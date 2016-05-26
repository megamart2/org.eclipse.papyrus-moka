package org.eclipse.papyrus.moka.discreteevent;

import java.util.ArrayList;
import java.util.List;

public class DefaultPushPullStrategy extends AbstractPushPullStrategy {

	public List<Event> pullEvents(double date) {
		List<Event> selectedEvents = new ArrayList<Event>() ;
		int i = 0 ;
		while (i < this.scheduler.getEvents().size() && this.scheduler.getEvents().get(i).getAbsoluteDate() == date) {
			selectedEvents.add(this.scheduler.getEvents().get(i)) ;
			i ++ ;
		}
		this.scheduler.removeAllEvents(selectedEvents) ;
		return selectedEvents ;
	}
	
	public void pushEvent(Event event) {
		double absoluteDate = this.scheduler.getCurrentTime() + event.getRelativeDate() ;
		this.pushEvent(event, absoluteDate);
	}
	
	public void pushEvent(Event event, double absoluteDate) {
		event.setAbsoluteDate(absoluteDate);
		event.setScheduler(this.scheduler) ;
		int i = 0 ;
		int insertAt = 0 ;
		boolean foundInsertion = false ;
		while (i < this.scheduler.getEvents().size() && !foundInsertion) {
			if (this.scheduler.getEvents().get(i).getAbsoluteDate() < event.getAbsoluteDate()) {
				insertAt = i + 1 ;
				i++ ;
			}
			else {
				foundInsertion = true ;
			}
		}
		if (insertAt < this.scheduler.getEvents().size()) {
			this.scheduler.addEventAt(insertAt, event);
		}
		else {
			this.scheduler.addEvent(event);
		}
	}
}
