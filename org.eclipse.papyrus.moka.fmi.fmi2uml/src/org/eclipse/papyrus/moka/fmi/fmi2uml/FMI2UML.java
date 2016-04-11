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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.fmi2uml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.m2m.qvt.oml.util.Log;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiPackage;
import org.eclipse.papyrus.moka.fmi.modeldescription.util.FmiResourceFactoryImpl;

public class FMI2UML {


	private static final String MODEL_DESCRIPTION_NAME = "modelDescription.xml";


	private static final String XMI2UML_QVTO_URI = "platform:/plugin/org.eclipse.papyrus.moka.fmi.fmi2uml/transforms/Fmi2Uml.qvto";
	private static final String UML_TYPES_URI = "pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml";
	public static final String FMI_PROFILE_URI = "pathmap://FmiML_PROFILE/FmiML.profile.uml";
	public static final String FMU_PROXY_LIB_URI = "pathmap://PAPYRUS_MOKA_EXTRAS_FMI2_LIBRARY/fmu2Proxy.uml";
	private ResourceSet resourceSet;

	public FMI2UML(ResourceSet resSet) {
		resourceSet = resSet;
	}

	public FMI2UML() {
		resourceSet = new ResourceSetImpl();
	}


	public Resource extractFMUAndGenerateUMLModel(URI fmuURI, URI outputFolderURI, boolean saveResult) {

		extractFMU(fmuURI, outputFolderURI);

		String modelName = outputFolderURI.lastSegment();
		URI outputModelURI = outputFolderURI.appendSegment(modelName).appendFileExtension("uml");
		URI modelDescriptionURI = outputFolderURI.appendSegment(MODEL_DESCRIPTION_NAME);

		return transformModelDescriptionToUMLModel(modelDescriptionURI, outputModelURI, saveResult);

	}


	public void extractFMU(URI fmuURI, URI outputFolderURI) {
		FMIUtils.extractZipContents(fmuURI.toFileString(), FMIUtils.getFilePath(outputFolderURI) + File.separator);
	}

	public Resource transformModelDescriptionToUMLModel(URI modelDescriptionURI, URI outputModelURI, boolean saveResult) {

		// executeXml2XmiTransformation(modelDescriptionURI);
		return executeXml2UmlTransformation(modelDescriptionURI, outputModelURI, saveResult);

	}





	public Resource executeXml2UmlTransformation(URI xmlURI, URI outputModelURI, boolean saveResult) {
		// create executor for the given transformation
		URI secondTransformationURI = URI.createURI(XMI2UML_QVTO_URI);

		TransformationExecutor executor = new TransformationExecutor(secondTransformationURI);

		ResourceSet xmlResourceSet = new ResourceSetImpl();
		xmlResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*",
				new FmiResourceFactoryImpl());
		xmlResourceSet.getPackageRegistry().put(null, FmiPackage.eINSTANCE);

		Resource xmiResource = xmlResourceSet.getResource(xmlURI, true);

		// define the transformation input
		ExecutionContextImpl context = new ExecutionContextImpl();
		// context.setConfigProperty("keepModeling", true);
		// context.setConfigProperty("dllPath", dllPath);
		// context.setConfigProperty("fmuLocation", fmuLocation);

		context.setLog(new Log() {
			@Override
			public void log(int level, String message, Object param) {
				System.out.println(message);
			}

			@Override
			public void log(String message, Object param) {
				System.out.println(message);
			}

			@Override
			public void log(int level, String message) {
				System.out.println(message);
			}

			@Override
			public void log(String message) {
				System.out.println(message);
			}
		});


		if (xmiResource != null) {
			// create the inputs extents with its initial contents
			// xmi input
			EList<EObject> xmiObjects = xmiResource.getContents();
			final ModelExtent xmiInput = new BasicModelExtent(xmiObjects);
			// uml meta model input
			Resource umlResource = resourceSet.getResource(URI.createURI(UML_TYPES_URI), true);
			EList<EObject> umlObjects = umlResource.getContents();
			ModelExtent umlInput = new BasicModelExtent(umlObjects);

			// fmi profile input
			Resource fmiProfileResource = resourceSet.getResource(URI.createURI(FMI_PROFILE_URI), true);
			EList<EObject> fmiProfileObjects = fmiProfileResource.getContents();
			ModelExtent fmiProfileInput = new BasicModelExtent(fmiProfileObjects);
			// fmu proxy library input
			Resource fmuProxyResource = resourceSet.getResource(URI.createURI(FMU_PROXY_LIB_URI),
					true);
			EList<EObject> fmuProxyObjects = fmuProxyResource.getContents();
			final ModelExtent fmuProxyInput = new BasicModelExtent(fmuProxyObjects);




			// create an empty extent to catch the output
			ModelExtent outputModel = new BasicModelExtent();

			// run the transformation assigned to the executor with the given
			// input and output and execution context
			ExecutionDiagnostic result = executor.execute(context, xmiInput, umlInput, fmiProfileInput, fmuProxyInput, outputModel);

			// check the result for success
			if (result.getSeverity() == Diagnostic.OK) {
				// the output objects got captured in the output extent
				final List<EObject> outObjects = outputModel.getContents();
				Resource resultResource = resourceSet.createResource(outputModelURI);
				resultResource.getContents().addAll(outObjects);
				if (saveResult) {
					try {
						resultResource.save(null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return resultResource;

			} else {
				// turn the result diagnostic into status and send it to error log
				IStatus status = BasicDiagnostic.toIStatus(result);
				Activator.getDefault().getLog().log(status);
			}
		}
		return null;
	}





}
