/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.datavisualization.util;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.moka.datavisualization.profile.DataSource;
import org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.VisualizationPackage;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

public class VisualizationUtil {

	private static final String DEFAULT_DATATYPE_NAME = "CSVResource";
	
	public static final String VISUALIZATION_PROFILE_URI = "pathmap://PAPYRUS_MOKA_DATA_VISUALIZATION/datavisualization.profile.uml";
	public static final String DATA_SOURCE_STEREO_NAME = "DataSource";
	public static final String VARIABLE_STEREO_NAME = "Variable";
	public static final String VISUALIZATION_PROFILE_NAME="DataVisualizationProfile";

	
	public static final String DATA_SOURCE_STEREO_QUALIFIED_NAME = VISUALIZATION_PROFILE_NAME+"::"+DATA_SOURCE_STEREO_NAME;
	public static final String VARIABLE_STEREO__QUALIFIED_NAME = VISUALIZATION_PROFILE_NAME+"::"+VARIABLE_STEREO_NAME;

	public static final String PRIMITIVE_TYPE_URI = "pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml";
	public static final String REAL_NAME = "Real";
	public static final String BOOLEAN_NAME = "Boolean";
	public static final String INTEGER_NAME = "Integer";
	public static final String STRING_NAME = "String";
	
	

	public static DataType createOwnedDataType(Package owningPackage, DataValueSet valueSet){
		
		applyVisualizationProfileIfNeeded(owningPackage);
		String name;
		if (valueSet.eResource()!=null && valueSet.eResource().getURI() != null){
			name = valueSet.eResource().getURI().trimFileExtension().lastSegment();
		}else {
			name = DEFAULT_DATATYPE_NAME;
		}
		DataType ret = (DataType) owningPackage.createPackagedElement(name, UMLPackage.eINSTANCE.getDataType());
		Stereotype dataSourceStereo = getDataSourceStereotype(owningPackage);
		if (dataSourceStereo!= null){
			DataSource dataSourceApplication = (DataSource) ret.applyStereotype(dataSourceStereo);
			dataSourceApplication.getValueSets().add(valueSet);
		}
		Stereotype variableStereo = getVariableStereotype(owningPackage);
		for (ValueSeries serie : valueSet.getSeries()){
			Property variable = ret.createOwnedAttribute(serie.getVariableName(), getUMLTypeForValueSeries(serie));
			if (variableStereo != null){
				variable.applyStereotype(variableStereo);
			}
		}		
		return ret;
	}

	private static Type getUMLTypeForValueSeries(ValueSeries serie) {
		switch (serie.eClass().getClassifierID()){
		case VisualizationPackage.BOOLEAN_SERIES :
			return getUMLPrimitiveType(serie, BOOLEAN_NAME);
		case VisualizationPackage.INTEGER_SERIES :
			return getUMLPrimitiveType(serie, INTEGER_NAME);
		case VisualizationPackage.DOUBLE_SERIES :
			return getUMLPrimitiveType(serie, REAL_NAME);
		default : 
			return getUMLPrimitiveType(serie, STRING_NAME);
		}
		
	}

	public static void applyVisualizationProfileIfNeeded(Package owningPackage) {
		Profile visualizationProfile = getVisualizationProfile(owningPackage);
		
		Iterator<Profile> profileIter = owningPackage.getAllAppliedProfiles().iterator();
		Profile appliedProfile = null;
		while (profileIter.hasNext() && (appliedProfile != visualizationProfile)){
			appliedProfile = profileIter.next();
		}
		if(appliedProfile != visualizationProfile){
			Package rootPackage = owningPackage.getModel();
			if (rootPackage != null && rootPackage.eResource() == owningPackage.eResource()){
				rootPackage.applyProfile(visualizationProfile);
			}else {
				owningPackage.applyProfile(visualizationProfile);
			}
		}
	}

	public ResourceSet getResourceSet(EObject context) {
		if (context != null && context.eResource() != null && context.eResource().getResourceSet() != null){
			return context.eResource().getResourceSet();
		}else {
			return new ResourceSetImpl();
		}
	}
	
	public static Profile getVisualizationProfile(EObject context){
		return UMLUtil.getProfile(VisualizationPackage.eINSTANCE, context);
	}
	
	public static PrimitiveType getUMLPrimitiveType(EObject context, String typeName){
		ResourceSet resSet = UMLUtil.getResourceSet(context);
		if (resSet == null){
			resSet = new ResourceSetImpl();
		}
		return (PrimitiveType) resSet.getEObject(URI.createURI(PRIMITIVE_TYPE_URI).appendFragment(typeName), true);
	}
	
	public static Stereotype getDataSourceStereotype(EObject context){
		Profile visualizationProfile = getVisualizationProfile(context);
		if (visualizationProfile != null){
			return visualizationProfile.getOwnedStereotype(DATA_SOURCE_STEREO_NAME);
		}
		return  null;
	}
	public static Stereotype getVariableStereotype(EObject context){
		Profile visualizationProfile = getVisualizationProfile(context);
		if (visualizationProfile != null){
			return visualizationProfile.getOwnedStereotype(VARIABLE_STEREO_NAME);
		}
		return null;
	}
	
}
