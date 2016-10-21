package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;

public interface IDoActivityContextObject extends IObject_{

	public void initialize(IObject_ context);
	
	public IObject_ getContext();
}
