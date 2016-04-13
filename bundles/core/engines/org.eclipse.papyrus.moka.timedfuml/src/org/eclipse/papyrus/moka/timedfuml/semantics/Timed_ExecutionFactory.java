/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.timedfuml.semantics;

import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_ExecutionFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.Trigger;

/**
 * @author sg239226
 *
 */
public class Timed_ExecutionFactory extends CS_ExecutionFactory {

	@Override
	public ISemanticVisitor instantiateVisitor(Element element) {
		// Extends fUML semantics in the sense that newly introduced
		// semantic visitors are instantiated instead of fUML visitors

		ISemanticVisitor visitor = null;
		if (element instanceof AcceptEventAction) {
			boolean isTimeEventTriggered = false;
			for (Element elt : element.getOwnedElements()) {
				if (elt instanceof Trigger) {
					Trigger t = (Trigger) elt;
					if (t.getEvent() != null) {
						Event e = (Event) t.getEvent();
						if (e instanceof TimeEvent) {
							isTimeEventTriggered = true;
						}
					}
				}
			}
			if (isTimeEventTriggered) {
				visitor = new Timed_AcceptEventActionActivation();
			} else {
				visitor = super.instantiateVisitor(element);
			}
		} else if (element instanceof OpaqueAction) {
			visitor = new Timed_OpaqueActionActivation();
		} else if (element instanceof SendSignalAction) {
			visitor = new Timed_SendSignalActionActivation();
		} else {
			visitor = super.instantiateVisitor(element);
		}
		return visitor;
	}

}
