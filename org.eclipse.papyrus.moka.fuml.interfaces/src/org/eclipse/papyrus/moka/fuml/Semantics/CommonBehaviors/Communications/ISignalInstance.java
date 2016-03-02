package org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ICompoundValue;

import org.eclipse.uml2.uml.Signal;

public interface ISignalInstance extends ICompoundValue {

	public void setType(Signal type);
	
	public Signal getType();
}
