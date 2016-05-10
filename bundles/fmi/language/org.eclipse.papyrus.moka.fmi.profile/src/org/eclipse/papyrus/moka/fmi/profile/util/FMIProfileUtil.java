package org.eclipse.papyrus.moka.fmi.profile.util;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfilePackage;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FMU;
import org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiModelDescriptionType;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
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
	public static final String INITIAL_UNKNWOWN_STEREO_NAME = FMIProfilePackage.eINSTANCE.getInitialUnknown().getName();

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
}
