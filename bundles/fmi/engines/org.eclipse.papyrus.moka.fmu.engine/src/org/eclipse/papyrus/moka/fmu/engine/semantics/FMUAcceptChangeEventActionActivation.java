package org.eclipse.papyrus.moka.fmu.engine.semantics;

import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.InvocationActions.CS_AcceptEventActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.ISignalInstance;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Property;

public class FMUAcceptChangeEventActionActivation extends CS_AcceptEventActionActivation {
	
	protected AcceptEventAction acceptChangeEventNode ;
	protected Property observedProperty ; 
	
	@Override
	public void run() {
		// Create an event accepter and initialize waiting to false.
		super.run();
		this.acceptChangeEventNode = (AcceptEventAction)this.node ;
		ChangeEvent changeEvent = (ChangeEvent)this.acceptChangeEventNode.getTriggers().get(0).getEvent() ;
		if (changeEvent.getChangeExpression() != null) {
			String changeExpression = ((LiteralString)changeEvent.getChangeExpression()).getValue() ;
			Class type = (Class)this.getExecutionContext().getTypes().get(0) ;
			for (Property p : type.getAllAttributes()) {
				if (changeExpression.equals(p.getName())) {
					observedProperty = p ;
				}
			}
		}
		this.eventAccepter = new FMUAcceptChangeEventActionEventAccepter();
		this.eventAccepter.setAcceptEventActionActivation(this);
		this.waiting = false;
	}

	@Override
	public void accept(ISignalInstance signalInstance) {
		if (this.running) {
			this.sendOffers();
			this.waiting = false;
			Debug.println("[fire] Checking if " + this.node.getName() + " should fire again...");
			this.receiveOffer();
			this.resume();
		}
	}
	
	public Boolean matchChangeEvent(FMUChangeEventOccurence eventOccurence) {
		return eventOccurence.getChangedProperty() == observedProperty ;
	}
	
}
