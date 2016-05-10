package org.eclipse.papyrus.moka.fmu.engine;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_Executor;
import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.fmu.engine.semantics.FMULocus;
import org.eclipse.papyrus.moka.fmu.engine.semantics.FMUObject;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.papyrus.moka.fmu.rcp.thrift.ThriftServer;
import org.eclipse.papyrus.moka.service.IMokaService;
import org.eclipse.papyrus.moka.service.MokaServiceRegistry;
import org.eclipse.papyrus.moka.timedfuml.TimedUmlExecutionEngine;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_ExecutionFactory;
import org.eclipse.uml2.uml.Class;

public class MokaEngineForFMUExport extends TimedUmlExecutionEngine {

	@Override
	public void start(IProgressMonitor monitor) {
		if(!mode.equals(OperatingMode.QUIET)){
			// Initialize every service with the parameters of this particular run
			MokaServiceRegistry registry = MokaServiceRegistry.getInstance();
			registry.loadServices();
			for (IMokaService service : registry.getAllServices()) {
				service.init(this.launch, executionEntryPoint);
			}
		}
		Runnable execution = new Runnable() {
			public void run() {
				Class fmuClass = FMUEngineUtils.getFMUControlService().getFmuClass() ; 
				if (fmuClass != null) {
					startFMU(fmuClass);
					DEScheduler.init(-1.0);
					ThriftServer thriftServer = new ThriftServer("9090",FMUEngineUtils.getFMUControlService());
					thriftServer.start();
					FMUEngineUtils.getFMUControlService().waitForTermination();
				}
			}
		};
		Thread mainThread = new Thread(execution, "Moka - Main thread");
		mainThread.start();
	}

	public void startFMU(Class fmuClass) {
		if (fmuClass != null && FMUEngineUtils.isFMU(fmuClass)) {

			// creates the locus, executor and execution factory
			this.locus = new FMULocus();
			locus.setExecutor(new CS_Executor());
			locus.setFactory(new Timed_ExecutionFactory());

			// initializes built-in primitive types
			this.initializeBuiltInPrimitiveTypes(locus);

			// Initializes opaque behavior executions
			this.registerOpaqueBehaviorExecutions(locus);

			// Initializes semantic strategies
			this.registerSemanticStrategies(locus);

			// Initializes system services
			this.registerSystemServices(locus);

			// Finally instantiate the fmu object
			FMUObject fmuObject = (FMUObject)this.locus.instantiate(fmuClass) ;
			FMUEngineUtils.getFMUControlService().setFmuObject(fmuObject);
		}
	}

}
