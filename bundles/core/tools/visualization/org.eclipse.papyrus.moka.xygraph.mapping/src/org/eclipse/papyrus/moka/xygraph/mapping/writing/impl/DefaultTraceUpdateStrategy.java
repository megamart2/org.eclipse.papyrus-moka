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
import org.eclipse.papyrus.moka.xygraph.model.reflection.TraceStructuralFeature;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphBinder;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.TraceUpdateStrategy;

public class DefaultTraceUpdateStrategy implements TraceUpdateStrategy{

	@Override
	public void updateTraceDescriptor(Trace trace, XYGraphBinder map) {
//		TraceDescriptor tDesc = map.getDescriptorFor(trace);
//		
//		tDesc.setAreaAlpha(trace.getAreaAlpha());
//		tDesc.setBaseLine(XYGraphMappingHelper.descTraceBaseLine(trace.getBaseLine()));
//		tDesc.setErrorBarCapWidth(tDesc.getErrorBarCapWidth());
//		tDesc.setErrorBarColor(XYGraphMappingHelper.mapColor(trace.getErrorBarColor()));
//		tDesc.setLineWidth(trace.getLineWidth());
//		tDesc.setPointSize(trace.getPointSize());
//		tDesc.setPointStyle(XYGraphMappingHelper.descPointStyle(trace.getPointStyle()));
//		tDesc.setTraceColor(XYGraphMappingHelper.mapColor(trace.getTraceColor()));
//		tDesc.setTraceType(XYGraphMappingHelper.descTraceType(trace.getTraceType()));
//		tDesc.setXErrorBarType(XYGraphMappingHelper.descErrorBarType(trace.getXErrorBarType()));
//		tDesc.setYErrorBarType(XYGraphMappingHelper.descErrorBarType(trace.getYErrorBarType()));
//		tDesc.setAntiAliasing(trace.isAntiAliasing());
//		tDesc.setDrawYErrorInArea(trace.isDrawYErrorInArea());
//		tDesc.setErrorBarEnabled(trace.isErrorBarEnabled());
//		tDesc.setName(trace.getName());
//		
//		//Only 
//		tDesc.setXAxis(map.getDescriptorFor(trace.getXAxis()));
//		tDesc.setYAxis(map.getDescriptorFor(trace.getYAxis()));
		TraceUpdateStrategy.updateAllFeatures(this, trace, map);
	}

	@Override
	public <T> void updateTraceFeature(TraceDescriptor tDesc, TraceStructuralFeature field, T newValue) {
		tDesc.eSet(field.getEStructuralFeature(), newValue);
	}

	@Override
	public void commitUpdate() {
		
	}

	

}
