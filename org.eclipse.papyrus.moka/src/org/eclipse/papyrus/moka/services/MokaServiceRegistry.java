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

package org.eclipse.papyrus.moka.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class MokaServiceRegistry{

	protected static String MOKA_SERVICE_EXTENSION_POIN_ID = "org.eclipse.papyrus.moka.service";
	
	protected HashMap<java.lang.Class<?>, IMokaService> registry; 
	
	private static MokaServiceRegistry INSTANCE;
	
	private MokaServiceRegistry(){
		this.registry = new HashMap<java.lang.Class<?>, IMokaService>();
	}
	
	public static MokaServiceRegistry getInstance(){
		if(INSTANCE==null){
			INSTANCE = new MokaServiceRegistry();
			INSTANCE.loadServices();
		}
		return INSTANCE;
	}
	
	protected void loadServices(){
		// Instantiate all services known as being Moka services 
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurations = registry.getConfigurationElementsFor(MOKA_SERVICE_EXTENSION_POIN_ID);
		for(int i=0; i < configurations.length; i++){
			Object instantiatedContribution = null;
			try {
				instantiatedContribution = configurations[i].createExecutableExtension("class");
			} catch (CoreException e) {
				e.printStackTrace();
			}
			if(instantiatedContribution!=null && instantiatedContribution instanceof IMokaService){
				this.register((IMokaService)instantiatedContribution);
			}
		}
	}
	
	protected void register(IMokaService service){
		if(service!=null){
			this.registry.put(service.getClass(), service);
		}
	}
	
	public IMokaService getService(java.lang.Class<?> clazz){
		IMokaService targetService = null;
		Iterator<Class<?>> iterator = this.registry.keySet().iterator();
		while(targetService==null && iterator.hasNext()){
			Class<?> currentClass = iterator.next();
			if(clazz.isAssignableFrom(currentClass)){
				targetService = this.registry.get(currentClass);
			}
		}
		return targetService;
	}
	
	public Collection<IMokaService> getServices(){
		return this.registry.values();
	}
}
