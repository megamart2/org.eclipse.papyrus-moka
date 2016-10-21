package org.eclipse.papyrus.fuml.statemachines.interfaces.Semantics.StateMachines;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Trigger;

public interface IStateMachineSemanticVisitor extends ISemanticVisitor {

	public void setNode(NamedElement node);
	
	public NamedElement getNode();
	
	public void setParent(ISemanticVisitor parent);
	
	public ISemanticVisitor getParent();
	
	public List<ISemanticVisitor> getContextChain();
	
	public IExecution getStateMachineExecution();
	
	public ILocus getExecutionLocus();
	
	public IObject_ getExecutionContext();
	
	public boolean isVisitorFor(NamedElement node);
	
	public void activate();
	
	public void activateTransitions();
	
	public IExecution getExecutionFor(Behavior behavior, IEventOccurrence eventOccurrence);
	
	public boolean match(IEventOccurrence eventOccurrence, List<Trigger> triggers);
	
}
