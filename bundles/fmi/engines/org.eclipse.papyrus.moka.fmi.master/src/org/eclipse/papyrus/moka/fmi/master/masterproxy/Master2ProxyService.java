/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.master.masterproxy;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.StructuredClasses.CS_Reference;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2CallbackFunctions;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2CausalityType;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2InitialType;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Port;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Status;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2VariabilityType;
import org.eclipse.papyrus.moka.fmi.master.fmulibrary.Fmu2Library;
import org.eclipse.papyrus.moka.fmi.master.fmulibrary.Fmu2Status;
import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
import org.eclipse.papyrus.moka.fmi.master.masterlibrary.CoSimEnvironment;
import org.eclipse.papyrus.moka.fmi.master.masterlibrary.DependencyGraph;
import org.eclipse.papyrus.moka.fmi.master.masterlibrary.Experiments;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.registry.service.framework.AbstractService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.StructuralFeature;

public class Master2ProxyService extends AbstractService {

	// status of the co-simulation
	private int fmi2Status = 0;

	private CoSimEnvironment coSimEnv;
	// Inputs to Master algorithm
	private Experiments experiments;
	private DependencyGraph depGraph;
	
	
	private ArrayList<Fmi2Port> variablesOrder = new ArrayList<Fmi2Port>();
	private ArrayList<Fmi2Port> inputVariables = new ArrayList<Fmi2Port>();
//	private HashMap<Fmu2ProxyService, Pointer> m = new HashMap<Fmu2ProxyService, Pointer>();
//	private HashMap<Fmu2ProxyService, Pointer> r = new HashMap<Fmu2ProxyService, Pointer>();

	private Path traceFile;
	PrintWriter logWriter;

	
	public Master2ProxyService(Class service) {
		super(service);

	}

	// prepare the simyulation environment
	public void setupEnvironment() {
		Fmu2Library.cbf = new Fmi2CallbackFunctions.ByValue(new Fmu2Library.FMULogger(),
				new Fmu2Library.FMUAllocateMemory(), new Fmu2Library.FMUFreeMemory(), new Fmu2Library.FMUStepFinished(),
				null);
		this.coSimEnv = new CoSimEnvironment(getOuter().locus.getExtensionalValues());
		coSimEnv.setupPortMapping();
		this.experiments = new Experiments(coSimEnv.getContainer());
		this.depGraph = new DependencyGraph( coSimEnv);
		this.variablesOrder = depGraph.topoSort();
		for (Fmi2Port v : variablesOrder) {
			if (v.getCausality().equals(Fmi2CausalityType.fmi2Input)) {
				inputVariables.add(v);
			}
		}

		initializeTraceFile();

	}

	private void initializeTraceFile() {
		Resource modelRes = this.coSimEnv.getContainer().getTypes().get(0).eResource();
		URI traceURI = modelRes.getURI().trimSegments(1).appendSegment("fmi_simulation_trace.csv");
		try {
			URL resolvedURL = FileLocator.resolve(new URL(traceURI.toString()));

			traceFile = Paths.get(resolvedURL.getFile());
			logWriter =new PrintWriter(traceFile.toFile());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "time";
		for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
			StructuralFeature definingFeature =null;
			for (IFeatureValue featValue :fmu.getDirectContainers().get(0).getFeatureValues()){
				for (IValue value : featValue.getValues()){
					if (value instanceof CS_Reference){
						CS_Reference ref = (CS_Reference) value;
						if (fmu.equals(ref.getCompositeReferent())){
							definingFeature = featValue.getFeature();
							break;
						}
					}
					
				}
				if (definingFeature != null) {
					break;
				}
			}
			String featName = definingFeature!= null? definingFeature.getName()+".": "";
			

			for (Fmi2ScalarVariable output : fmu.variables) {
				line += ";" + featName + output.getName();
			}
		}

		logWriter.println(line);

	}

	// returns the master2proxyService instance if needed
	public Master2ProxyService getOuter() {
		return Master2ProxyService.this;
	}

	/**
	 * instantiateAllFmus : call "fmi2Instantiate" for each fmu in the graph
	 * 
	 * @return return 0 if a problem occurs else return 1;
	 * @param String
	 *            instanceName (modelIdentifier)
	 * @param String
	 *            fmuGUID
	 * @param String
	 *            fmuResourceLocation
	 * @param Boolean
	 *            visible
	 * @param Boolean
	 *            loggingOn
	 */
	protected class InstantiateFmusOperationExecution extends ServiceOperationExecution {

		public InstantiateFmusOperationExecution(Operation operation) {
			super(operation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {

			
			setupEnvironment();
			// for each fmu in the co-simulation graph
			// instantiate
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
				// instantiate
				fmi2Status = fmu.fmi2Instantiate();
				System.out.println(
						"Fmu: " + fmu.types.get(0).getName() + " State: Instantiated... Status: " + fmi2Status);

				// setupExperiments
				fmi2Status = fmu.fmi2SetupExperiments(experiments.getStartTime(), experiments.getStopTime(),
						experiments.getTolerance());
				System.out.println(
						"Fmu: " + fmu.types.get(0).getName() + " State: Experiments set up... Status: " + fmi2Status);

				fmu.fmu2Status = Fmu2Status.instantiated;
				// set variables after instantiation
				// fmi2Status = fmu.fmi2UpdateVariables();
			}

			// if fmi2Status = fmi2status.fatal/error/...
			// terminate
		}

		@Override
		public IValue new_() {
			// TODO Auto-generated method stub
			return new InstantiateFmusOperationExecution(operation);
		}
	}

	/**
	 * initialize fmus
	 */
	protected class InitializeFmusOperationExecution extends ServiceOperationExecution {
		// IntegerValue result = new IntegerValue();
		public InitializeFmusOperationExecution(Operation operation) {
			super(operation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {
			// TODO
			// variable with variability ≠ "constant" that has initial = "exact"
			// or "approx" can be set
			// for each fmu call fmuSetINIVariables
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
				// enter initialization mode
				fmi2Status = fmu.fmi2EnterInitializationMode();
				System.out.println(
						"Fmu: " + fmu.types.get(0).getName() + " State: Initialization mode... Status: " + fmi2Status);
				fmu.fmu2Status = Fmu2Status.underInitializationMode;
			}
				

			//we set first parameters values
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()){
				for (Fmi2ScalarVariable variable : fmu.variables){
					if (variable.getCausality().equals(Fmi2CausalityType.fmi2Parameter)  && !variable.getVariability().equals(Fmi2VariabilityType.fmi2Constant) 
							&& variable.getInitial().equals(Fmi2InitialType.fmi2Exact) && variable.getRuntimeValue() != null){
						fmu.fmi2Set(variable, variable.getRuntimeValue());
					}
				}
			}
			
			//then we propagate initial values according direct dependency graph
			for (Fmi2Port targetPort: inputVariables){
				Fmi2Port sourcePort =targetPort.getDrivingPort();
				// get the value on port sourcePort
				//set value on port u
				Fmu2ProxyService sourceFmu = sourcePort.getFmu();
				Object value = sourceFmu.fmi2Get(sourcePort);
			

				Fmu2ProxyService targetFmu = targetPort.getFmu();
				fmi2Status = targetFmu.fmi2Set(targetPort,value);
				
				
			}
			
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
				fmu.fetchGetCache();
			}
			
			
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
			fmi2Status = fmu.fmi2ExitInitializationMode();
			System.out.println("Fmu: " + fmu.types.get(0).getName() + " State: Exit initialization mode... Status: "
					+ fmi2Status);
			fmu.fmu2Status = Fmu2Status.initialized;
			}
			
		}

		@Override
		public IValue new_() {
			// TODO Auto-generated method stub
			return new InitializeFmusOperationExecution(operation);
		}
	}

	/**
	 * doStepForAllFmus calls fmi2DoStep for each fmu in the graph
	 * 
	 * @return simulationStatus : 0 if a problem occurs, 1 else. *
	 * @param double
	 *            currentCommunicationPoint
	 * @param double
	 *            communicationStepSize : to be received from master model
	 * @param boolean
	 *            noSetFMUStatePriorToCurrentPoint;
	 *
	 */
	protected class DoStepOperationExecution extends ServiceOperationExecution {

	
	
		


		public DoStepOperationExecution(Operation operation) {
			super(operation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {

			System.out.println("Entering in doStep loop");
			double h = experiments.getStepSize();
			
			double systemStartTime = System.currentTimeMillis();
			double simulatedTime = experiments.getStartTime();
			int stepNumber = 0;
			
			//we log values at t=0 after initialization
		
		
			while (simulatedTime < experiments.getStopTime() && fmi2Status == Fmi2Status.fmi2OK) {

				logValues(simulatedTime);
				
				//we do the step
				for (final Fmu2ProxyService fmu : coSimEnv.getFmus()) {
					fmi2Status = fmu.fmi2DoStep(simulatedTime, h, false);
				}
				stepNumber++;
				updateAndPropagateValues();


				simulatedTime += h;
				
			}


			double duration = System.currentTimeMillis() - systemStartTime;
			final int finalSteps = stepNumber;
			  Display.getDefault().asyncExec(new Runnable() {
	               public void run() {
	       			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Simulation success", "Successfully Simulated " + finalSteps + " steps in " +duration+" ms");
	       			
	       			
	               }
	            });
		  
			
			IBooleanValue isFinished = new BooleanValue();
			isFinished.setValue(true);
			getOutputParameterValues().get(0).getValues().add(isFinished);


		}

		

		@Override
		public IValue new_() {

			return new DoStepOperationExecution(operation);
		}

	}

	/** Round to tolerance */
	public double round(double value) {
		int pow = (int) Math.round(1 / experiments.getTolerance()); // If
																	// tolerance
																	// is 10^-6,
																	// pow is
																	// 1000000
																	// (10^6)
		int places = (int) Math.ceil(Math.log10(pow)); // If pow is 10^6, places
														// is 6

		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * terminateSimulation
	 * 
	 * @author SG239226
	 *
	 */
	protected class TerminateSimulationOperationExecution extends ServiceOperationExecution {

		public TerminateSimulationOperationExecution(Operation operation) {
			super(operation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doBody(List<IParameterValue> inputParameters, List<IParameterValue> outputParameters) {
			if ((fmi2Status != Fmi2Status.fmi2Error) && (fmi2Status != Fmi2Status.fmi2Fatal)) {
				for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
					fmi2Status = fmu.fmi2Terminate();
					System.out.println("Fmu" + fmu.types.get(0).getName() + " terminates...");
				}
			}
			if (fmi2Status != Fmi2Status.fmi2Fatal) {
				for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
					fmu.fmi2FreeInstance();
					System.out.println("Fmu" + fmu.types.get(0).getName() + " free memory...");
				}
			}
		
			System.out.println("Saving simulation trace in file : " + traceFile.toString());


			logWriter.close();
			
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()){
				fmu.dll_lib.dispose();
				
			}
		}

		@Override
		public IValue new_() {
			// TODO Auto-generated method stub
			return new TerminateSimulationOperationExecution(operation);
		}
	}

	@Override
	public void doOperationExecutionMapping() {
		Class type = this.types.get(0);
		for (Operation operation : type.getAllOperations()) {
			if (operation.getName().equals("instantiateFmus")) {
				this.operationExecution.put(operation, new InstantiateFmusOperationExecution(operation));
			} else if (operation.getName().equals("initializeFmus")) {
				this.operationExecution.put(operation, new InitializeFmusOperationExecution(operation));
			} else if (operation.getName().equals("doStep")) {
				this.operationExecution.put(operation, new DoStepOperationExecution(operation));
			} else if (operation.getName().equals("terminateSimulation")) {
				this.operationExecution.put(operation, new TerminateSimulationOperationExecution(operation));
			}
		}

	}
	
	
	private void updateAndPropagateValues() {

		//we get the new values
		for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
				fmu.fetchGetCache();	
		}
			
		//we propagate locally the values
		for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
			for (Fmi2Port input : fmu.inputPorts) {
				input.updateRuntimeValue();
			}
		}
		
		//we send the new values to FMU
		for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
			fmu.flushSetCache();
		}

	}

	private void logValues(double currentTime) {

		StringBuilder lineBuiler = new StringBuilder("" + currentTime);
		for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
			for (Fmi2ScalarVariable variable : fmu.variables) {
				lineBuiler.append(";").append(variable.getRuntimeValue());
			}
		}
		logWriter.println(lineBuiler.toString());

	}

}
