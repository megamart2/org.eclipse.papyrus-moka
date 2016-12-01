/*****************************************************************************
 * 
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmu.engine.semantics;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_ExecutionFactory;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Trigger;

public class FMUExecutionFactory extends Timed_ExecutionFactory {

	@Override
	public ISemanticVisitor instantiateVisitor(Element element) {
		ISemanticVisitor visitor = null;
		if (element instanceof AcceptEventAction) {
			AcceptEventAction acceptEventAction = (AcceptEventAction)element ;
			if (! acceptEventAction.getTriggers().isEmpty()) {
				Trigger t = acceptEventAction.getTriggers().get(0) ;
				if (t.getEvent() != null && t.getEvent() instanceof ChangeEvent) {
					visitor = new FMUAcceptChangeEventActionActivation() ;
				}
			}
			if (visitor == null) {
				visitor = super.instantiateVisitor(element) ;
			}
		} 
		else if (element instanceof AddStructuralFeatureValueAction) {
			visitor = new FMUAddStructuralFeatureValueAction() ;
		} else if (element instanceof Transition){
			Transition transition = (Transition) element;
			if(transition.getKind() == TransitionKind.EXTERNAL_LITERAL){
				visitor = new FMUExternalTransitionActivation();
			}
		}
		else {
			visitor = super.instantiateVisitor(element);
		}
		return visitor ;
	}
}
