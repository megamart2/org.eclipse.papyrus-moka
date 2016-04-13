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

public interface Fmi2StatusKind {
	public static final int fmi2DoStepStatus = 0;
	public static final int fmi2PendingStatus = 1;
	public static final int fmi2LastSuccessfulTime = 2;
	public static final int fmi2Terminated = 3;
}
