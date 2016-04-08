package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

public interface Fmi2StatusKind {
	public static final int fmi2DoStepStatus = 0;
	public static final int fmi2PendingStatus = 1;
	public static final int fmi2LastSuccessfulTime = 2;
	public static final int fmi2Terminated = 3;
}
