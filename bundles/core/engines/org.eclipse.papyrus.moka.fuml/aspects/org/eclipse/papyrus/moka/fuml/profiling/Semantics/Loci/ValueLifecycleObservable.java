/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrus.moka.fuml.profiling.Semantics.Loci;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.profiling.MokaObservable;
import org.eclipse.papyrus.moka.service.IMokaExecutionListener;

public class ValueLifecycleObservable extends MokaObservable{

	public void fireValueCreated(final IValue value){
		for(int i=0; i < this.listeners.size(); i++){
			IMokaExecutionListener listener = this.listeners.get(i); 
			listener.valueCreated(value);			
		}
	}
	
	public void fireValueDestroyed(final IValue value){
		for(int i=0; i < this.listeners.size(); i++){
			IMokaExecutionListener listener = this.listeners.get(i); 
			listener.valueDestroyed(value);
		}
	} 
	
}
