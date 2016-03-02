/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.async.fuml;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.moka.async.fuml.Semantics.CommonBehaviors.Communications.AsyncFIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.async.fuml.Semantics.Loci.LociL1.AsyncLocus;
import org.eclipse.papyrus.moka.async.fuml.debug.AsyncControlDelegate;
import org.eclipse.papyrus.moka.communication.Marshaller;
import org.eclipse.papyrus.moka.communication.event.isuspendresume.Resume_Event;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request;
import org.eclipse.papyrus.moka.composites.CompositeStructuresExecutionEngine;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CommonBehaviors.Communications.CS_DispatchOperationOfInterfaceStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CommonBehaviors.Communications.CS_NameBased_StructuralFeatureOfInterfaceAccessStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.InvocationActions.CS_DefaultConstructStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.InvocationActions.CS_DefaultRequestPropagationStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_ExecutionFactory;
import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_Executor;
import org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.debug.ControlDelegate;
import org.eclipse.papyrus.moka.utils.MokaConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Behavior;

/**
 * Entry point of this plug-in. By extending the FUMLExecutionEngine it contributes
 * to the extension point defined by Moka and is by the way registered as a new engine
 * that can be used to execute a defined model
 *
 * @author CEA LIST (jt605650)
 *
 */
public class FUMLAsyncExecutionEngine extends CompositeStructuresExecutionEngine {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.composites.CompositeStructuresExecutionEngine#start(org.eclipse.uml2.uml.Behavior)
	 */
	@Override
	public void start(Behavior behavior) {
		if (behavior != null) {
			main = behavior;
			// creates the locus, executor and execution factory
			this.locus = new AsyncLocus();
			this.locus.setFactory(new CS_ExecutionFactory());
			this.locus.setExecutor(new CS_Executor());
			// initializes built-in primitive types
			this.initializeBuiltInPrimitiveTypes(locus);
			// Initializes opaque behavior executions
			this.registerOpaqueBehaviorExecutions(locus);
			// Initializes semantic strategies
			this.registerSemanticStrategies(locus);
			// Initializes system services
			this.registerSystemServices(locus);
			// Initializes arguments
			this.initializeArguments(this.args);
			// Finally launches the execution
			this.started = true;
			this.locus.getExecutor().execute(main, null, this.arguments);
			((AsyncControlDelegate) eInstance.getControlDelegate()).notifyMainThreadLogicallyEnded();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.moka.composites.CompositeStructuresExecutionEngine#registerSemanticStrategies(org.eclipse.papyrus.moka.fuml.Semantics.Loci
	 * .LociL1.Locus)
	 */
	@Override
	protected void registerSemanticStrategies(ILocus locus) {
		super.registerSemanticStrategies(locus);
		locus.getFactory().setStrategy(new AsyncFIFOGetNextEventStrategy());
		locus.getFactory().setStrategy(new CS_DispatchOperationOfInterfaceStrategy());
		locus.getFactory().setStrategy(new CS_NameBased_StructuralFeatureOfInterfaceAccessStrategy());
		locus.getFactory().setStrategy(new CS_DefaultRequestPropagationStrategy());
		locus.getFactory().setStrategy(new CS_DefaultConstructStrategy());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.FUMLExecutionEngineForMoka#getControlDelegate()
	 */
	@Override
	public ControlDelegate getControlDelegate() {
		if (this.controlDelegate == null) {
			this.controlDelegate = new AsyncControlDelegate(this);
		}
		return this.controlDelegate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.moka.fuml.FUMLExecutionEngineForMoka#resume(org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request)
	 */
	@Override
	public void resume(Resume_Request request) {
		if (!this.started) {
			Runnable execution = new Runnable() {

				public void run() {
					if (main != null) {
						try {
							start(main);
						}
						catch (Exception e) {
							Activator.log.error(e);
							if (!MokaConstants.SILENT_MODE) {
								Display.getDefault().syncExec(new Runnable() {
									public void run() {
										MessageDialog.openError(Display.getDefault().getActiveShell(), "Moka", "An unexpected error occurred during execution. See error log for details.");
									}
								});
							}
							setIsTerminated(true);
						}
						// Waits for termination. i.e., the main thread is terminated, but object activations may still be running
						FUMLExecutionEngine.eInstance.getControlDelegate().waitForTermination();
						try {
							getDebugTarget().terminate();
						} catch (DebugException e) {
							Activator.log.error(e);
						}
					}
				}
			};
			Thread mainThread = new Thread(execution);
			((AsyncControlDelegate) eInstance.getControlDelegate()).registerObjectActivation(null);
			mainThread.start();
		} else {
			this.getControlDelegate().resume(request);
		}
	}

	// **************************************
	// Management of a resume() request emitted from the debug target.
	// @see ISuspendResume.resume()
	// ***************************************
	/**
	 * Manages reply to a resume() request emitted from the debug target.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	protected void resume_reply(String message) {
		Resume_Request request = Marshaller.getInstance().resume_request_unmarshal(message);
		this.resume(request);
		Resume_Event event = new Resume_Event(request.getDebugElement(), request.getResumeDetail(), this.getThreads());
		this.sendEvent(event);
	}
}
