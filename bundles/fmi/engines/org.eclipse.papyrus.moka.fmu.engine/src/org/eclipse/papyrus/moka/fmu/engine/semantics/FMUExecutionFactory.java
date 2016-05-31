package org.eclipse.papyrus.moka.fmu.engine.semantics;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_ExecutionFactory;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Element;
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
		} else {
			visitor = super.instantiateVisitor(element);
		}
		return visitor ;
	}
}
