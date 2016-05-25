package org.eclipse.papyrus.moka.fmi.fmu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

public class FMUResource extends XMLResourceImpl {

	
	FMUHandler fmuHandler;
	
	public FMUHandler getFmuHandler() {
		return fmuHandler;
	}
	
	
	@Override
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		Path tmpDirectoryPath = Files.createTempDirectory("fmuResource");
		UnzipUtility.unzip(new ZipInputStream(inputStream), tmpDirectoryPath.toString());
		fmuHandler = new FMUHandler(tmpDirectoryPath.toString());
		if (fmuHandler.getModelDescription()!= null){
			getContents().add(fmuHandler.getModelDescription());
		}
		
		
	}
	
	
	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
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
