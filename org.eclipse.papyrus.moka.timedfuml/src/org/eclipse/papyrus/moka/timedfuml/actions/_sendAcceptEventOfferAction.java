
package org.eclipse.papyrus.moka.timedfuml.actions;

import org.eclipse.papyrus.moka.discreteevent.actions.Action;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_AcceptEventActionActivation;

/**
 * @author sg239226
 *
 */
public class _sendAcceptEventOfferAction extends Action {

	protected Timed_AcceptEventActionActivation actionActivation ;
	
	public _sendAcceptEventOfferAction(Timed_AcceptEventActionActivation actionActivation) {
		super();
		this.actionActivation = actionActivation;
	}
		
	/**
	 * @see org.eclipse.papyrus.moka.discreteevent.actions.Action#execute()
	 *
	 */
	@Override
	public void execute() {
		//System.out.println(DEScheduler.getInstance().getCurrentTime() + " : sending offer - " + this.actionActivation);
		this.actionActivation.sendOffersDefault() ;
	}

}
