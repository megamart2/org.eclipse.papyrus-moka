package org.eclipse.papyrus.moka.service;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IReference;

public interface IMokaStepListener {
	
	public void stepStart(IReference context);
	
	public void stepEnd(IReference context);
	
}
