package org.eclipse.papyrus.moka.timedfuml;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.moka.composites.CompositeStructuresExecutionEngine;
import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_Executor;
import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.timedfuml.actions._displayCurrentTimeAction;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_ExecutionFactory;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_Locus;
public class TimedUmlExecutionEngine extends CompositeStructuresExecutionEngine{


	@Override
	public ILocus initializeLocus() {
		this.locus = new Timed_Locus();
		locus.setExecutor(new CS_Executor());
		locus.setFactory(new Timed_ExecutionFactory());

		return this.locus;

	}	

	public void start(IProgressMonitor monitor) {
		super.start(monitor);
		_displayCurrentTimeAction displayAction = new _displayCurrentTimeAction();
		DEScheduler.init(-1.0);
		DEScheduler.getInstance().pushPreStepAction(displayAction);
		DEScheduler.getInstance().run();
	}



}
