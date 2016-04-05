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

package org.eclipse.papyrus.moka.tests.semantics;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.papyrus.infra.core.resource.AbstractBaseModel;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.papyrus.moka.tests.AbstractMokaLaunchConfigurationDelegate;
import org.eclipse.papyrus.moka.tests.Activator;
import org.eclipse.papyrus.moka.utils.constants.MokaConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A generic test class for Moka.
 * It factorizes mechanisms to start a Moka execution from a launch configuration.
 * 
 * @author CEA LIST
 *
 */
public abstract class AbstractMokaTest extends AbstractEditorTest {

	public static final String PROJECT_NAME = "org.eclipse.papyrus.moka.tests"; //$NON-NLS-1$

	/**
	 * The model containing the test case activity
	 */
	protected Model model;

	/**
	 * The main test case activity
	 */
	protected Activity testCaseActivity;

	/**
	 * This method initialize the model element from the project.
	 * First, it creates a new project and copy papyrus file (.di, .notation, .uml).
	 * Then, it opens the model.
	 *
	 */
	@Before
	public void initModelForTestReport() {
		try {
			MokaConstants.MOKA_AUTOMATIC_ANIMATION = false;
			MokaConstants.SILENT_MODE = true;
			initModel(PROJECT_NAME, getModelName(), Activator.getDefault().getBundle());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Override
	protected String getSourcePath() {
		return getResourcesPath();
	}

	@Test
	/**
	 * This is the "@Test" method.
	 * First, gets the model loaded in the model explorer,
	 * Then, gets the activity to execute,
	 * At the end, creates a launch configuration and executes it by the Moka engine.
	 *
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public void runTest() throws CoreException, InterruptedException {
		/* 1. Get Activity to Execute */
		//get the rootModel
		Assert.assertNotNull("RootModel is null", getRootUMLModel()); //$NON-NLS-1$
		//get all semantic element that will handled
		model = (Model)getRootUMLModel();

		//get activity to execute
		testCaseActivity = (org.eclipse.uml2.uml.Activity)model.getPackagedElement(this.getActivityName());

		/* 2. Execute it */
		// define launch configuration
		ILaunchConfiguration configurations[] = getLaunchConfgurations(project);
		ILaunch launch = new Launch(configurations[0], "debug", null);

		// Run Moka Execution with launch configuration
		AbstractMokaLaunchConfigurationDelegate mokaExecution = this.getLaunchDelegate();
		mokaExecution.launch(configurations[0], "debug", launch, new NullProgressMonitor());

		// Wait till moka execution thread is terminated
		while(!launch.isTerminated()) {
			if(Display.getCurrent() != null) {
				try {
					if(!Display.getCurrent().readAndDispatch()) {
						Thread.sleep(100);
					}
				} catch (InterruptedException ex) {
					throw ex;
				} catch (Exception ex) {
					Activator.log.error(ex); //Exception in a runnable. Log and keep going
				}

			} else {
				Thread.sleep(100);
			}
		}
	}

	/**
	 * This method returns the root package element of the model.
	 *
	 */
	@Override
	protected Package getRootUMLModel() {
		IModel umlIModel;
		try {
			umlIModel = getModelSet().getModel("org.eclipse.papyrus.infra.core.resource.uml.UmlModel");

			AbstractBaseModel umlModel = null;
			if(umlIModel instanceof AbstractBaseModel) {
				umlModel = (AbstractBaseModel)umlIModel;
			}

			Assert.assertFalse("umlRessource contains nothing", umlModel.getResource().getContents().size() < 1);
			Object root = umlModel.getResource().getContents().get(0);
			Assert.assertFalse("the root of UML model is not a package", root instanceof Stereotype);
			Assert.assertTrue("the root of UML model is not a stereotype", root instanceof Package);
			return (org.eclipse.uml2.uml.Package)root;
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return null;

	}

	/**
	 * This method create a launch configuration for selected model
	 *
	 * @param resource
	 *        : the model
	 * @return list of launch configuration
	 * @throws CoreException
	 */
	protected ILaunchConfiguration[] getLaunchConfgurations(IResource resource) throws CoreException {
		ILaunchConfiguration configurations[];
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.papyrus.moka.launchConfiguration");

		ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, "MOKA JUNIT TESTS");
		configuration.setAttribute(MokaConstants.URI_ATTRIBUTE_NAME, model.eResource().getURI().toString());
		configuration.setAttribute(MokaConstants.FRAGMENT_ATTRIBUTE_NAME, testCaseActivity.eResource().getURIFragment(testCaseActivity));
		configuration.setAttribute(MokaConstants.ARGS_ATTRIBUTE_NAME, "");

		// save and return new configuration
		configuration.doSave();
		configurations = new ILaunchConfiguration[]{ configuration };

		return configurations;
	}

	/**
	 * Returns the name of the main activity to execute
	 *
	 * @return the name of the main activity to execute
	 */
	public abstract String getActivityName();

	/**
	 * Returns the path of the resources folder, which contains the base UML model of the test case 
	 *
	 * @return the path of the resources folder
	 */
	public abstract String getResourcesPath();
	
	/**
	 * Returns the name of the model
	 *
	 * @return the name of the model
	 */
	public abstract String getModelName();

	/**
	 * Returns the launch configuration delegate to be used for this test
	 *
	 * @return the launch configuration delegate to be used for this test
	 */
	public abstract AbstractMokaLaunchConfigurationDelegate getLaunchDelegate();
}
