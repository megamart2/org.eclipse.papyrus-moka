package org.eclipse.papyrus.moka.fmu.engine.utils;

import java.util.List;

import org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Port;
import org.eclipse.papyrus.moka.fmu.engine.control.FMUControlService;
import org.eclipse.papyrus.moka.fmu.json.JSONSocketClient;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StructuralFeature;

public class FMUEngineUtils {

	protected static FMUControlService fmuControlService ;

	private static JSONSocketClient jsonSocketClient;

	protected static final String PORT_STEREOTYPE_NAME = "Port";
	protected static final String CS_FMU_STEREOTYPE_NAME = "CS_FMU";

	protected static final String PORT_INPUT_DIRECTION = "in";
	protected static final String PORT_OUTPUT_DIRECTION = "out";
	protected static final String PORT_CAUSILITY_ATTRIBUTE = "causality";
	protected static final String PORT_DIRECTION_ATTRIBUTE = "direction" ;

	protected static final String VALUE_REFERENCE_FIELD_NAME = "valueReference" ;



	public static FMUControlService getFMUControlService() {
		return fmuControlService ;
	}

	public static void setFMUControlService(FMUControlService controlService) {
		fmuControlService = controlService ;
	}
	
	public static void setJSONSocketClient(JSONSocketClient server){
		jsonSocketClient = server;
	}
	
	public static JSONSocketClient getJsonSocketClient() {
		
		//TODO improve the way to implement a socket server from the GUI...
		if (jsonSocketClient== null){
			jsonSocketClient = new JSONSocketClient(9090);
		}
		return jsonSocketClient;
	}
}
