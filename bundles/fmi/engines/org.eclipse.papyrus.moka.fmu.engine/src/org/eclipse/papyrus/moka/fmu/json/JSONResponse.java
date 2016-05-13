package org.eclipse.papyrus.moka.fmu.json;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONResponse {
	public static final String STATUS = "s";

	public String status;
	
	public int[] intVRs;
	public int[] ints;
	
	public int[] doubleVRs;
	public double[] doubles;
	
	public int[] boolVRs;
	public boolean[] bools;
	
	public int[] stringVRs;
	public String[] strings;
	
}
