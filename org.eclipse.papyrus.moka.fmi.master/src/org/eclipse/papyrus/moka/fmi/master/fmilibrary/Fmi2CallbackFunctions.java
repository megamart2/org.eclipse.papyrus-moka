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

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Library.fmi2CallbackAllocateMemory;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Library.fmi2CallbackFreeMemory;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Library.fmi2CallbackLogger;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Library.fmi2StepFinished;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * A class representing the FMI2CallbackFunctions structure
 * defined in FMI2.0 for co-simulation standard.
 * 
 * @author sahar.guermazi@cea.fr
 *
 */
public class Fmi2CallbackFunctions extends Structure {

	public fmi2CallbackLogger logger;
	public fmi2CallbackAllocateMemory allocateMemory;
	public fmi2CallbackFreeMemory freeMemory;
	public fmi2StepFinished stepFinished;
	public Pointer componentEnvironment;

	protected List<?> getFieldOrder() {
		return Arrays.asList(new String[] { "logger", "allocateMemory", "freeMemory",
				"stepFinished", "componentEnvironment" });
	}

	public Fmi2CallbackFunctions() {
		super();
	}

	public Fmi2CallbackFunctions(fmi2CallbackLogger logger,
			fmi2CallbackAllocateMemory allocateMemory,
			fmi2CallbackFreeMemory freeMemory,
			fmi2StepFinished stepFinished,
			Pointer componentEnvironment) {
		super();
		this.logger = logger;
		this.allocateMemory = allocateMemory;
		this.freeMemory = freeMemory;
		this.stepFinished = stepFinished;
		this.componentEnvironment = componentEnvironment;
		setAlignType(Structure.ALIGN_GNUC);
	}

	public static class ByValue extends Fmi2CallbackFunctions
			implements Structure.ByValue {
		public ByValue(fmi2CallbackLogger logger,
				fmi2CallbackAllocateMemory allocateMemory,
				fmi2CallbackFreeMemory freeMemory,
				fmi2StepFinished stepFinished,
				Pointer componentEnvironment) {
			super(logger, allocateMemory, freeMemory, stepFinished, componentEnvironment);
		}
	};
}
