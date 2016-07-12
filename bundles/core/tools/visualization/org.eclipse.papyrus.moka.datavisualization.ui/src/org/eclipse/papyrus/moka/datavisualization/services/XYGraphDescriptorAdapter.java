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
package org.eclipse.papyrus.moka.datavisualization.services;

import java.util.HashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphCoordinator;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

@Deprecated
public class XYGraphDescriptorAdapter extends AdapterImpl {
	private HashMap<XYGraphDescriptor, XYGraphCoordinator> coords = new HashMap<>();
	
	@Override
	public void notifyChanged(Notification notification) {
		if ( !( notification.getNotifier() instanceof XYGraphDescriptor ) )
			return;
		
		XYGraphCoordinator coord = coords.get(notification.getNotifier());
		
		if( coord == null )
			return;
		
		coord.onModelUpdate( notification );
	}

	public void addCoordinator(XYGraphCoordinator coord) {
		coords.put(coord.getXYGraphDescriptor(), coord);
	}

//	@Override
//	public boolean isAdapterForType(Object type) {
//		return (type instanceof XYGraphDescriptor);
//	}

}
