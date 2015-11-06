/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * (CEA LIST) - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.moka.tests;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.moka.MokaConstants;
import org.eclipse.papyrus.moka.debug.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.MokaProcess;
import org.eclipse.papyrus.moka.engine.IExecutionEngine;
import org.eclipse.papyrus.moka.engine.MokaExecutionEngineJob;
import org.eclipse.papyrus.moka.fuml.assertionlibrary.utils.ReportNameSingleton;
import org.eclipse.papyrus.moka.launch.EditorUtils;
import org.eclipse.papyrus.moka.launch.MokaLaunchDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Activity;

public abstract class AbstractMokaLaunchConfigurationDelegate extends LaunchConfigurationDelegate implements ILaunchConfigurationDelegate {

	protected IExecutionEngine engine;

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		// instantiates the actual execution engine
		engine = this.instantiateExecutionEngine();
		if(engine == null) {
			this.abort("Could not instantiate execution engine", null);
		}

		// Create a job for the execution of this engine
		MokaExecutionEngineJob engineJob = new MokaExecutionEngineJob("Execution Engine Job", engine);

		// retrieves values for the various attributes associated with the launch configuration
		String resourceURI = configuration.getAttribute(MokaLaunchDelegate.URI_ATTRIBUTE_NAME, "");
		String uriFragment = configuration.getAttribute(MokaLaunchDelegate.FRAGMENT_ATTRIBUTE_NAME, "");
		String arguments = configuration.getAttribute(MokaLaunchDelegate.ARGS_ATTRIBUTE_NAME, "");
		String[] args = arguments.split("\\s+");

		IEditorPart part = EditorUtils.getEditorPart(resourceURI);

		//IEditorPart part = workbench.getActiveWorkbenchWindow().getActivePage().getActiveEditor() ;
		ServicesRegistry servicesRegistry = (ServicesRegistry)part.getAdapter(ServicesRegistry.class);
		ResourceSet resourceSet = null;
		try {
			resourceSet = servicesRegistry.getService(ModelSet.class);
		} catch (ServiceException e1) {
			resourceSet = new ResourceSetImpl();
			org.eclipse.papyrus.infra.core.Activator.log.error(e1);
		}

		// from the arguments, retrieves the EObject to be executed

		//ResourceSet resourceSet = new ResourceSetImpl() ;
		Resource resource = resourceSet.getResource(URI.createURI(resourceURI), true);
		final EObject eObjectToExecute = resource.getEObject(uriFragment);

		ReportNameSingleton reportNameSingleton = ReportNameSingleton.getInstance();

		reportNameSingleton.setEngineName(engine.getClass().getSimpleName());
		reportNameSingleton.setExecutedActivityName(((Activity)eObjectToExecute).getName());

		// Gets port addresses for sockets
		int requestPort = -1;
		int replyPort = -1;
		int eventPort = -1;
		requestPort = findFreePort();
		eventPort = findFreePort();
		replyPort = findFreePort();
		if(requestPort == -1 || replyPort == -1 || eventPort == -1) {
			this.abort("Unable to find free port", null);
		}

		// The resulting job is used for the creation of MokaRuntimeProcess, thereby simulating a real, physical process
		IProcess process = new MokaProcess(launch, engineJob, "Moka runtime process", new HashMap<String, String>());
		// Initializes the engine as well as the debug target
		MokaDebugTarget target = new MokaDebugTarget(launch, process);
		try {
			engine.init(eObjectToExecute, args, target, requestPort, replyPort, eventPort);
			target.connect(requestPort, replyPort, eventPort);
			launch.addDebugTarget(target);
			engineJob.setDebugTarget(target);
			engineJob.schedule();
		} catch (UnknownHostException e) {
			org.eclipse.papyrus.infra.core.Activator.log.equals(e);
		} catch (IOException e) {
			org.eclipse.papyrus.infra.core.Activator.log.equals(e);
		}
	}

	/**
	 * A convenience method for instantiating the actual execution engine to be used by a given JUnit test suite.
	 *
	 * @return
	 */
	public abstract IExecutionEngine instantiateExecutionEngine();

	public IExecutionEngine getExecutionEngine() {
		return this.engine;
	}

	/**
	 * A convenience method for aborting launching.
	 * This code has been duplicated from the PDAExample. See http://www.eclipse.org/articles/Article-Debugger/how-to.html
	 *
	 * @param message
	 * @param e
	 * @throws CoreException
	 */
	protected void abort(String message, Throwable e) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, MokaConstants.MOKA_DEBUG_MODEL_ID, 0, message, e));
	}

	/**
	 * Returns a free port number on localhost, or -1 if unable to find a free port.
	 * This code has been duplicated from the PDAExample. See http://www.eclipse.org/articles/Article-Debugger/how-to.html
	 *
	 * @return a free port number on localhost, or -1 if unable to find a free port
	 */
	protected static int findFreePort() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(0);
			return socket.getLocalPort();
		} catch (IOException e) {
		} finally {
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		return -1;
	}

}
