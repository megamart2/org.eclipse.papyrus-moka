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
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphCoordinator;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

public class XYGraphResourceSetListener extends ResourceSetListenerImpl {
	
	private HashMap<XYGraphDescriptor, XYGraphCoordinator> coords = new HashMap<>();
	
	public XYGraphResourceSetListener() {

	}
	
	@Override
	public void resourceSetChanged(ResourceSetChangeEvent event) {
		for( Notification n : event.getNotifications() ){
			if( !(n.getNotifier() instanceof XYGraphDescriptor) )
				continue;
			
			XYGraphCoordinator coord = coords.get(n.getNotifier());
			
			if( coord == null )
				continue;
				
			coord.onModelUpdate(n);
		}
	}

	public void registerCoordinator(XYGraphCoordinator coord) {
		coords.put(coord.getXYGraphDescriptor(), coord);
	}
	
	@Override
	public boolean isPostcommitOnly() {
		return super.isPostcommitOnly();
	}
}
