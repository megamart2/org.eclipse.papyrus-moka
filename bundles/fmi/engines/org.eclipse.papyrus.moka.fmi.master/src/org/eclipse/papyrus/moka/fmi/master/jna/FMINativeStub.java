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

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class FMINativeStub implements FMIInterface{
	
	
	
	public FMINativeStub(String dllPath){
		Native.register(dllPath);
    }
	
	
	
	public native int fmi2SetReal( Pointer pointer, int[] vr, NativeSizeT nvr, double[] values);
	public native int fmi2GetReal(Pointer pointer, int[] vr, NativeSizeT nvr, double[] values);
	public native int fmi2DoStep(Pointer pointer, double currentTime, double steoSize, boolean noSetPrior) ;
	
	@Override
	public int setReal(Pointer pointer, int[] vr, NativeSizeT nvr, double[] values) {
		
		return fmi2SetReal(pointer, vr, nvr, values);
	}
	@Override
	public int getReal(Pointer pointer, int[] vr, NativeSizeT nvr, double[] values) {
		return fmi2GetReal(pointer, vr, nvr, values);
	}
	@Override
	public int doStep(Pointer pointer, double currentTime, double steoSize, boolean noSetPrior) {
		return fmi2DoStep(pointer, currentTime, steoSize, noSetPrior);
	}
	
}
