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

import org.eclipse.papyrus.moka.xygraph.mapping.writing.AxisBuildStrategy;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.AxisUpdateStrategy;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.ModelWritingStrategyFactory;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.TraceBuildStrategy;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.TraceUpdateStrategy;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.XYGraphBuildStrategy;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.XYGraphUpdateStrategy;

public class DefaultModelWritingStrategyFactory implements ModelWritingStrategyFactory {
	
	@Override
	public AxisUpdateStrategy getAxisUpdateStrategy() {
		return new DefaultAxisUpdateStrategy();
	}

	@Override
	public TraceUpdateStrategy getTraceUpdateStrategy() {
		return new DefaultTraceUpdateStrategy();
	}

	@Override
	public XYGraphUpdateStrategy getXYGraphUpdateStrategy() {
		return new DefaultXYGraphUpdateStrategy();
	}

	@Override
	public AxisBuildStrategy getAxisBuildStrategy() {
		return new DefaultAxisBuildStrategy();
	}

	@Override
	public TraceBuildStrategy getTraceBuildStrategy() {
		return new DefaultTraceBuildStrategy();
	}

	@Override
	public XYGraphBuildStrategy getXYGraphBuildStrategy() {
		return new DefaultXYGraphBuildStrategy();
	}
}
