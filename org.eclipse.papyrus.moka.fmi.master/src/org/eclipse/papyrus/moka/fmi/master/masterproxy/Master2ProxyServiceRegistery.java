package org.eclipse.papyrus.moka.fmi.master.masterproxy;

import org.eclipse.papyrus.moka.fuml.registry.service.framework.AbstractServicesRegistery;

public class Master2ProxyServiceRegistery extends AbstractServicesRegistery{

	public Master2ProxyServiceRegistery(){
		super();
	}

	@Override
	public void initServiceQualifiedNames() {
		this.serviceQualifiedNames.add(Master2ProxyServiceFactory.SERVICE_NAME);
	}

	@Override
	public void initServiceFactory() {
		this.serviceFactory = new Master2ProxyServiceFactory();
		
	}

	@Override
	public void initLibraryName() {
		this.libraryName = "Fmi2Master";
	}
	
}
