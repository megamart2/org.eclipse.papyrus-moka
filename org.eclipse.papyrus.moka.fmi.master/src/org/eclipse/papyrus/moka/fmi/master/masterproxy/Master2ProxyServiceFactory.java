package org.eclipse.papyrus.moka.fmi.master.masterproxy;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.registry.service.framework.AbstractServiceFactory;
import org.eclipse.uml2.uml.Class;

public class Master2ProxyServiceFactory extends AbstractServiceFactory{


	protected static final String SERVICE_NAME = "Master2Service::Master2Proxy"; // qualified name of FmuProxy

	public Master2ProxyServiceFactory(){
	}

	@Override
	public IObject_ instantiate(Class service) {
		IObject_ serviceInstance = null;
		if (service.getQualifiedName().equals(SERVICE_NAME)){
			serviceInstance = this.createMaster2Service(service); 
		}
		return serviceInstance;
	}

	public IObject_ createMaster2Service(Class service){
		return new Master2ProxyService(service);
	}
}
