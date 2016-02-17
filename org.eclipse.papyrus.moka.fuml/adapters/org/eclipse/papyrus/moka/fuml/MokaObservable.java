/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.services.IMokaService;

public abstract class MokaObservable {
	
	protected List<IMokaService> listeners;
	
	public MokaObservable(){
		this.listeners = new ArrayList<IMokaService>();
	}
	
	public void addListener(IMokaService service){
		this.listeners.add(service);
	}
	
	public void removeListener(IMokaService service){
		this.listeners.remove(service);
	}
}
