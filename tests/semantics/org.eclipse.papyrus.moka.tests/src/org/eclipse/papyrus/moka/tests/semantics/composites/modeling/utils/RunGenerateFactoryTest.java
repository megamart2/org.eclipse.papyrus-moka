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

package org.eclipse.papyrus.moka.tests.semantics.composites.modeling.utils;

import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.Launch;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.moka.composites.utils.handlers.GenerateFactoryHandler;
import org.eclipse.papyrus.moka.tests.AbstractMokaLaunchConfigurationDelegate;
import org.eclipse.papyrus.moka.tests.Activator;
import org.eclipse.papyrus.moka.tests.semantics.AbstractMokaTest;
import org.eclipse.papyrus.moka.tests.semantics.async.fuml.AsyncFUMLEngineLaunchDelegate;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * A test case for testing the generation of factory 
 * (See https://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution#Your_first_executable_model)  
 * 
 * @author Bruno Marques - CEA LIST
 *
 */
public class RunGenerateFactoryTest extends AbstractMokaTest {
	
	protected final String ACTIVITY_NAME = "Tester_Factory"; //$NON-NLS-1$
	
	protected final String RESOURCES_PATH = "/resources/Modeling Utils"; //$NON-NLS-1$

	protected final String MODEL_NAME = "/model"; //$NON-NLS-1$
	
	protected final String CLASS_NAME = "Tester";
	
	protected final String PACKAGE_NAME = "GenerateFactoryTest";
	
	protected Class class4Factory;

	@Override
	@Test
	public void runTest()  throws CoreException, InterruptedException {
		/* 1. Get Class  */
		//get the rootModel
		Assert.assertNotNull("RootModel is null", getRootUMLModel()); //$NON-NLS-1$
		//get all semantic element that will handled
		model = (Model)getRootUMLModel();
		
		PackageableElement packageElement = model.getPackagedElement(this.PACKAGE_NAME);
		
		for (int i = 0; i < packageElement.getOwnedElements().size(); i++) {
			Element cddElement = packageElement.getOwnedElements().get(i);
			if (cddElement instanceof Class) {
				Class cddClass = (Class)cddElement;
				if (cddClass.getName().equals(this.CLASS_NAME)) {
					class4Factory = cddClass;
				}
			}
		}
			
		/* 2. Generate factory for the class */
		TransactionalEditingDomain domain = (TransactionalEditingDomain)EMFHelper.resolveEditingDomain(class4Factory);
		GenerateFactoryHandler factoryHandler = new GenerateFactoryHandler();
		domain.getCommandStack().execute(factoryHandler.getUpdateCommand(class4Factory, domain));
		
		for(Iterator<Behavior> i = class4Factory.getOwnedBehaviors().iterator(); i.hasNext() && testCaseActivity == null;) {
			Behavior cddFactory = i.next();
			String cddName = cddFactory.getName() == null ? "" : cddFactory.getName();
			if(cddFactory instanceof Activity && cddName.equals(this.ACTIVITY_NAME)) {
				testCaseActivity = (Activity)cddFactory;
			}
		}
	
		/* 3. Execute it */
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

	@Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	}
	
	@Override
	public String getResourcesPath() {
		return RESOURCES_PATH;
	}

	@Override
	public String getModelName() {
		return MODEL_NAME;
	}

	@Override
	public AbstractMokaLaunchConfigurationDelegate getLaunchDelegate() {
		return new AsyncFUMLEngineLaunchDelegate();
	}
}
