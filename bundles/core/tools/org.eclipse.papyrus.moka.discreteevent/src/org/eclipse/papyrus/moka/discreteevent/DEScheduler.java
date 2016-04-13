package org.eclipse.papyrus.moka.discreteevent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.discreteevent.actions.Action;

public class DEScheduler {

	protected double currentTime = 0.0 ;
	protected double stopTime = -1.0 ;
	protected boolean finished = false ;
	protected boolean started = false ;
	protected List<Event> events = new ArrayList<Event>() ;
	protected List<Action> preRunActions = new ArrayList<Action>() ;
	protected List<Action> preStepActions = new ArrayList<Action>() ;
	protected List<Action> postStepActions = new ArrayList<Action>() ;
	protected static DEScheduler instance ;
	
	public static void init(double stopTime) {
		instance = new DEScheduler() ;
		instance.stopTime = stopTime ;
		instance.started = false ;
		instance.finished = false ;
	}
	
	public static DEScheduler getInstance() {
		return instance ;
	}
	
	public void run() {
		for (Action a : preRunActions) {
			a.execute();
		}
		while (!finished) {
			this.step() ;
		}
	}
	
	
	
	public void step() {
		this.updateTime();
		if (!finished) {
			for (Action a : preStepActions) {
				a.execute() ;
			}
			List<Event> events = this.pullEvents(currentTime) ;
			for (Event e : events) {
				e.getAction().execute() ;
			}
			for (Action a : postStepActions) {
				a.execute() ;
			}
		}
	}
	
	public void updateTime() {
		if (this.events.isEmpty()) {
			this.finished = true ;
		}
		else {
			currentTime = this.events.get(0).getAbsoluteDate() ;
		}
		if (stopTime != -1 && (currentTime >= stopTime)) {
			this.finished = true ;
		}
	}
	
	public List<Event> pullEvents(double date) {
		List<Event> selectedEvents = new ArrayList<Event>() ;
		int i = 0 ;
		while (i < this.events.size() && this.events.get(i).getAbsoluteDate() == date) {
			selectedEvents.add(this.events.get(i)) ;
			i ++ ;
		}
		this.events.removeAll(selectedEvents) ;
		return selectedEvents ;
	}
	
	public void pushEvent(Event event) {
		event.setAbsoluteDate(this.currentTime + event.getRelativeDate());
		event.setScheduler(this) ;
		int i = 0 ;
		int insertAt = 0 ;
		boolean foundInsertion = false ;
		while (i < this.events.size() && !foundInsertion) {
			if (this.events.get(i).getAbsoluteDate() < event.getAbsoluteDate()) {
				insertAt = i + 1 ;
				i++ ;
			}
			else {
				foundInsertion = true ;
			}
		}
		if (insertAt < this.events.size()) {
			this.events.add(insertAt, event);
		}
		else {
			this.events.add(event);
		}
	}
	
	public void pushPreRunAction(Action action) {
		this.preRunActions.add(action) ;
	}
	
	public void pushPreStepAction(Action action) {
		this.preStepActions.add(action) ;
	}
	
	public void pushPostStepAction(Action action) {
		this.postStepActions.add(action) ;
	}
	
	public double getCurrentTime() {
		return this.currentTime ;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

}
