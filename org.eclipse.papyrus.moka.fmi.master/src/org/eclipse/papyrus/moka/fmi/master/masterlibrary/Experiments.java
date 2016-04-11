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
package org.eclipse.papyrus.moka.fmi.master.masterlibrary;

import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_Object;
import org.eclipse.uml2.uml.Stereotype;

public class Experiments {
	private double stepSize = 0.1;
	private double startTime = 0.0;
	private double stopTime = 100.0;
	private double tolerance = 0.0;
	private boolean toleranceDefined = true;
	private boolean stopTimeDefined = true;
	private double current_time = 0.0;
	private boolean computeExperimentsFromFmu = false;

	public Experiments(ICS_Object container) {
		// TODO Auto-generated method stub
		this.current_time = 0;
		Stereotype cs_graphStereoptype = container.getTypes().get(0).getAppliedStereotype("FmiMLProfile::CS_Graph");
		this.startTime = (Double) container.getTypes().get(0).getValue(cs_graphStereoptype, "startTime");
		this.stopTime = (Double) container.getTypes().get(0).getValue(cs_graphStereoptype, "stopTime");
		this.stepSize = (Double) container.getTypes().get(0).getValue(cs_graphStereoptype, "stepSize");
		this.tolerance = (Double) container.getTypes().get(0).getValue(cs_graphStereoptype, "tolerance");

	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getStopTime() {
		return stopTime;
	}

	public void setStopTime(double stopTime) {
		this.stopTime = stopTime;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public boolean isToleranceDefined() {
		return toleranceDefined;
	}

	public void setToleranceDefined(boolean toleranceDefined) {
		this.toleranceDefined = toleranceDefined;
	}

	public boolean isStopTimeDefined() {
		return stopTimeDefined;
	}

	public void setStopTimeDefined(boolean stopTimeDefined) {
		this.stopTimeDefined = stopTimeDefined;
	}

	public boolean isComputeExperimentsFromFmu() {
		return computeExperimentsFromFmu;
	}

	public void setComputeExperimentsFromFmu(boolean computeExperimentsFromFmu) {
		this.computeExperimentsFromFmu = computeExperimentsFromFmu;
	}

	public double getCurrent_time() {
		return current_time;
	}

	public void setCurrent_time(double current_time) {
		this.current_time = current_time;
	}




}
