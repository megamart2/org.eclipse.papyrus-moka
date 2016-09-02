/*****************************************************************************
 * 
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmu.engine.debug;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.fmu.engine.MokaEngineForFMUExport;
import org.eclipse.papyrus.moka.fmu.engine.control.FMUControlService;
import org.eclipse.papyrus.moka.fmu.engine.de.FMIPushPullStrategy;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.papyrus.moka.service.IMokaService;
import org.eclipse.papyrus.moka.service.MokaServiceRegistry;
import org.eclipse.papyrus.moka.timedfuml.actions._displayCurrentTimeAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;

public class MokaEngineForFMUDebug extends MokaEngineForFMUExport {


	
	@Override
	public void stop(IProgressMonitor monitor) {
		super.stop(monitor);
	}

	protected IProgressMonitor monitor ;

	@Override
	public void start(IProgressMonitor monitor) {
		this.monitor = monitor ;
		if(!mode.equals(OperatingMode.QUIET)){
			// Initialize every service with the parameters of this particular run
			MokaServiceRegistry registry = MokaServiceRegistry.getInstance();
			registry.loadServices();
			for (IMokaService service : registry.getAllServices()) {
				service.init(this.launch, executionEntryPoint);
			}
		}
		Class fmuClass = FMUEngineUtils.getFMUControlService().getFmuClass() ; 
		if (fmuClass != null) {
			startFMU(fmuClass);
			_displayCurrentTimeAction action = new _displayCurrentTimeAction() ;
			DEScheduler.init(-1.0, new FMIPushPullStrategy());
			DEScheduler.getInstance().pushPreStepAction(action);
			
			launchMaster();
			FMUControlService controlService = FMUEngineUtils.getFMUControlService() ;
			controlService.waitForTermination();
			
		}
	}

	
	
	
	protected void launchMaster() {
			
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				FMUDebugDialog dialog = new FMUDebugDialog(Display.getDefault().getActiveShell());
				dialog.setBlockOnOpen(false);
				dialog.open();
				
			}
		});
		
		FMUEngineUtils.getFMUControlService().getInstantiationLock().release();	
	
	}	
}