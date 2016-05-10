package org.eclipse.papyrus.moka.fmi.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fmi.modeldescription.CausalityType;
import org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiModelDescriptionType;

public class FMIUtil {

	
	public static List<Fmi2ScalarVariable> getScalarVariables(FmiModelDescriptionType modelDescription, CausalityType causality){
		List<Fmi2ScalarVariable> ret = new ArrayList<Fmi2ScalarVariable>();
		if (modelDescription != null && modelDescription.getModelVariables() != null ){
			for (Fmi2ScalarVariable scalarVariable : modelDescription.getModelVariables().getScalarVariable()){
				if (causality.equals(scalarVariable.getCausality())){
					ret.add(scalarVariable);
				}
			}
		}
		return ret;
	}

	public static boolean isPort(Fmi2ScalarVariable variable) {
		
		return variable.getCausality() == CausalityType.INPUT || variable.getCausality() == CausalityType.OUTPUT;
	}
}
