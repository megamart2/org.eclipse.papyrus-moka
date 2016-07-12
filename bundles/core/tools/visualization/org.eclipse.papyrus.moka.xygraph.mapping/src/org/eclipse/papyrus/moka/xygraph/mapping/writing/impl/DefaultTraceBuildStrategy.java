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
package org.eclipse.papyrus.moka.xygraph.mapping.writing.impl;

import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphBinder;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphMappingHelper;
import org.eclipse.papyrus.moka.xygraph.mapping.util.BatchedCircularDataProvider;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.TraceBuildStrategy;

public class DefaultTraceBuildStrategy implements TraceBuildStrategy {

	@Override
	public void buildTraces(XYGraphBinder map) {
		XYGraphDescriptor gDesc = map.getXYGraphDescriptor();
		for( TraceDescriptor tDesc : gDesc.getTraceDescriptors()){
			map.bindTrace(tDesc, buildTrace(tDesc, map) );
		}
	}

	protected Trace buildTrace(TraceDescriptor tDesc, XYGraphBinder map) {
		
		BatchedCircularDataProvider prov = new BatchedCircularDataProvider(true);
//
//		RandomDataProducer rdp = new RandomDataProducer(4, 5, 100);
//		rdp.sample(prov);

		Trace trace = new Trace(tDesc.getName(), map.getAxisFor(tDesc.getXAxis()),
				map.getAxisFor(tDesc.getYAxis()), prov);

		trace.setAreaAlpha(tDesc.getAreaAlpha());
		trace.setBaseLine(XYGraphMappingHelper.buildTraceBaseLine(tDesc.getBaseLine()));
		trace.setErrorBarCapWidth(tDesc.getErrorBarCapWidth());
		trace.setErrorBarColor(XYGraphMappingHelper.mapColorDescriptor(tDesc.getErrorBarColor()));
		trace.setLineWidth(tDesc.getLineWidth());
		trace.setPointSize(tDesc.getPointSize());
		trace.setPointStyle(XYGraphMappingHelper.buildPointStyle(tDesc.getPointStyle()));
		trace.setTraceColor(XYGraphMappingHelper.mapColorDescriptor(tDesc.getTraceColor()));
		trace.setTraceType(XYGraphMappingHelper.buildTraceType(tDesc.getTraceType()));
		trace.setXErrorBarType(XYGraphMappingHelper.buildErrorBarType(tDesc.getXErrorBarType()));
		trace.setYErrorBarType(XYGraphMappingHelper.buildErrorBarType(tDesc.getYErrorBarType()));

		trace.setAntiAliasing(tDesc.isAntiAliasing());
		trace.setDrawYErrorInArea(tDesc.isDrawYErrorInArea());
		trace.setErrorBarEnabled(tDesc.isErrorBarEnabled());

		return trace;
		
	}

}
