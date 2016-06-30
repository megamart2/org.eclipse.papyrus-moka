package org.eclipse.papyrus.moka.fmu.json;

import java.util.ArrayList;

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
	
	public ArrayList<Integer> intVRs=new  ArrayList<Integer> ();
	public ArrayList<Integer> ints = new  ArrayList<Integer> ();
	
	public ArrayList<Integer> doubleVRs=new  ArrayList<Integer> ();
	public ArrayList<Double> doubles=new  ArrayList<Double>();
	
	public  ArrayList<Integer> boolVRs=new  ArrayList<Integer> ();
	public ArrayList<Boolean> bools=new  ArrayList<Boolean>();
	
	public  ArrayList<Integer> stringVRs=new  ArrayList<Integer> ();
	public ArrayList<String> strings=new  ArrayList<String> ();

	
	
}
