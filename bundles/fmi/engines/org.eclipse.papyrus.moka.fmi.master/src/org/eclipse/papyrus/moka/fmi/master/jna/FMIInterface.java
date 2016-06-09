package org.eclipse.papyrus.moka.fmi.master.jna;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.NativeSizeT;

import com.sun.jna.Pointer;

public interface FMIInterface {
	public int setReal( Pointer pointer, int[] vr, NativeSizeT nvr, double[] values);
	public  int getReal(Pointer pointer, int[] vr, NativeSizeT nvr, double[] values);
	public  int doStep(Pointer pointer, double currentTime, double steoSize, boolean noSetPrior) ;
	

}
