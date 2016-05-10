package org.eclipse.papyrus.moka.fmu.rcp.fmu;

import java.util.Map;

public interface FMUInterface {
	
	

	public Map<Integer, Double> fmiGetReals();
	public Map<Integer, Integer> fmiGetIntegers();
	public Map<Integer, Boolean>  fmiGetBools();
	public Map<Integer, String> fmiGetStrings();
	
	
	public void fmiSetReals(Map<Integer, Double> realsMap);
	public void fmiSetIntegers(Map<Integer, Integer> integersMap);
	public void fmiSetBools(Map<Integer, Boolean> boolsMap);
	public void fmiSetStrings(Map<Integer, String> stringsMap);
	
	
	public void doStep(double currentCommunicationTime, double stepSize);
	public void init();
	public void terminate();
	void updateMaps();
	
}
