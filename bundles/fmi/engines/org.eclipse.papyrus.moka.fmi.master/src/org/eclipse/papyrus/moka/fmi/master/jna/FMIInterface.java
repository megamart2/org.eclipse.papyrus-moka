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
package org.eclipse.papyrus.moka.fmi.master.jna;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.NativeSizeT;

import com.sun.jna.Pointer;

public interface FMIInterface {
	public int setReal( Pointer pointer, int[] vr, NativeSizeT nvr, double[] values);
	public  int getReal(Pointer pointer, int[] vr, NativeSizeT nvr, double[] values);
	public  int doStep(Pointer pointer, double currentTime, double steoSize, boolean noSetPrior) ;
	

}
