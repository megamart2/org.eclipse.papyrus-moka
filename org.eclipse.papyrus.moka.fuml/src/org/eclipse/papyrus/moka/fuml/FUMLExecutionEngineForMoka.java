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
package org.eclipse.papyrus.moka.fuml;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.moka.MokaConstants;
import org.eclipse.papyrus.moka.animation.engine.AnimationManager;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Suspend_Request;
import org.eclipse.papyrus.moka.communication.request.iterminate.Terminate_Request;
import org.eclipse.papyrus.moka.debug.MokaBreakpoint;
import org.eclipse.papyrus.moka.debug.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.MokaThread;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.ControlDelegate;
import org.eclipse.papyrus.moka.fuml.presentation.FUMLPresentationUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class FUMLExecutionEngineForMoka.
 */
public class FUMLExecutionEngineForMoka extends FUMLExecutionEngine {

	/** The control delegate. */
	protected ControlDelegate controlDelegate;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#init(org.eclipse.emf.ecore.EObject, java.lang.String[],
	 * org.eclipse.papyrus.moka.debug.MokaDebugTarget, int, int, int)
	 */
	@Override
	public void init(EObject eObjectToExecute, String[] args, MokaDebugTarget debugTarget, int requestPort, int replyPort, int eventPort) throws UnknownHostException, IOException {
		super.init(eObjectToExecute, args, debugTarget, requestPort, replyPort, eventPort);
		if (eObjectToExecute instanceof Behavior) {
			main = (Behavior) eObjectToExecute;
			this.debugTarget = debugTarget;
			this.debugTarget.setName("FUML Execution Engine");
			FUMLPresentationUtils.init(eObjectToExecute);
			AnimationManager.getInstance().init(eObjectToExecute);
			FUMLExecutionEngine.eInstance = this;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#initializeArguments(java.lang.String[])
	 */
	@Override
	public void initializeArguments(String[] args) {
		this.args = args;
		if (this.locus == null) {
			return;
		}

		this.arguments = new ArrayList<ParameterValue>();
		if (this.args == null) {
			return;
		}

		List<Value> tmpArgs = new ArrayList<Value>();

		// analyzes arguments versus parameters of the main behavior
		List<Parameter> parameters = main.getOwnedParameters();
		if (parameters == null) {
			return;
		}
		List<Parameter> parametersWhichNeedArguments = new ArrayList<Parameter>();
		// There must be the same number of parameters (except the return parameter)
		for (Parameter p : parameters) {
			if (p.getDirection() != ParameterDirectionKind.RETURN_LITERAL) {
				parametersWhichNeedArguments.add(p);
			}
		}
		if (parametersWhichNeedArguments.size() != this.args.length) {
			return;
		}

		// iterates on parameters, and tries to create tokens corresponding to arguments
		int i = 0;
		for (Parameter p : parametersWhichNeedArguments) {
			Type t = p.getType();
			if (t != null) {
				// FIXME
				PrimitiveType pt = (PrimitiveType) this.locus.factory.getBuiltInType(t.getName());
				if (pt == null) {
					return;
				}
				if (pt.getName().equals("Integer")) {
					IntegerValue value = new IntegerValue();
					value.value = new Integer(this.args[i]);
					tmpArgs.add(value);
				} else if (pt.getName().equals("String")) {
					StringValue value = new StringValue();
					value.value = this.args[i];
					tmpArgs.add(value);
				} else if (pt.getName().equals("Boolean")) {
					BooleanValue value = new BooleanValue();
					value.value = new Boolean(this.args[i]);
					tmpArgs.add(value);
				} else if (pt.getName().equals("UnlimitedNatural")) {
					UnlimitedNaturalValue value = new UnlimitedNaturalValue();
					value.value = new Integer(args[i]);
					tmpArgs.add(value);
				} else {
					return; // Unsupported type. TODO Consider Real
				}
			}
			i++;
		}

		i = 0;
		// creates actual arguments
		for (Value v : tmpArgs) {
			ParameterValue arg = new ParameterValue();
			arg.parameter = parameters.get(i);
			arg.values = new ArrayList<Value>();
			arg.values.add(v);
			this.arguments.add(arg);
			i++;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine#getControlDelegate()
	 */
	@Override
	public ControlDelegate getControlDelegate() {
		if (this.controlDelegate == null) {
			this.controlDelegate = new ControlDelegate(this);
		}
		return this.controlDelegate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#addBreakpoint(org.eclipse.papyrus.moka.debug.MokaBreakpoint)
	 */
	@Override
	public void addBreakpoint(MokaBreakpoint breakpoint) {
		this.getControlDelegate().addBreakpoint(breakpoint);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#removeBreakpoint(org.eclipse.papyrus.moka.debug.MokaBreakpoint)
	 */
	@Override
	public void removeBreakpoint(MokaBreakpoint breakpoint) {
		this.getControlDelegate().removeBreakpoint(breakpoint);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#disconnect()
	 */
	@Override
	public void disconnect() {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#resume(org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request)
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
						}
						if (!isTerminated()) {
							try {
								getDebugTarget().terminate();
							} catch (DebugException e) {
								Activator.log.error(e);
							}
						}
					}
				}
			};
			Thread mainThread = new Thread(execution);
			mainThread.start();
		} else {
			this.getControlDelegate().resume(request);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#suspend(org.eclipse.papyrus.moka.communication.request.isuspendresume.Suspend_Request)
	 */
	@Override
	public void suspend(Suspend_Request request) {
		this.getControlDelegate().suspend(request);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#terminate(org.eclipse.papyrus.moka.communication.request.iterminate.Terminate_Request)
	 */
	@Override
	public void terminate(Terminate_Request request) {
		this.getControlDelegate().terminate(request);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.IExecutionEngine#getThreads()
	 */
	public MokaThread[] getThreads() {
		return this.getControlDelegate().getThreads();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.engine.AbstractExecutionEngine#getStackFrames(org.eclipse.debug.core.model.IThread)
	 */
	@Override
	public IStackFrame[] getStackFrames(IThread thread) {
		return this.getControlDelegate().getStackFrames(thread);
	}
}
