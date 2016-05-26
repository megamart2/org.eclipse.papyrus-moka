package org.eclipse.papyrus.moka.fmu.engine.de;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.discreteevent.DefaultPushPullStrategy;
import org.eclipse.papyrus.moka.discreteevent.Event;

public class FMIPushPullStrategy extends DefaultPushPullStrategy {

	public List<Event> pullEvents(double date) {
		List<Event> selectedEvents = new ArrayList<Event>() ;
		int i = 0 ;
		Event fmuStepEndEvent = null ;
		while (i < this.scheduler.getEvents().size() && this.scheduler.getEvents().get(i).getAbsoluteDate() == date) {
			Event event = this.scheduler.getEvents().get(i) ;
			if (event.getAction() instanceof FMUStepEnd) {
				fmuStepEndEvent = event ;
			}
			selectedEvents.add(this.scheduler.getEvents().get(i)) ;
			i ++ ;
		}
		if (selectedEvents.size() > 1 && fmuStepEndEvent != null) {
			// This is to ensure that for a given date,
			// the fmuStepEndEvent will be treated last
			selectedEvents.remove(fmuStepEndEvent) ;
		}
		this.scheduler.removeAllEvents(selectedEvents) ;
		return selectedEvents ;
	}
	
}
