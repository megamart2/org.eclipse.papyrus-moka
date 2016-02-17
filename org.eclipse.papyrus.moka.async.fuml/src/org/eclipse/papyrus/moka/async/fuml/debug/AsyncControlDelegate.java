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
package org.eclipse.papyrus.moka.async.fuml.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.moka.MokaConstants;
import org.eclipse.papyrus.moka.async.fuml.Semantics.CommonBehaviors.Communications.AsyncObjectActivation;
import org.eclipse.papyrus.moka.communication.event.Start_Event;
import org.eclipse.papyrus.moka.communication.event.isuspendresume.Resume_Event;
import org.eclipse.papyrus.moka.communication.event.isuspendresume.Suspend_Event;
import org.eclipse.papyrus.moka.communication.event.iterminate.Terminate_Event;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Suspend_Request;
import org.eclipse.papyrus.moka.communication.request.iterminate.Terminate_Request;
import org.eclipse.papyrus.moka.debug.MokaStackFrame;
import org.eclipse.papyrus.moka.debug.MokaThread;
import org.eclipse.papyrus.moka.engine.AbstractExecutionEngine;
import org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IObjectActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.CompleteActions.AcceptEventActionEventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityNodeActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.ObjectActivation;
import org.eclipse.papyrus.moka.fuml.debug.ControlDelegate;
import org.eclipse.papyrus.moka.fuml.debug.FUMLThread;
import org.eclipse.papyrus.moka.fuml.presentation.FUMLPresentationUtils;
import org.eclipse.uml2.uml.AcceptEventAction;

// TODO: Auto-generated Javadoc
/**
 * The Class AsyncControlDelegate.
 */
public class AsyncControlDelegate extends ControlDelegate {

	/** The main thread. */
	protected FUMLThread mainThread;

	/** The object activations. */
	protected List<IObjectActivation> objectActivations = new ArrayList<IObjectActivation>();

	/** The object activations to fuml thread. */
	protected Map<IObjectActivation, FUMLThread> objectActivationsToFUMLThread = new HashMap<IObjectActivation, FUMLThread>();

	/** The terminate request by client. */
	protected boolean terminateRequestByClient = false;

	/** The locks. */
	protected Map<FUMLThread, Object> locks = new HashMap<FUMLThread, Object>();

	/**
	 * Instantiates a new async control delegate.
	 *
	 * @param engine
	 *            the engine
	 */
	public AsyncControlDelegate(AbstractExecutionEngine engine) {
		super(engine);
		this.threads = new ArrayList<FUMLThread>();
		this.objectActivationToWaitingAcceptEventActions = new HashMap<AsyncObjectActivation, List<AcceptEventAction>>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.debug.ControlDelegate#getThreads()
	 */
	@Override
	public synchronized MokaThread[] getThreads() {
		return threads.toArray(new MokaThread[threads.size()]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.debug.ControlDelegate#waitForTermination()
	 */
	@Override
	public void waitForTermination() {
		while (!this.engine.isTerminated()) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					Activator.log.error(e);
				}
			}
		}
	}

	/**
	 * Resume fuml thread.
	 *
	 * @param thread
	 *            the thread
	 * @param reasonForResuming
	 *            the reason for resuming
	 */
	protected void resumeFUMLThread(FUMLThread thread, int reasonForResuming) {
		thread.setSuspended(false);
		thread.stepEnded();
		thread.setReasonForResuming(reasonForResuming);
		thread.setReasonForSuspending(-1);
		if (reasonForResuming == DebugEvent.STEP_OVER) {
			thread.setIsStepping(true);
		}
		//RenderHandler.getInstance().stopRendering(thread.getSuspensionPoint(), AnimationKind.SUSPENDED);
		thread.setStackFrames(new IStackFrame[] {});
		Object lock = this.locks.get(thread);
		synchronized (lock) {
			this.locks.remove(thread);
			lock.notify();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.debug.ControlDelegate#resume(org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request)
	 */
	@Override
	public synchronized void resume(Resume_Request request) {
		this.reasonForResuming = request.getResumeDetail();
		if (reasonForResuming != DebugEvent.CLIENT_REQUEST) {
			reasonForResuming = DebugEvent.STEP_OVER;
		}
		IDebugElement elementToResume = request.getDebugElement();
		if (elementToResume instanceof IDebugTarget) {
			// resume all threads
			for (FUMLThread thread : this.threads) {
				if (thread.isSuspended() && !thread.isWaiting()) {
					this.resumeFUMLThread(thread, reasonForResuming);
				}
			}
		} else {
			// This is a thread
			FUMLThread thread = (FUMLThread) request.getDebugElement();
			this.resumeFUMLThread(thread, reasonForResuming);
		}
	}

	/**
	 * Register object activation.
	 *
	 * @param activation
	 *            the activation
	 * @param activationName
	 *            the activation name
	 */
	public synchronized void registerObjectActivation(IObjectActivation activation, String activationName) {
		FUMLThread fUMLThread = new FUMLThread(FUMLExecutionEngine.eInstance.getDebugTarget());
		this.threads.add(0, fUMLThread);
		if (activation == null) { // this is the main thread
			fUMLThread.setName("Main Thread");
			fUMLThread.setStackFrames(new IStackFrame[] {});
			mainThread = fUMLThread;
			return;
		}
		objectActivations.add(activation);
		this.objectActivationsToFUMLThread.put(activation, fUMLThread);
		// This is a thread for an object activation, created in the course of execution
		Start_Event event = new Start_Event(fUMLThread, getThreads());
		fUMLThread.setName(activationName + " (" + fUMLThread.hashCode() + ")");
		fUMLThread.setStackFrames(new IStackFrame[] {});
		FUMLExecutionEngine.eInstance.sendEvent(event);
	}

	/**
	 * Register object activation.
	 *
	 * @param activation
	 *            the activation
	 */
	public void registerObjectActivation(ObjectActivation activation) {
		this.registerObjectActivation(activation, "ObjectActivation");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.debug.ControlDelegate#terminate(org.eclipse.papyrus.moka.communication.request.iterminate.Terminate_Request)
	 */
	@Override
	public void terminate(Terminate_Request request) {
		engine.setIsTerminated(true);
		this.terminateRequestByClient = true;
		/**********/
		// 439639: [Moka] oepm.async.fuml.debug.AsyncControlDelegate.terminate shall send a TerminateSignalInstance to all objects in the execution locus
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=439639
		for (IExtensionalValue v : FUMLExecutionEngine.eInstance.getLocus().getExtensionalValues()) {
			if (v instanceof Object_) {
				((Object_) v).send(new TerminateSignalInstance());
			}
		}
		// for(ObjectActivation activation : objectActivations) {
		// if(activation != null /* && ((AsyncObjectActivation)activation).getCurrentState() == ObjectActivationState.WAITING */) {
		// //activation.send(new TerminateSignalInstance());
		// activation.object.send(new TerminateSignalInstance()) ;
		// }
		// }
		/***********/
		for (Object lock : this.locks.values()) {
			synchronized (lock) {
				lock.notify();
			}
		}
		synchronized (this) {
			notifyAll();
		}
		//RenderHandler.getInstance().clean();
	}

	/**
	 * Notify thread termination.
	 *
	 * @param objectActivation
	 *            the object activation
	 */
	public synchronized void notifyThreadTermination(ObjectActivation objectActivation) {
		if (this.terminateRequestByClient)
		{
			return; // do nothing
		}
		if (!FUMLExecutionEngine.eInstance.isTerminated()) {
			FUMLThread fUMLThread = this.objectActivationsToFUMLThread.get(objectActivation);
			if (fUMLThread != null) {
				fUMLThread.setIsTerminated(true);
				this.threads.remove(fUMLThread);
			}
			Terminate_Event terminateEvent = null;
			if (this.threads.isEmpty()) {
				this.engine.setIsTerminated(true);
				synchronized (this) {
					notifyAll();
				}
				terminateEvent = new Terminate_Event(this.engine.getDebugTarget(), this.getThreads());
			} else if (fUMLThread != null) {
				terminateEvent = new Terminate_Event(fUMLThread, this.getThreads());
			}
			// else {
			// terminateEvent = new Terminate_Event(this.engine.getDebugTarget(), this.getThreads()) ;
			// }
			if (terminateEvent != null) {
				FUMLExecutionEngine.eInstance.sendEvent(terminateEvent);
			}
		}

	}

	/**
	 * Notify main thread logically ended.
	 */
	public synchronized void notifyMainThreadLogicallyEnded() {
		if (this.terminateRequestByClient)
		{
			return; // do nothing
		}
		this.mainThread.setIsTerminated(true);
		Terminate_Event terminateEvent;
		this.threads.remove(mainThread);
		if (this.threads.isEmpty()) {
			this.engine.setIsTerminated(true);
			synchronized (this) { // FIXME
				notifyAll();
			}
			terminateEvent = new Terminate_Event(this.engine.getDebugTarget(), this.getThreads());
		} else {
			terminateEvent = new Terminate_Event(mainThread, this.getThreads());
		}
		FUMLExecutionEngine.eInstance.sendEvent(terminateEvent);
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.debug.ControlDelegate#suspend(org.eclipse.papyrus.moka.communication.request.isuspendresume.Suspend_Request)
	 */
	@Override
	public synchronized void suspend(Suspend_Request request) {
		if (request.getDebugElement() instanceof IDebugTarget) {
			for (FUMLThread thread : this.threads) {
				thread.setReasonForSuspending(request.getSuspendDetail());
				thread.setIsStepping(false);
			}
		} else { // This is a thread
			FUMLThread thread = (FUMLThread) request.getDebugElement();
			thread.setReasonForSuspending(request.getSuspendDetail());
			thread.setIsStepping(false);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.debug.ControlDelegate#control(java.lang.Object)
	 */
	@Override
	public boolean control(Object object) {
		if (this.engine.isTerminated()) {
			return false;
		}

		// Retrieves the semantic element
		EObject semanticElement = null;
		IObject_ executionContext = null;
		if (object instanceof ActivityNodeActivation) {
			semanticElement = ((ActivityNodeActivation) object).node;
			if (((ActivityNodeActivation) object).group != null) {
				executionContext = ((ActivityNodeActivation) object).getExecutionContext();
			}
		} else if (object instanceof ActivityEdgeInstance) {
			semanticElement = ((ActivityEdgeInstance) object).edge;
			if (((ActivityEdgeInstance) object).group != null) {
				executionContext = ((ActivityEdgeInstance) object).group.getActivityExecution().context;
			}
		} else {
			Activator.log.error(new Exception("Unexpected element in ControlDelegate::control"));
			this.engine.setIsTerminated(true);
			return false;
		}

		if (executionContext != null) {
			try {
				FUMLThread thread = null;
				if (executionContext instanceof ActivityExecution) {
					// Execution takes place in the context of the Main thread
					thread = this.mainThread;
				} else {
					IObjectActivation objectActivation = executionContext.getObjectActivation();
					thread = this.objectActivationsToFUMLThread.get(objectActivation);
					if (thread == null) { // This means that the context is a passive object executing in the context of the main thread ?? TODO check this...
						thread = this.mainThread;
					}
				}
				if (object != null && MokaConstants.MOKA_AUTOMATIC_ANIMATION && this.mode.equals(ILaunchManager.DEBUG_MODE) && !thread.isStepping()) {
					this.animate(semanticElement);
				}
				int reasonForSuspending = -1;
				if (thread.getReasonForSuspending() != -1) {
					reasonForSuspending = thread.getReasonForSuspending();
				} else if (thread.getReasonForResuming() != DebugEvent.CLIENT_REQUEST) {
					reasonForSuspending = DebugEvent.STEP_END;
				} else if (this.elementsWithBreakpoints.contains(semanticElement)) { // Tries to check if a breakpoint applies
					reasonForSuspending = DebugEvent.BREAKPOINT;
					thread.setSuspensionPoint(semanticElement);
				}
				if (reasonForSuspending != -1) {
					thread.setSuspended(true);
					thread.setReasonForSuspending(reasonForSuspending);
					MokaStackFrame stackFrame = FUMLPresentationUtils.getMokaStackFrame(object);
					stackFrame.setThread(thread);
					thread.setStackFrames(new IStackFrame[] { stackFrame });
					Suspend_Event suspendEvent = new Suspend_Event(thread, reasonForSuspending, this.getThreads());
					engine.sendEvent(suspendEvent);
					String lock = new String();
					this.locks.put(thread, lock);
					synchronized (lock) {
						lock.wait();
					}
				}
			} catch (InterruptedException e) {
				Activator.log.error(e);
			}
		}

		return !this.engine.isTerminated();
	}

	/** The object activation to waiting accept event actions. */
	protected Map<AsyncObjectActivation, List<AcceptEventAction>> objectActivationToWaitingAcceptEventActions;

	/**
	 * Notify waiting state entered.
	 *
	 * @param asyncObjectActivation
	 *            the async object activation
	 */
	public void notifyWaitingStateEntered(AsyncObjectActivation asyncObjectActivation) {
		FUMLThread thread = this.objectActivationsToFUMLThread.get(asyncObjectActivation);
		if (thread != null) {
			thread.setIsWaiting(true);
			thread.setSuspended(true);
			thread.setStackFrames(new IStackFrame[] {});
			if (MokaConstants.MOKA_AUTOMATIC_ANIMATION && this.mode.equals(ILaunchManager.DEBUG_MODE)) {
				Suspend_Event suspendEvent = new Suspend_Event(thread, DebugEvent.CHANGE, this.getThreads());
				engine.sendEvent(suspendEvent);
				List<AcceptEventAction> waitingAcceptEventActions = new ArrayList<AcceptEventAction>();
				for (IEventAccepter eventAccepter : asyncObjectActivation.waitingEventAccepters) {
					if (eventAccepter instanceof AcceptEventActionEventAccepter) {
						AcceptEventAction action = (AcceptEventAction) ((AcceptEventActionEventAccepter) eventAccepter).actionActivation.node;
						waitingAcceptEventActions.add(action);
						//((AcceptEventActionEventAccepter) eventAccepter).actionActivation.animate(RenderHandler.getInstance());
					}
				}
				objectActivationToWaitingAcceptEventActions.put(asyncObjectActivation, waitingAcceptEventActions);
			}
		}
	}

	/**
	 * Notify waiting state exit.
	 *
	 * @param asyncObjectActivation
	 *            the async object activation
	 * @param accepter
	 *            the accepter
	 */
	public void notifyWaitingStateExit(AsyncObjectActivation asyncObjectActivation, AcceptEventActionEventAccepter accepter) {
		FUMLThread thread = this.objectActivationsToFUMLThread.get(asyncObjectActivation);
		if (thread != null) {
			thread.setIsWaiting(false);
			thread.setSuspended(false);
			thread.setStackFrames(new IStackFrame[] {});
			if (MokaConstants.MOKA_AUTOMATIC_ANIMATION && this.mode.equals(ILaunchManager.DEBUG_MODE)) {
				Resume_Event resumeEvent = new Resume_Event(thread, DebugEvent.CHANGE, this.getThreads());
				engine.sendEvent(resumeEvent);
				List<AcceptEventAction> waitingAcceptEventActions = objectActivationToWaitingAcceptEventActions.get(asyncObjectActivation);
				AcceptEventAction action = (AcceptEventAction) accepter.actionActivation.node;
				if (waitingAcceptEventActions != null) {
					waitingAcceptEventActions.remove(action);
				} else {
					waitingAcceptEventActions = new ArrayList<AcceptEventAction>();
				}
				objectActivationToWaitingAcceptEventActions.put(asyncObjectActivation, waitingAcceptEventActions);
				if (action != null) {
					//accepter.actionActivation.animate(RenderHandler.getInstance());
				}
			}
		}
	}
}
