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
package org.eclipse.papyrus.moka.fmi.master.fmulibrary;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Library;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.NativeSizeT;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public interface Fmu2CallbackFunctions extends Fmi2Library {

	/**
	 * A class implementing the fmi2CallbackLogger defined in FMI2Library.java.
	 * TODO call logger to trace information during simulation.
	 * 
	 * @author sahar.guermazi@cea.fr
	 *
	 */
	public static class FMULogger implements fmi2CallbackLogger {
		public void invoke(Pointer componentEnvironment, String instanceName,
				int status, String category, String message,
				Pointer varArgs1) {
			// TODO Auto-generated method stub
			System.out.println(message);
		}
	}

	/**
	 * A class implementing the fmi2CallbackAllocateMemory defined in FMI2Library.java.
	 * Allocate memory that will be used to instantiate an FMU.
	 * 
	 * @author sahar.guermazi@cea.fr
	 *
	 */
	public static class FMUAllocateMemory implements fmi2CallbackAllocateMemory {
		public Pointer invoke(NativeSizeT numberOfObjects, NativeSizeT size) {
			int numberOfObjectsValue = numberOfObjects.intValue();
			if (numberOfObjectsValue <= 0) {
				numberOfObjectsValue = 1;
			}
			Memory memory = new Memory(numberOfObjectsValue * size.intValue());
			// FIXME: not sure about alignment.
			Memory alignedMemory = memory.align(4);
			memory.clear();
			Pointer pointer = alignedMemory.share(0);
			pointers.add(pointer);

			return pointer;
		}

		public static Set<Pointer> pointers = new HashSet<Pointer>();
	}

	/**
	 * A class implementing the fmi2CallbackFreeMemory defined in FMI2Library.java.
	 * free memory that was allocated at the beginning of the simulation.
	 * 
	 * @author sahar.guermazi@cea.fr
	 *
	 */
	public static class FMUFreeMemory implements fmi2CallbackFreeMemory {
		public void invoke(Pointer object) {
			FMUAllocateMemory.pointers.remove(object);
		}
	}

	/**
	 * A class implementing the fmi2CallbackFreeMemory defined in FMI2Library.java.
	 * TODO Add instructions to know about a finished step.
	 * 
	 * @author sahar.guermazi@cea.fr
	 *
	 */
	public static class FMUStepFinished implements fmi2StepFinished {
		public void invoke(Pointer fmiComponent, int status) {
			// FIXME: More should be done here.
			System.out.println("\n Java fmiStepFinished: " + fmiComponent + " "
					+ status);
		}
	}


}
