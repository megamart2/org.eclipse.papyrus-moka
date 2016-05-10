package org.eclipse.papyrus.moka.fmi.fmu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

public class FMUResource extends ResourceImpl {

	
	FMUHandler fmuHandler;
	
	public FMUHandler getFmuHandler() {
		return fmuHandler;
	}
	
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		Path tmpDirectoryPath = Files.createTempDirectory("fmuResource");
		UnzipUtility.unzip(new ZipInputStream(inputStream), tmpDirectoryPath.toString());
		fmuHandler = new FMUHandler(tmpDirectoryPath.toString());
		if (fmuHandler.getModelDescription()!= null){
			getContents().add(fmuHandler.getModelDescription());
		}
		
		
	}
	
	
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		// TODO Implement save! Currently it's not allowed to modify a FMU
		
	}
	
	@Override
	protected void doUnload() {
		super.doUnload();
		if (fmuHandler != null){
			fmuHandler.clean();
			fmuHandler = null;
		}
		
	}
}
