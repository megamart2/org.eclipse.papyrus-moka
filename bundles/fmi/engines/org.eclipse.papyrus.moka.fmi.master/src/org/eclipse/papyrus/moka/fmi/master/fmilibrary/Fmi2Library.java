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
package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * An interface representing the functions types and callback functions
 * defined in fmi2.0 for co-simulation standard.
 * Callback functions have to be implemented in the co-simulation environment.
 * 
 * @author sahar.guermazi@cea.fr
 *
 */
public interface Fmi2Library extends Library {

	// public static interface fmi2Status {
	// public static final int fmi2OK = 0;
	// public static final int fmi2Warning = 1;
	// public static final int fmi2Discard = 2;
	// public static final int fmi2Error = 3;
	// public static final int fmi2Fatal = 4;
	// public static final int fmi2Pending = 5;
	// };

	// public static interface fmi2Type {
	// public static final int fmi2ModelExchange = 0;
	// public static final int fmi2CoSimulation = 1;
	// };

	// public static interface fmi2StatusKind {
	// public static final int fmi2DoStepStatus = 0;
	// public static final int fmi2PendingStatus = 1;
	// public static final int fmi2LastSuccessfulTime = 2;
	// public static final int fmi2Terminated = 3;
	// };

	public static final int fmi2True = 1;
	public static final int fmi2False = 0;
	public static final String fmi2TypesPlatform = (String) "default";

	// public static interface fmi2VariableType{
	// public static final String fmi2Real = "Real";
	// public static final String fmi2String = "String";
	// public static final String fmi2Integer = "Integer";
	// public static final String fmi2Boolean = "Boolean";
	// }

	// public static interface fmi2CausalityType{
	// public static final String fmi2Parameter = "parameter";
	// public static final String fmi2CalculatedParameter = "calculatedParameter";
	// public static final String fmi2Input = "input";
	// public static final String fmi2Output = "output";
	// public static final String fmi2Local = "local";
	// public static final String fmi2Independent = "independent";
	// }

	// public static interface fmi2VariabilityType{
	// public static final String fmi2Constant = "constant";
	// public static final String fmi2Fixed = "fixed";
	// public static final String fmi2Tunable = "tunable";
	// public static final String fmi2Discrete = "discrete";
	// public static final String fmi2Continuous = "continuous";
	// }

	// public static interface fmi2InitialType{
	// public static final String fmi2Exact = "exact";
	// public static final String fmi2Approx = "approx";
	// public static final String fmi2Calculated = "calculated";
	// }

	// public static interface fmi2DependencyKind{
	// public static final String fmi2Dependent = "dependent";
	// public static final String fmi2Constant = "constant";
	// public static final String fmi2Fixed = "fixed";
	// public static final String fmi2Tunable = "tunable";
	// public static final String fmi2Discrete = "discrete";
	// }

	/**
	 * An interface to be implemented in order to log information during simulation.
	 * 
	 * @author sahar.guermazi@cea.fr
	 */
	public interface fmi2CallbackLogger extends Callback {
		void invoke(Pointer componentEnvironment, String instanceName, int status, String category, String message, Pointer varArgs1);
	};

	/**
	 * An interface to be implemented in order to
	 * allocate memory for the fmu.
	 * 
	 * @author sahar.guermazi@cea.fr
	 */
	public interface fmi2CallbackAllocateMemory extends Callback {
		Pointer invoke(NativeSizeT nobj, NativeSizeT size);
	};

	/**
	 * An interface to be implemented in order to
	 * free memory after termination of the simulation.
	 * 
	 * @author sahar.guermazi@cea.fr
	 */
	public interface fmi2CallbackFreeMemory extends Callback {
		void invoke(Pointer obj);
	};

	/**
	 * An interface to be implemented in order to
	 * know about a finished step.
	 * 
	 * @author sahar.guermazi@cea.fr
	 */
	public interface fmi2StepFinished extends Callback {
		void invoke(Pointer componentEnvironment, int status);
	};

}

