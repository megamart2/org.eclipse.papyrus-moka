package org.eclipse.papyrus.moka.timedfuml.semantics;

import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.discreteevent.Event;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.ClassifierBehaviorExecution;
import org.eclipse.papyrus.moka.timedfuml.actions._startObjectBehavior_Action;

/**
 * @author ac221913
 *
 */
public class Timed_ClassifierBehaviorExecution extends ClassifierBehaviorExecution {

	@Override
	public void _startObjectBehavior() {
		// FIXME delegate control here: this.execution.execute();
		_startObjectBehavior_Action deAction = new _startObjectBehavior_Action(this) ;
		DEScheduler.getInstance().pushEvent(new Event(0.0, deAction));
	}
}
