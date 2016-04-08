package org.eclipse.papyrus.moka.fmi.master.fmulibrary;

public interface Fmu2Status {
	public static final int start = 0;
	public static final int instantiated = 1;
	public static final int underInitializationMode = 2;
	public static final int initialized = 3;
	public static final int stepComplete = 4;
	public static final int stepfailed = 5;
	public static final int stapCanceled = 6;
	public static final int terminated = 7;
	public static final int error = 8;
	public static final int fatal = 9;
}
