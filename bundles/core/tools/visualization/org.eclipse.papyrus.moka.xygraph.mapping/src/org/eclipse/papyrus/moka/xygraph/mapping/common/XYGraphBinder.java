/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.xygraph.mapping.common;

import java.util.Collection;

import org.eclipse.nebula.visualization.xygraph.figures.Axis;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.AxisDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.xygraph.mapping.util.DataBatch;

public interface XYGraphBinder {

	void setGraphDescriptor(XYGraphDescriptor gDesc);
	XYGraphDescriptor getXYGraphDescriptor();

	void bindAxis(AxisDescriptor aDesc, Axis axis);
	void bindTrace(TraceDescriptor tDesc, Trace trace);

	Axis getAxisFor(AxisDescriptor aDesc);
	AxisDescriptor getDescriptorFor(Axis axis);

	Trace getTraceFor(TraceDescriptor tDesc);
	TraceDescriptor getDescriptorFor(Trace trace);

	Axis getXAxisPrimary();
	Axis getYAxisPrimary();

	boolean isPrimary(AxisDescriptor aDesc);
	boolean isPrimary(Axis axis);

	Collection<Axis> getAxes();
	Collection<Trace> getTraces();
	
	Collection<TraceDescriptor> getTraceDescriptors();
	Collection<AxisDescriptor> getAxisDescriptors();
	
	void addTraceSample(TraceDescriptor tDesc, double x, double y);
	void addTraceBatch(TraceDescriptor tDesc, DataBatch x, DataBatch y);
	void bindXYGraph(XYGraph xy);
	XYGraph getXYGraph();
}