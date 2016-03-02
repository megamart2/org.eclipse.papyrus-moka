package org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;

public interface IActivityExecution extends IExecution {

	public void setGroup(IActivityNodeActivationGroup group);
	
	public IActivityNodeActivationGroup getGroup();
	
}
