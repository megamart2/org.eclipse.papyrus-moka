package org.eclipse.papyrus.moka.fmu.rcp.fmu.example;

import org.eclipse.papyrus.moka.fmu.rcp.fmu.AbstractFMU;

public class TestFMU extends AbstractFMU {


	private Double v1 ;
	private Boolean v2;
	private Integer v3;


	private static final int V1_VALUE_REF = 0;
	private static final int V2_VALUE_REF = 1;
	private static final int V3_VALUE_REF = 2;

	@Override
	public void doStep(double currentCommunicationTime, double stepSize) {

		v1 += stepSize;
		v2 = !v2;
		v3++;
	
	}

	@Override
	public void updateMaps() {
		realMap.put(V1_VALUE_REF, v1);
		integerMap.put(V3_VALUE_REF, v3);
		booleanMap.put(V2_VALUE_REF,v2);

	}

	
	public void init() {
		v1 =0.0;
		v2= true;
		v3=0;
	}	

}
