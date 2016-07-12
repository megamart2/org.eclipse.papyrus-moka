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
package org.eclipse.papyrus.moka.xygraph.mapping.util;

import java.util.List;

import org.eclipse.nebula.visualization.xygraph.dataprovider.AbstractDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.ISample;
import org.eclipse.nebula.visualization.xygraph.dataprovider.Sample;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;

public class BatchedCircularDataProvider extends AbstractDataProvider{
	
	private ISample[] buffer = {};
	private boolean dataRangedirty; 
	
	public BatchedCircularDataProvider(boolean chronological) {
		super(chronological);
	}

	public void setDataList(List<Double> xl, List<Double> yl){
		
		if( xl.size() == 0 )
			return ;
			
		buffer = new Sample[xl.size()];
		
		double xMin = Float.POSITIVE_INFINITY, xMax = Float.NEGATIVE_INFINITY, 
			   yMin = Float.POSITIVE_INFINITY, yMax = Float.NEGATIVE_INFINITY;
		
		for( int i = 0; i < buffer.length; i++ ){
			
			double x = xl.get(i);
			double y = yl.get(i);
			
			xMin = Math.min(xMin, x);
			xMax = Math.max(xMax, x);
			yMin = Math.min(yMin, y);
			yMax = Math.max(yMax, y);
			
			buffer[i] = new Sample( x, y );
			
		}
		xDataMinMax = new Range(xMin, yMax);
		yDataMinMax = new Range(yMin, yMax);
		
		fireDataChange();
		
		dataRangedirty = false;
	}

	@Override
	public int getSize() {
		return buffer.length;
	}

	@Override
	public ISample getSample(int index) {
		return buffer[index];
	}
	
	@Override
	protected void innerUpdate() {
		dataRangedirty = true;
	}

	@Override
	protected void updateDataRange() {
		if (!dataRangedirty)
			return;
		dataRangedirty = false;
		if (getSize() > 0) {
			int lowerBound = 0;
			
			double xMin;
			double xMax;
			xMin = getSample(lowerBound).getXValue();
			xMax = xMin;

			double yMin;
			double yMax;
			yMin = getSample(lowerBound).getYValue();
			yMax = yMin;
			for (int i = lowerBound + 1; i < getSize(); i++) {
				ISample dp = getSample(i);
				if (xMin > dp.getXValue() - dp.getXMinusError())
					xMin = dp.getXValue() - dp.getXMinusError();
				if (xMax < dp.getXValue() + dp.getXPlusError())
					xMax = dp.getXValue() + dp.getXPlusError();

				if (yMin > dp.getYValue() - dp.getYMinusError())
					yMin = dp.getYValue() - dp.getYMinusError();
				if (yMax < dp.getYValue() + dp.getYPlusError())
					yMax = dp.getYValue() + dp.getYPlusError();
			}

			xDataMinMax = new Range(xMin, xMax);
			yDataMinMax = new Range(yMin, yMax);
		} else {
			xDataMinMax = null;
			yDataMinMax = null;
		}
	}
}
