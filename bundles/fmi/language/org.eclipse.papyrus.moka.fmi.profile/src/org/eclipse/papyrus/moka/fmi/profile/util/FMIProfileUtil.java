/*****************************************************************************
 * 
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.profile.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fmi.fmiprofile.CS_FMU;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfilePackage;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FMU;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Port;
import org.eclipse.papyrus.moka.fmi.fmiprofile.ScalarVariable;
import org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiModelDescriptionType;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StructuralFeature;
import org.eclipse.uml2.uml.util.UMLUtil;

public class FMIProfileUtil {
	public static final String FMI_PROFILE_NAME = "FMIProfile";
	public static final String CS_FMU_STEREO_NAME = FMIProfilePackage.eINSTANCE.getCS_FMU().getName();
	public static final String ME_FMU_STEREO_NAME = FMIProfilePackage.eINSTANCE.getME_FMU().getName();
	public static final String CS_GRAPH_STEREO_NAME = FMIProfilePackage.eINSTANCE.getCS_Graph().getName();
	public static final String CALCULATED_PARAMETER_STEREO_NAME = FMIProfilePackage.eINSTANCE.getCalculatedParameter().getName();
	public static final String PARAMETER_STEREO_NAME = FMIProfilePackage.eINSTANCE.getParameter().getName();
	public static final String LOCAL_STEREO_NAME = FMIProfilePackage.eINSTANCE.getLocal().getName();
	public static final String INDEPENDENT_STEREO_NAME = FMIProfilePackage.eINSTANCE.getIndependent().getName();
	public static final String PORT_STEREO_NAME = FMIProfilePackage.eINSTANCE.getPort().getName();
	public static final String OUTPUT_DEPENDENCY_STEREO_NAME = FMIProfilePackage.eINSTANCE.getOutputDependency().getName();
	public static final String DERIVATIVE_DEPENDENCY_STEREO_NAME = FMIProfilePackage.eINSTANCE.getDerivativeDependency().getName();
	public static final String INITIAL_UNKNWOWN_STEREO_NAME = FMIProfilePackage.eINSTANCE.getInitialUnknownDependency().getName();

	public static final String CS_FMU_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+CS_FMU_STEREO_NAME;
	public static final String ME_FMU_STEREO_QUALIFIED_NAME =FMI_PROFILE_NAME +"::"+ME_FMU_STEREO_NAME;
	public static final String CS_GRAPH_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+CS_GRAPH_STEREO_NAME;
	public static final String CALCULATED_PARAMETER_STEREO_QUALIFIED_NAME =FMI_PROFILE_NAME +"::"+CALCULATED_PARAMETER_STEREO_NAME;
	public static final String PARAMETER_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+PARAMETER_STEREO_NAME;
	public static final String LOCAL_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+LOCAL_STEREO_NAME;
	public static final String INDEPENDENT_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+INDEPENDENT_STEREO_NAME;
	public static final String PORT_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+PORT_STEREO_NAME;
	public static final String OUTPUT_DEPENDENCY_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+OUTPUT_DEPENDENCY_STEREO_NAME;
	public static final String DERIVATIVE_DEPENDENCY_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+DERIVATIVE_DEPENDENCY_STEREO_NAME;
	public static final String INITIAL_UNKNWOWN_STEREO_QUALIFIED_NAME = FMI_PROFILE_NAME +"::"+INITIAL_UNKNWOWN_STEREO_NAME;







	public static void applyFMIProfileIfNeeded(Package owningPackage) {
		Profile fmiProfile = getFMIProfile(owningPackage);

		Iterator<Profile> profileIter = owningPackage.getAllAppliedProfiles().iterator();
		Profile appliedProfile = null;
		while (profileIter.hasNext() && (appliedProfile != fmiProfile)){
			appliedProfile = profileIter.next();
		}
		if(appliedProfile != fmiProfile){
			Package rootPackage = owningPackage.getModel();
			if (rootPackage != null && rootPackage.eResource() == owningPackage.eResource()){
				rootPackage.applyProfile(fmiProfile);
			}else {
				owningPackage.applyProfile(fmiProfile);
			}
		}
	}


	public static Profile getFMIProfile(EObject context){
		return UMLUtil.getProfile(FMIProfilePackage.eINSTANCE, context);
	}

	public static Stereotype getStereotype(EObject context, String shortName){
		Profile fmiProfile = getFMIProfile(context);
		if (fmiProfile != null){
			return fmiProfile.getOwnedStereotype(shortName);
		}
		return null;
	}


	public static Fmi2ScalarVariable getFMIVariable(Property base_Property) {
		if (base_Property != null && base_Property.getName() != null){
			Class owningClass = base_Property.getClass_();
			if (owningClass != null) {
				Stereotype fmuStereotype = owningClass.getAppliedStereotype(CS_FMU_STEREO_QUALIFIED_NAME);
				if (fmuStereotype == null){
					fmuStereotype = owningClass.getAppliedStereotype(ME_FMU_STEREO_QUALIFIED_NAME);
				}
				if (fmuStereotype != null){
					FMU owningFMU = (FMU) owningClass.getStereotypeApplication(fmuStereotype);
					if (owningFMU != null){
						FmiModelDescriptionType modelDesc = owningFMU.getModelDescription();
						if (modelDesc != null && modelDesc.getModelVariables()!= null){
							for (Fmi2ScalarVariable variable : modelDesc.getModelVariables().getScalarVariable()){
								if (base_Property.getName().equals(variable.getName())){
									return variable;
								}
							}
						}
					}
				}
			}
		}
		return null;

	}
	
	
	
	
	public static Boolean isFMUPort(StructuralFeature p) {
		return isStereotypeApplied(PORT_STEREO_NAME, p);
	}

	public static Boolean isCS_FMU(Class c) {
		return isStereotypeApplied(CS_FMU_STEREO_NAME, c) ;
	}
	
	public static Boolean isME_FMU(Class c) {
		return isStereotypeApplied(ME_FMU_STEREO_NAME, c) ;
	}
	
	public static Boolean isFMU(Class c) {
		return isME_FMU(c)|| isCS_FMU(c) ;
	}

	protected static Boolean isStereotypeApplied(String stereotypeName, Element element) {
		return getStereotype(stereotypeName, element) != null ;
	}

	public static int getValueReference(Property fmuPort) { // FIXME should return a long
		Stereotype stereotype = getStereotype(PORT_STEREO_NAME, fmuPort) ;
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
		return (p instanceof org.eclipse.uml2.uml.Port) && FlowDirection.OUT == getDirection((org.eclipse.uml2.uml.Port)p) ;

	}
	
	public static boolean isInputPort(Property p) {
		
		return (p instanceof org.eclipse.uml2.uml.Port) && FlowDirection.IN == getDirection((org.eclipse.uml2.uml.Port)p) ;
	}


	public static FlowDirection getDirection(org.eclipse.uml2.uml.Port port) {
		Stereotype s = getStereotype(PORT_STEREO_NAME, port) ;
		if (s != null) {
			Port fmiPortObj = (Port) port.getStereotypeApplication(s);
			return  fmiPortObj.getDirection();
		}
		return null;
	}

	public static  int computeNewValueReference(Property prop){
		Class owningClass= prop.getClass_();
		List<Integer> valueReferences = new ArrayList<Integer>();
		for (Property attr: owningClass.getAllAttributes()){
			if (attr != prop){
				valueReferences.add(getValueReference(attr));
			}
		}
		if (!valueReferences.isEmpty()){
			return Collections.max(valueReferences) +1;
		}
		return 0;
		
	}


	public static CS_FMU getCSFMU(Class umlClass) {
		Stereotype csFmuStereo = umlClass.getAppliedStereotype(CS_FMU_STEREO_QUALIFIED_NAME);
		if (csFmuStereo != null){
			return (CS_FMU) umlClass.getStereotypeApplication(csFmuStereo);
		}
		return null;
	}


	public static List<ScalarVariable> collectScalarVariables(Class umlClass) {
		List<ScalarVariable> result = new ArrayList<>();
		for (Property prop : umlClass.getAllAttributes()){
			for(Stereotype stereo: prop.getAppliedStereotypes()){
				EObject application = prop.getStereotypeApplication(stereo);
				if (application instanceof ScalarVariable){
					result.add((ScalarVariable) application);
				}
			}
		}
		return result;
	}
	
}
