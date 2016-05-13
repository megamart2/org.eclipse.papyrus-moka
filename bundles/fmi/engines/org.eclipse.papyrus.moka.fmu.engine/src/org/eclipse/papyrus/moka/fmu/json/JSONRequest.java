package org.eclipse.papyrus.moka.fmu.json;




public class JSONRequest {

	public static final String INIT = "ini";
	public static final String TERMINATE = "ter";
	public static final String STEP_TIME = "t";
	public static final String STEP_SIZE = "h";
	public static final String INT_VRS = "iv";
	public static final String INTS = "i";
	public static final String DOUBLE_VRS = "dv";
	public static final String DOUBLES = "d";
	public static final String BOOL_VRS = "bv";
	public static final String BOOLS = "b";
	public static final String STRING_VRS = "sv";
	public static final String STRINGS = "s";
	
	
	
	
	public boolean init;
	public boolean terminate;
	
	public double t;
	public double h;
	
	public int[] intVRs;
	public int[] ints;
	
	public int[] doubleVRs;
	public double[] doubles;
	
	public int[] boolVRs;
	public boolean[] bools;
	
	public int[] stringVRs;
	public String[] strings;

	
	
}
