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

import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

public interface XYGraphListener {
	void onXYGraphUpdated( XYGraphCoordinator coordinator, XYGraphDescriptor xyGraph );
	void onXYGraphBuilt( XYGraphCoordinator coordinator, XYGraphDescriptor xyGraph );
}
