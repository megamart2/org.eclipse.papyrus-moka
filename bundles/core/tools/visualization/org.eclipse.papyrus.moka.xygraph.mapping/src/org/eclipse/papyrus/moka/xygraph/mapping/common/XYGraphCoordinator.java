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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.nebula.visualization.xygraph.figures.Axis;
import org.eclipse.nebula.visualization.xygraph.figures.IXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.ModelWritingStrategyFactory;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.AxisDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphPackage;

public class XYGraphCoordinator{
	
	protected XYGraphBinder graphMap;
	private ModelWritingStrategyFactory factory;
	private List<XYGraphListener> listeners = new ArrayList<XYGraphListener>();
	
	
	public XYGraphCoordinator(XYGraphBinder binder, ModelWritingStrategyFactory factory){
		this.graphMap = binder;
		this.factory = factory;
	}
	
	public void updateDescriptors(XYGraph xy){
		factory.getXYGraphUpdateStrategy().updateXYGraph(xy, graphMap);
		
		for( Axis axis : graphMap.getAxes())
			factory.getAxisUpdateStrategy().updateAxisDescriptor(axis, graphMap);
		
		for( Trace trace: graphMap.getTraces() )
			factory.getTraceUpdateStrategy().updateTraceDescriptor(trace, graphMap);
		
		onXYGraphUpdated();
	}
	
	protected void onXYGraphUpdated() {
		fireXYGraphUpdated(graphMap.getXYGraphDescriptor());
	}

	public IXYGraph buildXYGraph(){
		//1. Axes first (Primary axes are required for the xyGraph)
		factory.getAxisBuildStrategy().buildAxes(graphMap);
		
		//2. Build the xyGraph 
		IXYGraph xy = factory.getXYGraphBuildStrategy().buildGraph(graphMap);		
		XYGraphDescriptor gDesc = graphMap.getXYGraphDescriptor(); 
		
		//2.1. The axes need the graph before the traces.
		for( AxisDescriptor aDesc : gDesc.getAxisDescriptors() ){
			if( graphMap.isPrimary(aDesc) )
				continue;
			
			xy.addAxis(graphMap.getAxisFor(aDesc));
		}
		
		//3. Build and add the traces
		factory.getTraceBuildStrategy().buildTraces(graphMap);
		
		for( TraceDescriptor tDesc : gDesc.getTraceDescriptors() )
			xy.addTrace(graphMap.getTraceFor(tDesc));
		
		onXYGraphBuilt();
		
		return xy;
	}
	
	protected void onXYGraphBuilt(){
		fireXYGraphBuilt(graphMap.getXYGraphDescriptor());
	}
	
	public XYGraphDescriptor getXYGraphDescriptor(){
		return graphMap.getXYGraphDescriptor();
	}
	
	public void addXYGraphListener( XYGraphListener listener ){
		listeners.add(listener);
	}
	
	protected void fireXYGraphUpdated(XYGraphDescriptor xyGraph){
		for( XYGraphListener listener : listeners )
			listener.onXYGraphUpdated(this, xyGraph);
	}
	
	protected void fireXYGraphBuilt(XYGraphDescriptor xyGraph) {
		for( XYGraphListener listener : listeners )
			listener.onXYGraphBuilt(this, xyGraph);
	}
	
	public void synchronizeTracesVisibility(){
		XYGraphDescriptor xyDesc = graphMap.getXYGraphDescriptor();
		List<TraceDescriptor> visibles = xyDesc.getVisibleTraces();
		
		for( TraceDescriptor tDesc : graphMap.getTraceDescriptors() ){
			if( visibles.contains(tDesc) )
				showTrace(graphMap.getXYGraph(), graphMap.getTraceFor(tDesc));
			else
				hideTrace(graphMap.getXYGraph(), graphMap.getTraceFor(tDesc));
		}
	}
	
//	public void setTraceVisibility(TraceDescriptor tDesc, boolean visibility){
//		
//		if( tDesc.isVisible() == visibility )
//			return;//No need to change, add, remove ...etc.
//		
//		TraceUpdateStrategy strategy = factory.getTraceUpdateStrategy();
//		strategy.updateTraceFeature(tDesc, TraceStructuralFeature.Visible, visibility);
//		strategy.commitUpdate();
//		
//		XYGraph xyGraph = graphMap.getXYGraph();
//		Trace trace = graphMap.getTraceFor(tDesc);
//		
//		if( visibility )
//			showTrace(xyGraph , trace);
//		else
//			hideTrace(xyGraph, trace);
//	}

	private void hideTrace(XYGraph graph, Trace trace){
		//Only remove it if contained			
		if( graph.getPlotArea().getChildren().contains(trace) ){ 
			graph.removeTrace(trace);
		}
		
	}
	
	private void showTrace(XYGraph graph, Trace trace){
		//Only add it if not contained
		if( !graph.getPlotArea().getChildren().contains(trace) ){ 
			graph.addTrace(trace);
		}
	}

	public void onModelUpdate(Notification notification) {
		if( XYGraphPackage.eINSTANCE.getXYGraphDescriptor_VisibleTraces().equals(notification.getFeature()) ){
//			if( Notification.ADD == notification.getEventType() )
//				System.out.println("Adding a visible trace");
//			else if ( Notification.REMOVE == notification.getEventType() ){
//				System.out.println("Removing a visible trace");
//			}else if( Notification.ADD_MANY == notification.getEventType() ){
//				System.out.println("Adding many !");
//			}
			synchronizeTracesVisibility();
		}
		
		//System.out.println("Changed !");
	}
	
//	private XYGraphDataCollector collector = new XYGraphDataCollector() {
//		
//		@Override
//		public void collectValue(TraceDescriptor tDesc, double x, double y) {
//			Trace trace = graphMap.getTraceFor(tDesc);
//			CircularBufferDataProvider provider = (CircularBufferDataProvider)trace.getDataProvider();
//			provider.addSample(new Sample(x, y));
//		}
//	};
//	
//	protected void addXYGraphDataProducer(XYGraphDataProducer producer){
//		producer.init(collector);
//	}
	
//	private XYGraphDataListener dataListener = new XYGraphDataListener() {
//
//		@Override
//		public void onDataSampleAdded(TraceDescriptor tDesc, double x, double y) {
//			Trace trace = graphMap.getTraceFor(tDesc);
//			CircularBufferDataProvider provider = (CircularBufferDataProvider)trace.getDataProvider();
//			provider.addSample(new Sample(x, y));
//		}
//	};
//	
//	public void addXYGraphDataProducer(XYGraphDataProducer producer){
//		producer.setXYGraphDataListener(dataListener);
//	}
}
