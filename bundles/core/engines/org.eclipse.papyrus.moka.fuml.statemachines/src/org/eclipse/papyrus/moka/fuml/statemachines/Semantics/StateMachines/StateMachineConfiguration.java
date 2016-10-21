/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

import static org.eclipse.papyrus.moka.fuml.statemachines.Activator.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IStateActivation;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IStateConfiguration;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IStateMachineConfiguration;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IStateMachineExecution;
import org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines.IVertexActivation;

public class StateMachineConfiguration implements IStateMachineConfiguration{
		
	// The state-machine that is executed and for which this object
	// represents the hierarchy of active states.
	protected IStateMachineExecution execution;
	
	// The root node of the state machine configuration. It represents
	// the top level active state.
	protected IStateConfiguration rootConfiguration;
	
	// Provides a flattened view  of the hierarchy of active states.
	private Map<Integer, List<IVertexActivation>> cartorgraphy;
	
	public IStateConfiguration getRoot(){
		return this.rootConfiguration;
	}
	
	public IStateMachineExecution getExecution(){
		return this.execution;
	}
	
	public Map<Integer, List<IVertexActivation>> getCartography(){
		return this.cartorgraphy;
	}
	
	public StateMachineConfiguration(IStateMachineExecution execution){
		this.rootConfiguration = new StateConfiguration(this);
		this.execution = execution;
		this.cartorgraphy = new HashMap<Integer, List<IVertexActivation>>();
	}
		
	public boolean register(IStateActivation stateActivation){
		// Register the given state activation in the state-machine configuration.
		// This occurs when the state activation is entered.
		return this.add(stateActivation);
	}
	
	public boolean unregister(IStateActivation stateActivation){
		// Unregister the given state activation from the state-machine configuration
		// This occurs when the state activation is exited. When the removal process
		// is successful the last action is to release possibly deferred events related
		// to that state activation.
		boolean removed = this.remove(stateActivation);
		if(removed){
			stateActivation.releaseDeferredEvents();
		}
		return removed;
	}
	
	public boolean isActive(IVertexActivation activation){
		// A vertex that is currently active is part of the state-machine configuration
		boolean found = false;
		Iterator<Integer> levelsIterator = this.cartorgraphy.keySet().iterator();
		while(!found && levelsIterator.hasNext()){
			found = this.cartorgraphy.get(levelsIterator.next()).contains(activation);
		}
		return found;
	}
	
	protected boolean remove(IVertexActivation activation){
		boolean removed = this.rootConfiguration.removeChild(activation);
		logger.info(this.toString());
		return removed; 
	}
	
	protected boolean add(IVertexActivation activation){
		boolean added = this.rootConfiguration.addChild(activation);
		logger.info(this.toString());
		return added;
	}
	
	public void addToCartography(IStateConfiguration configuration){
		// Add the given representation of state that is part to the state-machine
		// configuration to the flattened representation.
		if(this.cartorgraphy.containsKey(configuration.getLevel())){
			this.cartorgraphy.get(configuration.getLevel()).add(configuration.getVertexActivation());
		}else{
			List<IVertexActivation> activation = new ArrayList<IVertexActivation>();
			activation.add(configuration.getVertexActivation());
			this.cartorgraphy.put(configuration.getLevel(), activation);
		}
	}
	
	public void deleteFromCartography(IStateConfiguration configuration){
		// Remove the given representation of state that is part to the state-machine configuration
		// from the flattened representation.
		if(this.cartorgraphy.containsKey(configuration.getLevel())){
			this.cartorgraphy.get(configuration.getLevel()).remove(configuration.getVertexActivation());
		}
	}
	
	public String toString(){
		// Return a string representing the current state-machine configuration.
		// This representation takes the following form:
		// [ROOT(L0)[S1(L1)[S1.X(L2), S.2.X(L2)]]]
		return "["+this.rootConfiguration.toString()+"]";
	}
}
