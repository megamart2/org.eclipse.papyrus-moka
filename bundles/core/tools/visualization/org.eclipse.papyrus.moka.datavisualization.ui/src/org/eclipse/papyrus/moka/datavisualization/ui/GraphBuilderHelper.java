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
package org.eclipse.papyrus.moka.datavisualization.ui;

import java.util.List;

import org.eclipse.papyrus.moka.xygraph.model.xygraph.AxisDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.ColorDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.FontDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.LinearScale_Orientation;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.Trace_TraceType;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphFactory;
import org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.swt.SWT;

public class GraphBuilderHelper {
	
	final static int[] colors[] = { 
			{21, 21, 196}, // blue
			{242, 26, 26}, // red
			{33, 179, 33}, // green
			//{0, 0, 0}, // black
			//{128, 0, 255}, // violett
			{255, 170, 0}, // (darkish) yellow
			{255, 0, 240}, // pink
			{243, 132, 132}, // peachy
			{0, 255, 11}, // neon green
			//{0, 214, 255}, // neon blue
			{114, 40, 3}, // brown
			{219, 128, 4}, // orange
	};
	
	final static Trace_TraceType[] types = {
			Trace_TraceType.SOLID_LINE,
			Trace_TraceType.DASHDOT_LINE,
			Trace_TraceType.DOT_LINE
	};
	
	public static XYGraphDescriptor buildFromDataValueSet(DataValueSet set){
		XYGraphDescriptor xy = XYGraphFactory.eINSTANCE.createXYGraphDescriptor();
		xy.setTitle("CSV-Graph");
		xy.setTitleFont(makeFontDescriptor("Segoe UI", 16, SWT.BOLD));
		xy.setPlotAreaBackgroundColor(makeColor(255, 255, 255));
		
		AxisDescriptor xAxis = XYGraphFactory.eINSTANCE.createAxisDescriptor();
		xAxis.setOrientation(LinearScale_Orientation.HORIZONTAL);
		xAxis.setTitle("Time");		
		xAxis.setFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		xAxis.setTitleFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		
		xy.getAxisDescriptors().add(xAxis);

		AxisDescriptor yAxis = XYGraphFactory.eINSTANCE.createAxisDescriptor();
		yAxis.setOrientation(LinearScale_Orientation.VERTICAL);
		yAxis.setTitle("Values");
		yAxis.setFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		yAxis.setTitleFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		xy.getAxisDescriptors().add(yAxis);

		
		int added = 0;
		for(ValueSeries serie : set.getSeries()){
			if( "time".equalsIgnoreCase(serie.getVariableName()) )
				continue;
			
			TraceDescriptor t1 = XYGraphFactory.eINSTANCE.createTraceDescriptor();
			t1.setXAxis(xAxis);
			t1.setYAxis(yAxis);
			t1.setName(serie.getVariableName());
			//t1.setPointStyle(Trace_PointStyle.CIRCLE);
			//t1.setPointSize(6);
			t1.setTraceColor(makeColor(colors[added % colors.length][0], colors[added % colors.length][1], colors[added % colors.length][2]));
			t1.setTraceType(types[added / colors.length]);
			xy.getTraceDescriptors().add(t1);
			
			added ++;
			
//			if( added >= count )
//				break;
			
//			TraceDescriptor t2 = XYGraphFactory.eINSTANCE.createTraceDescriptor();
//			t2.setXAxis(xAxis);
//			t2.setYAxis(yAxis);
//			t2.setName("Data 2");
//			t2.setPointStyle(Trace_PointStyle.XCROSS);
//			t2.setTraceType(Trace_TraceType.BAR);
//			t2.setLineWidth(10);
//			t2.setTraceColor(makeColor(21, 21, 196));
//			xy.getTraceDescriptors().add(t2);
		
		}
		return xy;
	}
	
	private static ColorDescriptor makeColor(int r, int g, int b) {
		ColorDescriptor color = XYGraphFactory.eINSTANCE.createColorDescriptor();
		
		color.setR(r);
		color.setG(g);
		color.setB(b);
		
		return color;
	}

	private static FontDescriptor makeFontDescriptor(String name, int size, int style){
		FontDescriptor fd = XYGraphFactory.eINSTANCE.createFontDescriptor();
		fd.setName(name);
		fd.setSize(size);
		fd.setStyle(style);
		
		return fd;
	}

	public static XYGraphDescriptor buildFromSeries(List<ValueSeries> series, List<ValueSeries> visibles) {
		XYGraphDescriptor xy = XYGraphFactory.eINSTANCE.createXYGraphDescriptor();
		xy.setTitle("CSV-Graph");
		xy.setTitleFont(makeFontDescriptor("Segoe UI", 16, SWT.BOLD));
		xy.setPlotAreaBackgroundColor(makeColor(255, 255, 255));
		
		AxisDescriptor xAxis = XYGraphFactory.eINSTANCE.createAxisDescriptor();
		xAxis.setOrientation(LinearScale_Orientation.HORIZONTAL);
		xAxis.setTitle("Time");		
		xAxis.setFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		xAxis.setTitleFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		
		xy.getAxisDescriptors().add(xAxis);

		AxisDescriptor yAxis = XYGraphFactory.eINSTANCE.createAxisDescriptor();
		yAxis.setOrientation(LinearScale_Orientation.VERTICAL);
		yAxis.setTitle("Values");
		yAxis.setFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		yAxis.setTitleFont(makeFontDescriptor("Segoe UI", 12, SWT.NORMAL));
		xy.getAxisDescriptors().add(yAxis);

		
		int added = 0;
		for(ValueSeries serie : series){
			if( "time".equalsIgnoreCase(serie.getVariableName()) )
				continue;
			
			TraceDescriptor t1 = XYGraphFactory.eINSTANCE.createTraceDescriptor();
			t1.setXAxis(xAxis);
			t1.setYAxis(yAxis);
			t1.setName(serie.getVariableName());
			//t1.setPointStyle(Trace_PointStyle.CIRCLE);
			//t1.setPointSize(6);
			t1.setTraceColor(makeColor(colors[added % colors.length][0], colors[added % colors.length][1], colors[added % colors.length][2]));
			t1.setTraceType(types[added / colors.length]);
			xy.getTraceDescriptors().add(t1);
			
			//Add the visible series
			if( visibles.contains(serie) )
				xy.getVisibleTraces().add(t1);
			
			added ++;
			
//			if( added >= count )
//				break;
			
//			TraceDescriptor t2 = XYGraphFactory.eINSTANCE.createTraceDescriptor();
//			t2.setXAxis(xAxis);
//			t2.setYAxis(yAxis);
//			t2.setName("Data 2");
//			t2.setPointStyle(Trace_PointStyle.XCROSS);
//			t2.setTraceType(Trace_TraceType.BAR);
//			t2.setLineWidth(10);
//			t2.setTraceColor(makeColor(21, 21, 196));
//			xy.getTraceDescriptors().add(t2);
		
		}
		return xy;
	}
}
