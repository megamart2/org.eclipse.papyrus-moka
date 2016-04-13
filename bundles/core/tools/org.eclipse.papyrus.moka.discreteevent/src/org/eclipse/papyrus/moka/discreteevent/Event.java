package org.eclipse.papyrus.moka.discreteevent;

import org.eclipse.papyrus.moka.discreteevent.actions.Action;

public class Event {
	
	protected double relativeDate ;
	protected double absoluteDate ;
	protected Action action ;
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		this.action.setEvent(this);
	}
	protected DEScheduler scheduler ;
	
	public DEScheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(DEScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Event(double relativeDate, Action action) {
		this.relativeDate = relativeDate;
		this.action = action;
	}
	
	public double getRelativeDate() {
		return relativeDate;
	}
	public void setRelativeDate(double relativeDate) {
		this.relativeDate = relativeDate;
	}
	public double getAbsoluteDate() {
		return absoluteDate;
	}
	public void setAbsoluteDate(double absoluteDate) {
		this.absoluteDate = absoluteDate;
	}
	
}
