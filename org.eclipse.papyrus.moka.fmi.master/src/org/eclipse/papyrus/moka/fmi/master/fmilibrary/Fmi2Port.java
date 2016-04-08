package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Stereotype;

public class Fmi2Port extends Fmi2ScalarVariable {
	private Port port;
	
	public Fmi2Port(Fmu2ProxyService fmu, Port p, Stereotype st) {
		// TODO Auto-generated constructor stub
		super(fmu,p,st);
		this.port = p;		
	}
	public Port getPort() {
		return port;
	}
	public void setPort(Port port) {
		this.port = port;
	}

}
