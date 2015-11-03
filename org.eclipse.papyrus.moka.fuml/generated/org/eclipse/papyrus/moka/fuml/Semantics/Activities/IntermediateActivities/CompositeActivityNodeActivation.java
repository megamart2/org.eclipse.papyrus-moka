/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities;


public abstract class CompositeActivityNodeActivation extends ActivityNodeActivation {

	/*
	 * public void run() {
	 * ((IActivityNodeActivation)this.master).run();
	 * }
	 *
	 * public void receiveOffer() {
	 * ((IActivityNodeActivation)this.master).receiveOffer();
	 * }
	 *
	 * public List<Token> takeOfferedTokens() {
	 * return ((IActivityNodeActivation)this.master).takeOfferedTokens();
	 * }
	 *
	 * public void sendOffers(List<Token> tokens) {
	 * ((IActivityNodeActivation)this.master).sendOffers(tokens);
	 * }
	 *
	 * public void terminate() {
	 * ((IActivityNodeActivation)this.master).terminate();
	 * }
	 *
	 * public Boolean isReady() {
	 * return ((IActivityNodeActivation)this.master).isReady();
	 * }
	 *
	 * public Boolean isRunning() {
	 * return ((IActivityNodeActivation)this.master).isRunning();
	 * }
	 *
	 * public void addOutgoingEdge(ActivityEdgeInstance edge) {
	 * ((IActivityNodeActivation)this.master).addOutgoingEdge(edge);
	 * }
	 *
	 * public void addIncomingEdge(ActivityEdgeInstance edge) {
	 * ((IActivityNodeActivation)this.master).addIncomingEdge(edge);
	 * }
	 *
	 * public void createNodeActivations() {
	 * ((IActivityNodeActivation)this.master).createNodeActivations();
	 * }
	 *
	 * public void createEdgeInstances() {
	 * ((IActivityNodeActivation)this.master).createEdgeInstances();
	 *
	 * }
	 *
	 * public Boolean isSourceFor(ActivityEdgeInstance edgeInstance) {
	 * return ((IActivityNodeActivation)this.master).isSourceFor(edgeInstance);
	 * }
	 *
	 * public ActivityExecution getActivityExecution() {
	 * return ((IActivityNodeActivation)this.master).getActivityExecution();
	 * }
	 *
	 * public Object_ getExecutionContext() {
	 * return ((IActivityNodeActivation)this.master).getExecutionContext();
	 * }
	 *
	 * public Locus getExecutionLocus() {
	 * return ((IActivityNodeActivation)this.master).getExecutionLocus();
	 * }
	 *
	 * public ActivityNodeActivation getNodeActivation(ActivityNode node) {
	 * return ((IActivityNodeActivation)this.master).getNodeActivation(node);
	 * }
	 *
	 * public void addToken(Token token) {
	 * ((IActivityNodeActivation)this.master).addToken(token);
	 * }
	 *
	 * public Integer removeToken(Token token) {
	 * return ((IActivityNodeActivation)this.master).removeToken(token);
	 * }
	 *
	 * public void addTokens(List<Token> tokens) {
	 * ((IActivityNodeActivation)this.master).addTokens(tokens);
	 * }
	 *
	 * public List<Token> takeTokens() {
	 * return ((IActivityNodeActivation)this.master).takeTokens();
	 * }
	 *
	 * public void clearTokens() {
	 * ((IActivityNodeActivation)this.master).clearTokens();
	 * }
	 *
	 * public List<Token> getTokens() {
	 * return ((IActivityNodeActivation)this.master).getTokens();
	 * }
	 *
	 * public void suspend() {
	 * ((IActivityNodeActivation)this.master).suspend();
	 * }
	 *
	 * public void resume() {
	 * ((IActivityNodeActivation)this.master).resume();
	 * }
	 *
	 * public ActivityNode getNode() {
	 * return ((ActivityNodeActivation)this.master).getNode();
	 * }
	 *
	 * public List<ActivityEdgeInstance> getIncomingEdges() {
	 * return ((IActivityNodeActivation)this.master).getIncomingEdges();
	 * }
	 *
	 * public List<ActivityEdgeInstance> getOutgoingEdges() {
	 * return ((IActivityNodeActivation)this.master).getIncomingEdges();
	 * }
	 *
	 * public ActivityNodeActivationGroup getGroup() {
	 * return ((IActivityNodeActivation)this.master).getGroup();
	 * }
	 */
}
