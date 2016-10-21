package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import java.util.List;

public interface IStateConfiguration {
	
	public int getLevel();
	
	public IStateConfiguration getParent();
	
	public void setParent(IStateConfiguration configuration);
	
	public IVertexActivation getVertexActivation();
	
	public List<IStateConfiguration> getChildren();
	
	public boolean addChild(IVertexActivation activation);
	
	public boolean removeChild(IVertexActivation activation);
	
	public boolean add(IVertexActivation activation, List<IVertexActivation> context);
	
	public boolean remove(IVertexActivation activation, List<IVertexActivation> context);
}
