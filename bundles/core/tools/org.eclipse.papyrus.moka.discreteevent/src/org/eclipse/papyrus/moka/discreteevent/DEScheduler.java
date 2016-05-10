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
	protected DefaultPushPullStrategy pushPullStrategy ;
	
	public static void init(double stopTime) {
		DefaultPushPullStrategy pushPullStrategy = new DefaultPushPullStrategy() ;
		init(stopTime, pushPullStrategy) ;
	}
	
	public static void init(double stopTime, DefaultPushPullStrategy pushPullStrategy) {
		instance = new DEScheduler() ;
		instance.stopTime = stopTime ;
		instance.started = false ;
		instance.finished = false ;
		instance.pushPullStrategy = pushPullStrategy ;
		instance.pushPullStrategy.setScheduler(instance);
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
		List<Event> selectedEvents = this.pushPullStrategy.pullEvents(date) ;
		return selectedEvents ;
	}
	
	public void pushEvent(Event event) {
		this.pushPullStrategy.pushEvent(event);
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

	public List<Event> getEvents() {
		return this.events ;
	}

	public void removeAllEvents(List<Event> selectedEvents) {
		this.events.removeAll(selectedEvents) ;
	}

	public void addEventAt(int insertAt, Event event) {
		this.events.add(insertAt, event);
	}

	public void addEvent(Event event) {
		this.events.add(event) ;
	}

}
