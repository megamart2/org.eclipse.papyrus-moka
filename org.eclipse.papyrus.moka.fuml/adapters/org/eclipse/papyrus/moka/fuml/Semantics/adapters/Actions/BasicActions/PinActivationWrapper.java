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

package org.eclipse.papyrus.moka.fuml.Semantics.adapters.Actions.BasicActions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.MokaObservable;
import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.IPinActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityNodeActivationGroup;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityExecution;
import org.eclipse.uml2.uml.ActivityNode;


public class PinActivationWrapper extends MokaObservable implements IPinActivation {

	protected IPinActivation pinActivation;
	
	public PinActivationWrapper(IPinActivation pinActivation){
		this.pinActivation = pinActivation;
	}
	
	public Integer countOfferedValues() {
		return this.pinActivation.countOfferedValues();
	}

	public void sendUnofferedTokens() {
		this.pinActivation.sendUnofferedTokens();
	}

	public Integer countUnofferedTokens() {
		return this.pinActivation.countOfferedValues();
	}
	
	public List<IToken> getUnofferedTokens() {
		return this.pinActivation.getUnofferedTokens();
	}

	public List<IToken> takeUnofferedTokens() {
		return this.pinActivation.takeUnofferedTokens();
	}

	public void run() {
		this.pinActivation.run();
	}

	public void receiveOffer() {
		this.pinActivation.receiveOffer();
	}

	public List<IToken> takeOfferedTokens(){
		return this.pinActivation.takeOfferedTokens();
	}

	public void fire(List<IToken> incomingTokens){
		this.pinActivation.fire(incomingTokens);
	}

	public void sendOffers(List<IToken> tokens) {
		this.pinActivation.sendOffers(tokens);
	}

	public void terminate() {
		this.pinActivation.terminate();
	}

	public Boolean isReady() {
		return this.pinActivation.isReady();
	}

	public Boolean isRunning() {
		return this.pinActivation.isRunning();
	}

	public void addOutgoingEdge(IActivityEdgeInstance edge) {
		this.pinActivation.addOutgoingEdge(edge);
	}

	public void addIncomingEdge(IActivityEdgeInstance edge) {
		this.pinActivation.addIncomingEdge(edge);
	}

	public void createNodeActivations() {
		this.pinActivation.createNodeActivations();
	}

	public void createEdgeInstances() {
		this.pinActivation.createEdgeInstances();
	}
	
	public Boolean isSourceFor(IActivityEdgeInstance edgeInstance) {
		return this.pinActivation.isSourceFor(edgeInstance);
	}

	public ActivityExecution getActivityExecution() {
		return this.pinActivation.getActivityExecution();
	}

	public IObject_ getExecutionContext() {
		return this.pinActivation.getExecutionContext();
	}

	public ILocus getExecutionLocus() {
		return this.pinActivation.getExecutionLocus();
	}

	public IActivityNodeActivation getNodeActivation(ActivityNode node) {
		return this.pinActivation.getNodeActivation(node);
	}

	public void addToken(IToken token) {
		this.pinActivation.addToken(token);
	}

	public Integer removeToken(IToken token) {
		return this.pinActivation.removeToken(token);
	}

	public void addTokens(List<IToken> tokens) {
		this.pinActivation.addTokens(tokens);
	}

	public List<IToken> takeTokens() {
		return this.pinActivation.takeTokens();
	}

	public void clearTokens() {
		this.pinActivation.clearTokens();
	}

	public List<IToken> getTokens() {
		return this.pinActivation.getTokens();
	}

	public void initialize(ActivityNode node, IActivityNodeActivationGroup group) {
		this.pinActivation.initialize(node, group);
	}

	public void suspend() {
		this.pinActivation.suspend();
	}

	public void resume() {
		this.pinActivation.resume();
	}

	public void setNode(ActivityNode node) {
		this.pinActivation.setNode(node);
	}

	public ActivityNode getNode() {
		return this.pinActivation.getNode();
	}

	public void setGroup(IActivityNodeActivationGroup group) {
		this.pinActivation.setGroup(group);
	}

	public IActivityNodeActivationGroup getGroup() {
		return this.pinActivation.getGroup();
	}

	public List<IActivityEdgeInstance> getIncomingEdges() {
		return this.pinActivation.getIncomingEdges();
	}

	public List<IActivityEdgeInstance> getOutgoingEdges() {
		return this.pinActivation.getOutgoingEdges();
	}

	public void setRunning(Boolean status) {
		this.pinActivation.setRunning(status);
	}

	public void _endIsolation() {
		this.pinActivation._endIsolation();
	}

	public void _beginIsolation() {
		this.pinActivation._beginIsolation();
	}
}
