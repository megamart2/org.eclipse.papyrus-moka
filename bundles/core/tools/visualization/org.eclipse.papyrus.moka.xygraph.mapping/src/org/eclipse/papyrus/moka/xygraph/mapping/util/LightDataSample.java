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

import org.eclipse.nebula.visualization.xygraph.dataprovider.ISample;

public class LightDataSample implements ISample {
	
	private static String VOID_INFO = "";
	
	private LightDataProvider prov;
	private int index;
	
	public LightDataSample(LightDataProvider lightDataProvider, int index) {
		this.prov = lightDataProvider;
		this.index = index;
	}

	@Override
	public double getXValue() {
		return prov.getXValue(index);
	}

	@Override
	public double getYValue() {
		return prov.getYValue(index);
	}

	@Override
	public double getXPlusError() {
		return 0;
	}

	@Override
	public double getYPlusError() {
		return 0;
	}

	@Override
	public double getXMinusError() {
		return 0;
	}

	@Override
	public double getYMinusError() {
		return 0;
	}

	@Override
	public String getInfo() {
		return VOID_INFO;
	}
}
