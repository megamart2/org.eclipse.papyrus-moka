/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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
package org.eclipse.papyrus.moka.fmu.engine.semantics;

import java.util.Iterator;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventOccurrence;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.ExternalTransitionActivation;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Trigger;

public class FMUExternalTransitionActivation extends ExternalTransitionActivation{

	public boolean canFireOn(IEventOccurrence eventOccurrence){
		boolean reactive = super.canFireOn(eventOccurrence);
		if(!reactive){
			reactive = this.hasTrigger(eventOccurrence) && this.evaluateGuard(eventOccurrence) && this.canPropagateExecution(eventOccurrence);
		}
		return reactive;
	}
	
	@Override
	public boolean match(IEventOccurrence eventOccurrence, List<Trigger> triggers) {
		boolean matches = false;
		if(eventOccurrence instanceof FMUChangeEventOccurence){
			int i = 0;
			while(!matches && i < triggers.size()){
				Trigger trigger = triggers.get(i);
				if(trigger.getEvent() instanceof ChangeEvent){
					Property observedProperty = this.getObservedProperty((ChangeEvent)trigger.getEvent());
					if(observedProperty == ((FMUChangeEventOccurence)eventOccurrence).getChangedProperty()){
						matches = true;
					}
				}
				i++;
			}
		}else{
			matches = super.match(eventOccurrence, triggers);
		}
		return matches;
	}
	
	protected Property getObservedProperty(ChangeEvent event){
		String propertyName = ((LiteralString)event.getChangeExpression()).getValue();
		IObject_ context = this.getExecutionContext();
		Iterator<Classifier> types =  context.getTypes().iterator();
		Property matchingProperty = null;
		while(matchingProperty == null && types.hasNext()){
			Iterator<Property> properties = types.next().allAttributes().iterator();
			while(matchingProperty == null && properties.hasNext()){
				Property property = properties.next();
				if(property.getName().equals(propertyName)){
					matchingProperty = property;
				}
			}
		}
		return matchingProperty;
	}
	
}
