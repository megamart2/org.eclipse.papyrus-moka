package org.eclipse.papyrus.moka.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class ResourceSetUtils {

	private static ResourceSet internalResSet;
	
	public static ResourceSet getResourceSet(EObject context){
		if (context != null && context.eResource() != null && context.eResource().getResourceSet() != null){
			return context.eResource().getResourceSet();
		}else {
			return null;
		}
	}

	public static ResourceSet getDefaultResourceSet() {
		if (internalResSet == null){
			internalResSet = new ResourceSetImpl();
		}
		return internalResSet;
	}
}
