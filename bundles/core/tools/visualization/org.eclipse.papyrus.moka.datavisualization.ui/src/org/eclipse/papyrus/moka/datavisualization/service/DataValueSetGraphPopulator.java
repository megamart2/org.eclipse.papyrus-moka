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

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.moka.xygraph.mapping.util.DataBatch;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.TraceDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.datavisualization.profile.BooleanSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.DataSource;
import org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet;
import org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.IntegerSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.VisualizationPackage;

public class DataValueSetGraphPopulator {

	//TODO This method should be replaced with a call to the DataService.
	public void populate(DataPort port, XYGraphDescriptor descriptor) {
		
		DataSource ds = (DataSource) descriptor.getDataSource();
		DataValueSet set = ds.getValueSets().get(0);

		// Get time series;
		EList<Double> times = null;
		
		for (ValueSeries serie : set.getSeries()) {
			if ("time".equalsIgnoreCase(serie.getVariableName())) {
				DoubleSeries doubleSerie = (DoubleSeries) serie;
				times = doubleSerie.getValues();
				break;
			}
		}

		//TODO This is only to avoid a blocking exception. In fact this should've been validated before
		if( times == null )
			return;
		
		DataBatch tBatch = DataBatch.fromDouble(times);
		
		for( TraceDescriptor tDesc : descriptor.getTraceDescriptors() ){
			for (ValueSeries serie : set.getSeries()) {		
				if( tDesc.getName().equalsIgnoreCase(serie.getVariableName()) ){
					//DoubleSeries doubleSerie = (DoubleSeries) serie;
					
					//RawDataBatch vBatch = RawDataBatch.from(doubleSerie.getValues());
					
					DataBatch vBatch = getDataFromValueSeries(serie);
					port.receiveNewBatch(tDesc.getName(), tBatch, vBatch);
				}
			}
		}
		
		//System.out.println("DataValueSetGraphPopulator finished populate");
	}
	
	private DataBatch getDataFromValueSeries(ValueSeries serie){
		
		switch (serie.eClass().getClassifierID()){
			
			case VisualizationPackage.BOOLEAN_SERIES :
				return DataBatch.fromBoolean( ((BooleanSeries) serie).getValues() );
				
			case VisualizationPackage.INTEGER_SERIES :
				return DataBatch.fromInteger( ((IntegerSeries) serie).getValues() );
				
			case VisualizationPackage.DOUBLE_SERIES :
				return DataBatch.fromDouble( ((DoubleSeries) serie).getValues() );
				
				
			default : 
				//TODO This case is not implemented
				return DataBatch.dummy();
		}
	}
}
