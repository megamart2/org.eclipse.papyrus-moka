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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2CallbackFunctions;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2CausalityType;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Port;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Status;
import org.eclipse.papyrus.moka.fmi.master.fmulibrary.Fmu2Library;
import org.eclipse.papyrus.moka.fmi.master.fmulibrary.Fmu2Status;
import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
import org.eclipse.papyrus.moka.fmi.master.masterlibrary.CoSimEnvironment;
import org.eclipse.papyrus.moka.fmi.master.masterlibrary.DependencyGraph;
import org.eclipse.papyrus.moka.fmi.master.masterlibrary.Experiments;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.registry.service.framework.AbstractService;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;

import com.sun.jna.Pointer;

public class Master2ProxyService extends AbstractService {


	// status of the co-simulation
	private int fmi2Status = 0;

	private CoSimEnvironment coSimEnv;
	// Inputs to Master algorithm
	private Experiments experiments;
	private DependencyGraph depGraph;
	private HashMap<Fmi2Port, Fmi2Port> P = new HashMap<Fmi2Port, Fmi2Port>(); // <input port, output port>
	private ArrayList<Fmi2Port> variablesOrder = new ArrayList<Fmi2Port>();
	private ArrayList<Fmi2Port> inputVariables = new ArrayList<Fmi2Port>();
	private HashMap<Fmu2ProxyService, Pointer> m = new HashMap<Fmu2ProxyService, Pointer>();
	private HashMap<Fmu2ProxyService, Pointer> r = new HashMap<Fmu2ProxyService, Pointer>();


	private Path traceFile;
	List<String> traceLines = new ArrayList<String>();
	private long startTime;
	private int stepNumber;

	public Master2ProxyService(Class service) {
		super(service);

	}

	// prepare the simyulation environment
	public void setupEnvironment() {
		Fmu2Library.cbf = new Fmi2CallbackFunctions.ByValue(
				new Fmu2Library.FMULogger(), new Fmu2Library.FMUAllocateMemory(),
				new Fmu2Library.FMUFreeMemory(),
				new Fmu2Library.FMUStepFinished(),
				null);
		this.coSimEnv = new CoSimEnvironment(getOuter().locus.getExtensionalValues());
		this.P = coSimEnv.setupPortMapping();
		this.experiments = new Experiments(coSimEnv.getContainer());
		this.depGraph = new DependencyGraph(P, coSimEnv.getIoDependencies());
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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "time";
		for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
			for (Fmi2ScalarVariable output : fmu.variables) {
				line += ";" + output.getFmu().getTypes().get(0).getName() + "." + output.getName();
			}
		}



		traceLines.add(line);

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
		public void doBody(List<IParameterValue> inputParameters,
				List<IParameterValue> outputParameters) {

			startTime = System.currentTimeMillis();
			setupEnvironment();
			// for each fmu in the co-simulation graph
			// instantiate
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
				// instantiate
				fmi2Status = fmu.fmi2Instantiate();
				System.out.println("Fmu: " + fmu.types.get(0).getName() + " State: Instantiated... Status: " + fmi2Status);

				// setupExperiments
				fmi2Status = fmu.fmi2SetupExperiments(experiments.getStartTime(), experiments.getStopTime(), experiments.getTolerance());
				System.out.println("Fmu: " + fmu.types.get(0).getName() + " State: Experiments set up... Status: " + fmi2Status);

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
		public void doBody(List<IParameterValue> inputParameters,
				List<IParameterValue> outputParameters) {
			// TODO
			// variable with variability ≠ "constant" that has initial = "exact" or "approx" can be set
			// for each fmu call fmuSetINIVariables
			for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
				// enter initialization mode
				fmi2Status = fmu.fmi2EnterInitializationMode();
				System.out.println("Fmu: " + fmu.types.get(0).getName() + " State: Initialization mode... Status: " + fmi2Status);
				fmu.fmu2Status = Fmu2Status.underInitializationMode;
				// set variables at initialization mode
				fmi2Status = fmu.fmi2UpdateVariables();
				// exit initilization mode
				fmi2Status = fmu.fmi2ExitInitializationMode();
				System.out.println("Fmu: " + fmu.types.get(0).getName() + " State: Exit initialization mode... Status: " + fmi2Status);
				fmu.fmu2Status = Fmu2Status.initialized;
			}

			if (coSimEnv.getFmus().size() < 2) {
				System.out.print("Step: " + experiments.getCurrent_time() + "\t");
				for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
					for (Fmi2ScalarVariable var : fmu.variables) {
						System.out.print(var.getName() + "," + fmu.fmi2Get(var) + "\t");
					}
					System.out.println();
				}
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

		IBooleanValue isFinished = new BooleanValue();
		double h = 0, h1 = 0, hmax = 0;
		double current_time = 0;
		Object v;
		Fmu2ProxyService c;
		Pointer state = null;

		public DoStepOperationExecution(Operation operation) {
			super(operation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doBody(List<IParameterValue> inputParameters,
				List<IParameterValue> outputParameters) {




			isFinished.setValue(false);
			// ExecutorService executor = Executors.newFixedThreadPool(4);
			// ExecutorService executor = Executors.newCachedThreadPool();
			// CompletionService<String> pool = new ExecutorCompletionService<String>(executor);
			while (isFinished.getValue() == false) {

				current_time = experiments.getCurrent_time();
				stepNumber++;
				logValues();

				for (Fmi2Port u : inputVariables) {
					Fmi2Port y = P.get(u);
					// get the value on port y
					// set value on port u
					c = y.getFmu();
					v = c.fmi2Get(y);

					// System.out.println("t:"+ current_time + "\tFMU: "+ c.getParameters().getModelName()+ "\t output: " + y.getName() + "\t value: " + v.toString());
					// fmi2Status = c.fmi2GetFMUstate();
					m.put(c, c.fmuState);

					c = u.getFmu();
					fmi2Status = c.fmi2Set(u, v);
					// fmi2Status = c.fmi2GetFMUstate();
					m.put(c, c.fmuState);

				}

				// for (Fmu2ProxyService c : coSimEnv.getFmus()){
				// fmi2Status = c.fmi2UpdateVariables();
				// }
				// save all fmu states to enable rollback
				r = m;
				hmax = experiments.getStepSize();
				h = hmax;
				// find h acceptable by all FMUs
				for (final Fmu2ProxyService c : coSimEnv.getFmus()) {
					// pool.submit(new Callable<String>() {
					//
					// @Override
					// public String call() throws Exception {
					fmi2Status = c.fmi2DoStep(current_time, h, false);
					// return null;
					// }
					// });



					// fmi2Status = c.fmi2DoStep(current_time, h, false); //FIXME noSetPrior
					// System.out.println(c.getParameters().getModelIdentifier() +"\t" + fmi2Status);
					// if the slave computed successfully only a subinterval of the step
					// if ((fmi2Status == Fmi2Status.fmi2Discard)||(fmi2Status==Fmi2Status.fmi2Error)){
					// h1 = round(c.fmi2GetRealStatus(Fmi2StatusKind.fmi2LastSuccessfulTime)-current_time);
					// h = Math.min(h, h1);
					// }
					// fmi2Status = c.fmi2GetFMUstate();
					// m.put(c, c.fmuState);

				}
				// for(int i = 0; i < coSimEnv.getFmus().size(); i++){
				// try {
				// String result = pool.take().get();
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (ExecutionException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				// //Compute the result
				// }
				// if (h<hmax){
				// for (Fmu2ProxyService c : coSimEnv.getFmus()){
				// fmi2Status = c.fmi2SetFMUstate(r.get(c));
				// fmi2Status = c.fmi2DoStep(current_time, h, true);
				// //if the slave computed successfully only a subinterval of the step
				// //fmi2Status = c.fmi2GetFMUstate();
				// //m.put(c, c.fmuState);
				//
				// }
				// }

				experiments.setCurrent_time(round(current_time + h));

				if ((experiments.getCurrent_time() < experiments.getStopTime()) && (fmi2Status == Fmi2Status.fmi2OK)) {
					isFinished.setValue(false);
					// advance time and check if simulation is finished
				} else {
					logValues();
					for (Fmi2Port u : inputVariables) {

						Fmi2Port y = P.get(u);
						// get the value on port y
						// set value on port u
						c = y.getFmu();
						v = c.fmi2Get(y);
						// line+=";"+v;

						// state = c.fmi2GetFMUstate();
						// m.put(c, state);

						c = u.getFmu();
						fmi2Status = c.fmi2Set(u, v);
						// state = c.fmi2GetFMUstate();
						// m.put(c, state);
						// System.out.println("t:"+ experiments.getCurrent_time() + "\tFMU: "+ c.getParameters().getModelName()+ "\t output: " + y.getName() + "\t value: " + v.toString());
					}
					// traceLines.add(line);
					isFinished.setValue(true);
				}

				if (coSimEnv.getFmus().size() < 2) {
					System.out.print("Step: " + experiments.getCurrent_time() + "\t");
					for (Fmu2ProxyService fmu : coSimEnv.getFmus()) {
						for (Fmi2ScalarVariable var : fmu.variables) {
							// System.out.print(var.getName()+","+fmu.fmi2Get(var)+"\t");
						}
						// System.out.println();
					}
				}

			}


			outputParameters.get(0).getValues().add(isFinished);
		}

		private void logValues() {

			// StringBuilder lineBuiler = new StringBuilder(""+ current_time);
			// for (Fmu2ProxyService fmu : coSimEnv.getFmus()){
			// for (Fmi2ScalarVariable output : fmu.variables){
			// lineBuiler.append(";").append(fmu.fmi2Get(output));
			// }
			// }
			// traceLines.add(lineBuiler.toString());

		}

		@Override
		public IValue new_() {

			return new DoStepOperationExecution(operation);
		}

	}

	/** Round to tolerance */
	public double round(double value) {
		int pow = (int) Math.round(1 / experiments.getTolerance()); // If tolerance is 10^-6, pow is 1000000 (10^6)
		int places = (int) Math.ceil(Math.log10(pow)); // If pow is 10^6, places is 6

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
		public void doBody(List<IParameterValue> inputParameters,
				List<IParameterValue> outputParameters) {
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
			System.out.println("Simulated " + stepNumber + " steps in " + (System.currentTimeMillis() - startTime) + " ms");
			System.out.println("Saving simulation trace in file : " + traceFile.toString());


			try {
				Files.write(traceFile, traceLines, Charset.forName("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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


}



