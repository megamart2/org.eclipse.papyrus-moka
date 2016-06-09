package org.eclipse.papyrus.moka.fmi.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.moka.fmi.fmu.FMUResource;
import org.eclipse.papyrus.moka.fmi.fmumetamodel.ArchiveToUnzipInFMU;
import org.eclipse.papyrus.moka.fmi.fmumetamodel.FMUBundle;
import org.eclipse.papyrus.moka.fmi.fmumetamodel.FmumetamodelFactory;
import org.eclipse.papyrus.moka.fmi.fmumetamodel.JavaFileProxy;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiModelDescriptionType;
import org.eclipse.papyrus.moka.fmi.util.FMUResourceUtil;
import org.eclipse.papyrus.moka.fmu.rcp.FMURCPApplication;
import org.eclipse.uml2.uml.Class;
import org.osgi.framework.Bundle;

public class FMUExporter {

	private static final String RESOURCE_DIR = "resources";
	private static final String RCP_NAME = "rcp.zip";
	private static final String LIB_NAME = "libfmuwrapper";

	public static void generateFMU(Class umlClass,String fmuName , String outputPath, String targetPlatform, String jrePath ){
		ResourceSet resSet = new ResourceSetImpl();
		
		FMUResource fmuRes = (FMUResource) resSet.createResource(URI.createFileURI(outputPath).appendSegment(fmuName).appendFileExtension("fmu"));
		FMUBundle fmuBundle = FmumetamodelFactory.eINSTANCE.createFMUBundle();
		fmuRes.getContents().add(fmuBundle);
		
		addModelDescription(umlClass, fmuName, fmuBundle);
		
		URI tmpURI = addUMLFile(umlClass, resSet, fmuBundle);
		
		addPropertiesFile(umlClass,tmpURI, fmuBundle);
		
		addRcp(targetPlatform, fmuBundle);
		
		addLibrary(targetPlatform, fmuName, fmuBundle);
		
		if (jrePath != null){
			addJRE(jrePath,fmuBundle);
		}
		
		try {
			fmuRes.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addJRE(String jrePath, FMUBundle fmuBundle) {
		File jreFile = new File(jrePath);
		JavaFileProxy proxy = FMUResourceUtil.createJavaFileProxy(jreFile, "rpc/jre");
		fmuBundle.getResourcesFiles().add(proxy);
	}

	private static void addLibrary(String targetPlatform, String fmuName, FMUBundle fmuBundle) {
		Bundle bundle = Activator.getDefault().getBundle();
		
		String fileExtension;
		if (targetPlatform.startsWith("linux")){
			fileExtension = ".so";
		}else {
			fileExtension = ".dll";
		}
		URL libURL = bundle.getEntry(RESOURCE_DIR+"/"+targetPlatform+"/"+LIB_NAME+fileExtension);
		try {
			libURL = FileLocator.toFileURL(libURL);
			File libFile = new File (libURL.getFile());
			JavaFileProxy proxy = FMUResourceUtil.createJavaFileProxy(libFile, fmuName+fileExtension);
			

			switch (targetPlatform){
				case FMUResourceUtil.BINARIES_LINUX32 :
					fmuBundle.getLinux32Files().add(proxy);
					break;
				case FMUResourceUtil.BINARIES_LINUX64 :
					fmuBundle.getLinux64Files().add(proxy);
					break;
				case FMUResourceUtil.BINARIES_WIN64 :
					fmuBundle.getWin64Files().add(proxy);
					break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	private static void addRcp(String targetPlatform, FMUBundle fmuBundle) {
		Bundle bundle = Activator.getDefault().getBundle();
		URL rcpURL = bundle.getEntry(RESOURCE_DIR+"/"+targetPlatform+"/"+RCP_NAME);
		try {
			rcpURL = FileLocator.toFileURL(rcpURL);
			File rcpFile = new File (rcpURL.getFile());
			ArchiveToUnzipInFMU archive = FMUResourceUtil.createArchiveToUnzipInFMU(rcpFile);
			fmuBundle.getResourcesFiles().add(archive);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void addPropertiesFile(Class umlClass, URI tmpURI, FMUBundle fmuBundle) {
		Properties prop = new Properties();
		prop.setProperty(FMURCPApplication.FMU_QN, umlClass.getQualifiedName());
		prop.setProperty(FMURCPApplication.MODEL_PATH_PROP, tmpURI.lastSegment());
		try {
			File propFile = new File(tmpURI.trimSegments(1).appendSegment(FMURCPApplication.MOKA_PROP_FILE_NAME).toFileString());
			prop.store(new FileOutputStream(propFile), null);
			propFile.deleteOnExit();
			JavaFileProxy proxy = FMUResourceUtil.createJavaFileProxy(propFile);
			fmuBundle.getResourcesFiles().add(proxy);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static URI addUMLFile(Class umlClass, ResourceSet resSet, FMUBundle fmuBundle) {
		Resource originalUMLResource = umlClass.eResource();
		try {
			Path tmpDirPath = Files.createTempDirectory("fmuExport");
			
			
			URI tmpURI = URI.createFileURI(tmpDirPath.toAbsolutePath().toString());
		
			String fileName = originalUMLResource.getURI().lastSegment();
			
			tmpURI = tmpURI.appendSegment(fileName);
			Resource reloadedResource = resSet.getResource( originalUMLResource.getURI(), true);
			Resource newResource = resSet.createResource(tmpURI);
			newResource.getContents().addAll(reloadedResource.getContents());
			
			newResource.save(null);
			File newResourceFile = new File(tmpURI.toFileString());
			newResourceFile.deleteOnExit();
			
			JavaFileProxy proxy = FMUResourceUtil.createJavaFileProxy(newResourceFile);
			fmuBundle.getResourcesFiles().add(proxy);
			
		
			return tmpURI;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	private static void addModelDescription(Class umlClass, String fmuName, FMUBundle fmuBundle) {
	
		FmiModelDescriptionType modelDesc = MokaUML2FMI.getModelDescription(umlClass, fmuName);
		if (modelDesc != null){
			fmuBundle.setModelDescription(modelDesc);
		}
		
	}
	
}
