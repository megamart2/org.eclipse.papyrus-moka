package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

public interface Fmi2Status {
	public static final int fmi2OK = 0;
	public static final int fmi2Warning = 1;
	public static final int fmi2Discard = 2;
	public static final int fmi2Error = 3;
	public static final int fmi2Fatal = 4;
	public static final int fmi2Pending = 5;
}
