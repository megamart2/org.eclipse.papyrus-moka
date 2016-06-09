package org.eclipse.papyrus.moka.fmi.fmi2uml;


import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.moka.fmi.fmumetamodel.FMUBundle;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;

public class FMI2UML {

	public static final String DEPENDENCIES_PACKAGE_SUFFIX = "Dependencies";

	public static Class getFMUClass(FMUBundle fmuBundle, Package receivingPackage){
		if (fmuBundle == null || fmuBundle.getModelDescription() ==null || receivingPackage == null ){
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "fmuHandler and receiving package should not be null"));
			return null;
		}
		FMU2UMLTransformation transformation = new FMU2UMLTransformation(fmuBundle, receivingPackage);
		
		return transformation.transform();
	}
}
