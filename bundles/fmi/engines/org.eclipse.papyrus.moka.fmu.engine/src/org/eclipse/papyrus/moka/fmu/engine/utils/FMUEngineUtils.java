package org.eclipse.papyrus.moka.fmu.engine.utils;

import java.util.List;

import org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Port;
import org.eclipse.papyrus.moka.fmu.engine.control.FMUControlService;
import org.eclipse.papyrus.moka.fmu.json.JSONSocketServer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StructuralFeature;

public class FMUEngineUtils {

	protected static FMUControlService fmuControlService ;

	private static JSONSocketServer jsonSocketServer;

	protected static final String PORT_STEREOTYPE_NAME = "Port";
	protected static final String CS_FMU_STEREOTYPE_NAME = "CS_FMU";

	protected static final String PORT_INPUT_DIRECTION = "in";
	protected static final String PORT_OUTPUT_DIRECTION = "out";
	protected static final String PORT_CAUSILITY_ATTRIBUTE = "causality";

	protected static final String VALUE_REFERENCE_FIELD_NAME = "valueReference" ;

	public static Boolean isFMUPort(StructuralFeature p) {
		return isStereotypeApplied(PORT_STEREOTYPE_NAME, p);
	}

	public static Boolean isFMU(Class c) {
		return isStereotypeApplied(CS_FMU_STEREOTYPE_NAME, c) ;
	}

	protected static Boolean isStereotypeApplied(String stereotypeName, Element element) {
		return getStereotype(stereotypeName, element) != null ;
	}

	public static int getValueReference(Property fmuPort) { // FIXME should return a long
		Stereotype stereotype = getStereotype(PORT_STEREOTYPE_NAME, fmuPort) ;
		if (stereotype != null) {
			Port fmiPortObj = (Port) fmuPort.getStereotypeApplication(stereotype);
			return fmiPortObj.getValueReference();
		}

		return -1 ;
	}

	protected static Stereotype getStereotype(String stereotypeName, Element element) {
		List<Stereotype> appliedStereotypes = element.getAppliedStereotypes() ;
		Stereotype stereotype = null ;
		// FIXME to be consolidated...
		for (int i = 0 ; i < appliedStereotypes.size() && stereotype == null ; i++) {
			if (stereotypeName.equals(appliedStereotypes.get(i).getName())) {
				stereotype = appliedStereotypes.get(i) ;
			}
		}
		return stereotype ;
	}

	public static boolean isOutputPort(Property p) {
		Stereotype s = getStereotype(PORT_STEREOTYPE_NAME, p) ;
		if (s != null) {
			Object o = p.getValue(s, PORT_CAUSILITY_ATTRIBUTE) ;
			FlowDirection fd = (FlowDirection)o; //FIXME Need to be changed if we change from FlowDirection to CausalityType in the profile
			return PORT_OUTPUT_DIRECTION.equals(fd.getLiteral()) ;
		}
		return false ;
	}

	public static boolean isInputPort(Property p) {
		Stereotype s = getStereotype(PORT_STEREOTYPE_NAME, p) ;
		if (s != null) {
			Object o = p.getValue(s, PORT_CAUSILITY_ATTRIBUTE) ;
			FlowDirection fd = (FlowDirection)o; //FIXME Need to be changed if we change from FlowDirection to CausalityType in the profile
			return PORT_INPUT_DIRECTION.equals(fd.getLiteral()) ;
		}
		return false ;
	}

	public static FMUControlService getFMUControlService() {
		return fmuControlService ;
	}

	public static void setFMUControlService(FMUControlService controlService) {
		fmuControlService = controlService ;
	}
	
	public static void setJSONSocketServer(JSONSocketServer server){
		jsonSocketServer = server;
	}
	
	public static JSONSocketServer getJsonSocketServer() {
		
		//TODO improve the way to implement a socket server from the GUI...
		if (jsonSocketServer== null){
			jsonSocketServer = new JSONSocketServer(9090);
		}
		return jsonSocketServer;
	}
}
