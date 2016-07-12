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
package org.eclipse.papyrus.moka.datavisualization.service;

import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphBinder;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphCoordinator;
import org.eclipse.papyrus.moka.xygraph.mapping.util.DataBatch;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.ModelWritingStrategyFactory;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;

public class DataSeriesXYGraphCoordinator extends XYGraphCoordinator implements DataPort {

	public DataSeriesXYGraphCoordinator(XYGraphBinder binder, ModelWritingStrategyFactory factory) {
		super(binder, factory);
	}

	@Override
	public void receiveNewValue(String variable, Double x, Double y) {
		TraceDescriptor tDesc = getTraceForVariable(variable);
		
		if( tDesc == null )
			return;
		
		graphMap.addTraceSample(tDesc, x, y);
	}

	@Override
	public void receiveNewBatch(String variable, DataBatch x, DataBatch y) {
		TraceDescriptor tDesc = getTraceForVariable(variable);
		
		if( tDesc == null )
			return;
		
		graphMap.addTraceBatch(tDesc, x, y);
	}

	private TraceDescriptor getTraceForVariable(String variable){
		for( TraceDescriptor trace : this.graphMap.getTraceDescriptors() ) {
			if( trace.getName().equalsIgnoreCase(variable) )
				return trace;
		}
		
		return null;
	}

	@Override
	protected void onXYGraphBuilt() {
		super.onXYGraphBuilt();
		
//		XYGraphDescriptorAdapter adapter = new XYGraphDescriptorAdapter();
//		this.graphMap.getXYGraphDescriptor().eAdapters().add(adapter);
//		
//		for( TraceDescriptor tDesc : this.graphMap.getXYGraphDescriptor().getTraceDescriptors())
//			tDesc.eAdapters().add(adapter);
		
		//DataValueSetGraphPopulator populator = new DataValueSetGraphPopulator();
		//populator.populate(this, getXYGraphDescriptor());
		
		synchronizeTracesVisibility();
	}

	@Override
	public void synchronizeTracesVisibility() {
		super.synchronizeTracesVisibility();
		
		DataValueSetGraphPopulator populator = new DataValueSetGraphPopulator();
		populator.populate(this, getXYGraphDescriptor());
	}
	
	
}
