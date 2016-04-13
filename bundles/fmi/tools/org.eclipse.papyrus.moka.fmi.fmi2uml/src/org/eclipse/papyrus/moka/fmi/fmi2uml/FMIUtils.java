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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;

public class FMIUtils {

	public final static String PARAMETER_STEREO_NAME = "FmiMLProfile::Parameter";
	public final static String CAUSALITY_FEATURE_NAME = "causality";
	public final static String CAUSALITY_TYPE__PARAMETER = "parameter";
	public static final String PORT_STEREO = "FmiMLProfile::Port";
	public static final String PORT_CAUSALITY_FEATURE_NAME = "causality";
	public static final String FMU_STEREOTYPE_NAME = "FmiMLProfile::CS_FMU";

	public static String getFilePath(URI uri) {
		URL fileURL;
		try {
			fileURL = FileLocator.toFileURL(
					new URL(
							uri.toString()));
			return fileURL.getFile();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void extractZipContents(String zipPath, String destDir) {
		try {
			ZipFile zipFile = new ZipFile(zipPath);
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();
				String name = destDir + zipEntry.getName();

				File file = new File(name);
				if (name.endsWith("/")) {
					file.mkdirs();
					continue;
				}

				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}

				InputStream is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[2048];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();
			}
			zipFile.close();
			System.out.printf("Unzip terminated with success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void refreshWS() {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				if (project != null)
					project.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
