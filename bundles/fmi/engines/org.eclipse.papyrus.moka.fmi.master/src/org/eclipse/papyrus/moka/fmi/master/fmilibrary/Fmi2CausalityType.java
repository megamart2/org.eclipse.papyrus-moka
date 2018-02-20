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

public interface Fmi2CausalityType {
	public static final String fmi2Parameter = "parameter";
	public static final String fmi2CalculatedParameter = "calculatedParameter";
	public static final String fmi2Input = "input";
	public static final String fmi2Output = "output";
	public static final String fmi2Local = "local";
	public static final String fmi2Independent = "independent";
}
