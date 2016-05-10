package org.eclipse.papyrus.moka.fmu.rcp.fmu.example;

import org.eclipse.papyrus.moka.fmu.rcp.fmu.AbstractFMU;

public class FMUC1 extends AbstractFMU {


	
	private Boolean openClose;
	private double endOfStep;


	private static final int OPEN_CLOSE_VALUE_REF = 0;
	private static final int END_OF_STEP_VALUE_REF = 1;
	

	@Override
	public void doStep(double currentCommunicationTime, double stepSize) {

		endOfStep =currentCommunicationTime + stepSize; 
		if (endOfStep >= 5.2){
			openClose = true;
		}else if (endOfStep >=3.2){
			openClose = false;
		}else if (endOfStep >=0.5){
			openClose = true;
		}
	
	}

	@Override
	public void updateMaps() {
		booleanMap.put(OPEN_CLOSE_VALUE_REF,openClose);
		realMap.put(END_OF_STEP_VALUE_REF, endOfStep);
	}

	
	public void init() {
		openClose=false;
		endOfStep = 0.0;
	}	

}
