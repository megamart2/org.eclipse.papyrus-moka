package org.eclipse.papyrus.moka.fmi.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.moka.fmi.fmi2uml.FMI2UML;
import org.eclipse.papyrus.moka.fmi.fmiprofile.CS_FMU;
import org.eclipse.papyrus.moka.fmi.fmu.FMUHandler;
import org.eclipse.papyrus.moka.fmi.fmu.FMUResource;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiModelDescriptionType;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Test;

public class TestFMI {

	private static String testFMUPath = "resources/PV.fmu";
	
	@Test
	public void testFMUHandler(){
		
		URL bundleFMUUrl =FileLocator.find(Platform.getBundle(Activator.bundleID),new Path(testFMUPath), null);
		try {
			URL fileFMUURL = FileLocator.toFileURL(bundleFMUUrl);
			FMUHandler handler = new FMUHandler(fileFMUURL.getFile());
			
			assertTrue(handler.getModelDescription() instanceof FmiModelDescriptionType);
			File fmuTmpFolder = handler.getFMUFolder();
			
			assertNotNull(fmuTmpFolder);
			assertTrue(fmuTmpFolder.exists());
			
			//we the model description is reparsed after unzip in tmp folder we check it's ok
			assertTrue(handler.getModelDescription() instanceof FmiModelDescriptionType);
			
			assertNotNull(handler.getCosimulationDll());
			assertTrue(handler.getCosimulationDll().exists());
			
			
			FMUHandler newHandler = new FMUHandler(fmuTmpFolder.getAbsolutePath());
			checkFMUHandlerFromFolder(newHandler);
			
			File fmuFolder = handler.getFMUFolder();
			newHandler.clean();
			assertFalse(fmuFolder.exists());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private void checkFMUHandlerFromFolder(FMUHandler handler) {
		File fmuFolder;
		try {
			fmuFolder = handler.getFMUFolder();
			assertNotNull(fmuFolder);
			assertTrue(fmuFolder.exists());
			
			//we the model description is reparsed after unzip in tmp folder we check it's ok
			assertTrue(handler.getModelDescription() instanceof FmiModelDescriptionType);
			
			assertNotNull(handler.getCosimulationDll());
			assertTrue(handler.getCosimulationDll().exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Test
	public void testFMUResource(){
		Resource res = loadFMUResource();
		assertTrue(res instanceof FMUResource);
		assertFalse(res.getContents().isEmpty());
		assertTrue(res.getContents().get(0) instanceof FmiModelDescriptionType);
		
		FMUResource fmuRes = (FMUResource) res;
		assertTrue(fmuRes.getFmuHandler() instanceof FMUHandler);
		FMUHandler handler = fmuRes.getFmuHandler();
		
		checkFMUHandlerFromFolder(handler);
		
		try {
			File fmuFolder = handler.getFMUFolder();
			res.unload();
			assertFalse(fmuFolder.exists());
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		
		}
		
		
	
	private Resource loadFMUResource() {
		URI testFMUURI = URI.createPlatformPluginURI("/"+ Activator.bundleID+"/"+testFMUPath, true);
		ResourceSet resSet = new ResourceSetImpl();
		Resource res = resSet.getResource(testFMUURI, true);
		
		return res;
	}

	@Test
	public void testFMIProfile(){
		FMUResource fmuRes = (FMUResource) loadFMUResource();
		ResourceSet resSet = fmuRes.getResourceSet();
		
		try {
			File tmpFile = File.createTempFile("fmuModel", ".uml");
			tmpFile.deleteOnExit();
			URI umlFileURI =URI.createFileURI(tmpFile.getAbsolutePath()) ;
			Resource umlResource = resSet.createResource(umlFileURI);
			Model umlModel = UMLFactory.eINSTANCE.createModel();
			umlModel.setName("testModel");
			umlResource.getContents().add(umlModel);
			
			Class fmuClass = FMI2UML.getFMUClass(fmuRes.getFmuHandler().getModelDescription(), umlModel);
			
		
			
			assertNotNull(fmuClass);
			String expectedClassName = fmuRes.getFmuHandler().getModelDescription().getCoSimulation().get(0).getModelIdentifier();
			assertTrue(fmuClass.getName().equals(expectedClassName));
			
			Stereotype csFMUStereo = FMIProfileUtil.getStereotype(umlModel, FMIProfileUtil.CS_FMU_STEREO_NAME);
			EObject application = fmuClass.getStereotypeApplication(csFMUStereo);
			assertTrue(application instanceof CS_FMU);
			CS_FMU csFMU = (CS_FMU) application;
			assertTrue(csFMU.getModelDescription() == fmuRes.getFmuHandler().getModelDescription());
			
			umlResource.save(null);
			fmuRes.unload();
			umlResource.unload();
			
			resSet = new ResourceSetImpl();
			umlResource = resSet.getResource(umlFileURI, true);
			
			assertFalse(umlResource.getContents().isEmpty());
			assertTrue(umlResource.getContents().get(0) instanceof Model);
			
			umlModel = (Model) umlResource.getContents().get(0);
			assertTrue(umlModel.getOwnedType(expectedClassName) instanceof Class);
			Class umlClass = (Class) umlModel.getOwnedType(expectedClassName) ;
			
			csFMUStereo = umlClass.getAppliedStereotype(FMIProfileUtil.CS_FMU_STEREO_QUALIFIED_NAME);
			assertTrue(csFMUStereo == FMIProfileUtil.getStereotype(umlModel, FMIProfileUtil.CS_FMU_STEREO_NAME));
			
			csFMU = (CS_FMU) umlClass.getStereotypeApplication(csFMUStereo);
			assertTrue(csFMU.getModelDescription() != null);
			assertTrue(csFMU.getModelDescription().eResource() instanceof FMUResource);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
