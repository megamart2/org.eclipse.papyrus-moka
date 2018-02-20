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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.visualization.xygraph.dataprovider.AbstractDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.ISample;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;

public class LightDataProvider extends AbstractDataProvider {
	
	private List<Double> xValues, yValues;
	private List<LightDataSample> samples = new ArrayList<>();
	
	private boolean dataRangedirty; 
	
	//private double xMin, xMax, yMin, yMax;
	private TraceDataBounds bounds;
	
	public LightDataProvider(boolean chronological) {
		super(chronological);
		bounds = new TraceDataBounds();
	}

	public void setDataList(List<Double> xl, List<Double> yl) {
		
		if (xl.size() == 0)
			return;
		
		this.xValues = xl;
		this.yValues = yl;
		
		if (samples.size() != 0)
			samples.clear();

		samples = new ArrayList<LightDataSample>();

		bounds.reset();

		for (int i = 0; i < xl.size(); i++) {

			double x = xl.get(i);
			double y = yl.get(i);

			bounds.addSample(x, y);
			samples.add(new LightDataSample(this, i));
		}
		xDataMinMax = new Range(bounds.getxMin(), bounds.getxMax());
		yDataMinMax = new Range(bounds.getyMin(), bounds.getyMax());

		fireDataChange();

		dataRangedirty = false;
	}

	@Override
	public int getSize() {
		return samples.size();
	}

	@Override
	public ISample getSample(int index) {
		return samples.get(index);
	}
	
	//TODO Careful ! The values are supposed to be already inside the 
	//buffers. Only update the ranges and add the sample.
	//This hasn't been tested
	public void addSample(double x, double y) {

		double newLowerX = Math.min(xDataMinMax.getLower(), x);
		double newUpperX = Math.max(xDataMinMax.getUpper(), x);

		double newLowerY = Math.min(yDataMinMax.getLower(), y);
		double newUpperY = Math.max(yDataMinMax.getUpper(), y);

		if (newLowerX != xDataMinMax.getLower() || newUpperX != xDataMinMax.getUpper())
			xDataMinMax = new Range(newLowerX, newUpperX);

		if (newLowerY != yDataMinMax.getLower() || newUpperY != yDataMinMax.getUpper())
			yDataMinMax = new Range(newLowerY, newUpperY);

		int index = getSize();
		samples.add(new LightDataSample(this, index));
		
		fireDataChange();

		dataRangedirty = false;
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
			xMin = getXValue(lowerBound);//getSample(lowerBound).getXValue();
			xMax = xMin;

			double yMin;
			double yMax;
			yMin = getYValue(lowerBound);//getSample(lowerBound).getYValue();
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

	public double getXValue(int index) {
		return xValues.get(index);
	}

	public double getYValue(int index) {
		return yValues.get(index);
	}

	public void clear() {
		samples.clear();
		xValues = null;
		yValues = null;
	}

	public TraceDataBounds getBounds() {
		return bounds;
	}

}
