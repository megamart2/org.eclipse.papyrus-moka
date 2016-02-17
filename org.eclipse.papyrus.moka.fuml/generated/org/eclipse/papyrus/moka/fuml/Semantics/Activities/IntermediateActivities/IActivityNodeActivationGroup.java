/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.CompleteStructuredActivities.IStructuredActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityParameterNodeActivation;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

public interface IActivityNodeActivationGroup {

	public void run(List<IActivityNodeActivation> activations);
	
	public Boolean checkIncomingEdges(List<IActivityEdgeInstance> incomingEdges, List<IActivityNodeActivation> activations);
	
	public void runNodes(List<ActivityNode> nodes);
	
	public void activate(List<ActivityNode> nodes, List<ActivityEdge> edges);
	
	public void terminateAll();
	
	public void createNodeActivations(List<ActivityNode> nodes);
	
	public IActivityNodeActivation createNodeActivation(ActivityNode node);
	
	public IActivityNodeActivation getNodeActivation(ActivityNode node);
	
	public void createEdgeInstances(List<ActivityEdge> edges);
	
	public ActivityExecution getActivityExecution();
	
	public List<ActivityParameterNodeActivation> getOutputParameterNodeActivations();
	
	public Boolean hasSourceFor(IActivityEdgeInstance edgeInstance);
	
	public Boolean isSuspended();
	
	public void suspend(IActivityNodeActivation activation);
	
	public void resume(IActivityNodeActivation activation);
	
	public void setContainingActivation(IStructuredActivityNodeActivation activation);
	
	public IStructuredActivityNodeActivation getContainingActivation();
	
	public List<IActivityNodeActivation> getActivityNodeActivations();
}