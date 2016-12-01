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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IStateConfiguration;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IStateMachineConfiguration;
import org.eclipse.papyrus.moka.fuml.statemachines.interfaces.Semantics.StateMachines.IVertexActivation;

public class StateConfiguration implements IStateConfiguration{

	// The activation (i.e. the semantic visitor) for which the StateConfiguration
	// represents an element of the state-machine configuration.
	protected IVertexActivation vertexActivation;
	
	// The parent state configuration of this node
	protected IStateConfiguration parent;
	
	// The children state configuration of this state-machine configuration
	protected List<IStateConfiguration> children;
	
	// The level at which the the state configuration is located in the hierarchy.
	private int level;
	
	// A reference to the complete state-machine configuration
	private IStateMachineConfiguration completeConfiguration;
	
	public StateConfiguration(StateMachineConfiguration configuration){
		this.level = 0;
		this.vertexActivation = null;
		this.children = new ArrayList<IStateConfiguration>();
		this.completeConfiguration = configuration;
	}
	
	public StateConfiguration(IVertexActivation activation){
		this.level = 0;
		this.children = new ArrayList<IStateConfiguration>();
		this.vertexActivation = activation;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public List<IStateConfiguration> getChildren(){
		return this.children;
	}
	
	public IStateConfiguration getParent(){
		return this.parent;
	}
	
	public IVertexActivation getVertexActivation(){
		return this.vertexActivation;
	}
	
	public void setParent(IStateConfiguration configuration){
		this.parent = configuration;
	}
	
	private List<IVertexActivation> getContext(IVertexActivation activation){
		List<IVertexActivation> context = new ArrayList<IVertexActivation>();
		List<IVertexActivation> hierarchy = activation.getAscendingHierarchy();
		int i = hierarchy.size();
		int j = 0;
		boolean found = false;
		while(i >= 1 && !found){
			while(j < this.children.size() && !found){
				if(this.children.get(j).getVertexActivation()==hierarchy.get(i-1)){
					found = true;
					context = hierarchy.subList(1, i);
				}
				j++;
			}
			j = 0;
			i--;
		}
		return context;
	}

	public boolean remove(IVertexActivation activation, List<IVertexActivation> context){
		boolean removed = false;
		if(!context.isEmpty()){
			IVertexActivation current = context.get(context.size()-1);
			IStateConfiguration selectedStateConfiguration = null; 
			int i = 0;
			while(i < this.children.size() && selectedStateConfiguration==null){
				if(this.children.get(i).getVertexActivation()==current){
					selectedStateConfiguration = this.children.get(i);
				}
				i++;
			}
			if(selectedStateConfiguration!=null){
				removed = selectedStateConfiguration.remove(activation, context.subList(0, context.size()-1));
			}
		}else{
			int i = 0;
			while(i < this.children.size() && !removed){
				if(this.children.get(i).getVertexActivation()==activation){
					this.completeConfiguration.deleteFromCartography(this.children.get(i));
					this.children.remove(i);
					removed = true;
				}
				i++;
			}
		}
		return removed;
	}
	
	public boolean add(IVertexActivation activation, List<IVertexActivation> context){
		boolean added = false;
		if(!context.isEmpty()){
			IVertexActivation current = context.get(context.size()-1);
			IStateConfiguration selectedStateConfiguration = null; 
			int i = 0;
			while(i < this.children.size() && selectedStateConfiguration==null){
				if(this.children.get(i).getVertexActivation() ==current){
					selectedStateConfiguration = this.children.get(i);
				}
				i++;
			}
			if(selectedStateConfiguration!=null){
				added = selectedStateConfiguration.add(activation, context.subList(0, context.size()-1));
			}
		}else{
			int i = 0;
			boolean alreadyAdded = false;
			while(i < this.children.size() && !alreadyAdded){
				if(this.children.get(i).getVertexActivation()==activation){
					alreadyAdded = true;
				}
				i++;
			}
			if(!alreadyAdded){
				StateConfiguration newConfiguration = new StateConfiguration(activation);
				newConfiguration.level = this.level + 1;
				newConfiguration.completeConfiguration = this.completeConfiguration;
				this.completeConfiguration.addToCartography(newConfiguration);
				added = this.children.add(newConfiguration);
			}
		}
		return added;
	}
	
	public boolean removeChild(IVertexActivation activation){
		return this.remove(activation, this.getContext(activation));
	}
	
	public boolean addChild(IVertexActivation activation){
		return this.add(activation, this.getContext(activation));	
	}
	
	public String toString(){
		// Return a string representing configuration taking this node as a the root.
		// The string that is obtained is possibly a partial representation of the
		// state-machine configuration.
		String result = "";
		int i = 0;
		result = this.vertexActivation==null ? "ROOT" : this.vertexActivation.getNode().getName();
		result+="(L"+this.level+")";
		if(!this.children.isEmpty()){
			result+="[";
			while(i < this.children.size()){
				result+=this.children.get(i).toString();
				if(i < this.children.size()-1){
					result+=", ";
				}
				i++;
			}
			result+="]";
		}
		return result;
	}
}
