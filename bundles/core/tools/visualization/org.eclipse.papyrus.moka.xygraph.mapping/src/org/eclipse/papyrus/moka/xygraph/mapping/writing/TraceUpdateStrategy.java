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
package org.eclipse.papyrus.moka.xygraph.mapping.writing;

import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.papyrus.moka.xygraph.model.reflection.TraceStructuralFeature;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphBinder;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphMappingHelper;

public interface TraceUpdateStrategy {
	
	public void commitUpdate();
	
	public void updateTraceDescriptor(Trace trace, XYGraphBinder map);
	
	public <T> void  updateTraceFeature( TraceDescriptor tDesc, TraceStructuralFeature field, T newValue);
	
	public static void updateAllFeatures(TraceUpdateStrategy strategy, Trace trace, XYGraphBinder map){
		TraceDescriptor tDesc = map.getDescriptorFor(trace);
		
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.AreaAlpha, trace.getAreaAlpha());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.BaseLine,XYGraphMappingHelper.descTraceBaseLine(trace.getBaseLine()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.ErrorBarCapWidth,tDesc.getErrorBarCapWidth());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.ErrorBarColor,XYGraphMappingHelper.mapColor(trace.getErrorBarColor()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.LineWidth,trace.getLineWidth());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.PointSize,trace.getPointSize());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.PointStyle,XYGraphMappingHelper.descPointStyle(trace.getPointStyle()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.TraceColor,XYGraphMappingHelper.mapColor(trace.getTraceColor()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.TraceType,XYGraphMappingHelper.descTraceType(trace.getTraceType()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.XErrorBarType,XYGraphMappingHelper.descErrorBarType(trace.getXErrorBarType()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.YErrorBarType,XYGraphMappingHelper.descErrorBarType(trace.getYErrorBarType()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.AntiAliasing,trace.isAntiAliasing());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.DrawYErrorInArea,trace.isDrawYErrorInArea());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.ErrorBarEnabled,trace.isErrorBarEnabled());
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.Name,trace.getName());
		
		//Only 
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.XAxis,map.getDescriptorFor(trace.getXAxis()));
		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.YAxis,map.getDescriptorFor(trace.getYAxis()));
		
		//TODO This doesn't work like this ! :)
		//strategy.updateTraceFeature(tDesc, TraceStructuralFeature.Visible,trace.isVisible());
	}
}
