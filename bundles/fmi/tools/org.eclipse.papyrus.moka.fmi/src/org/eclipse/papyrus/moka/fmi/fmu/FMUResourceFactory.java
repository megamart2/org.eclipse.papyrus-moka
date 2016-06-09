package org.eclipse.papyrus.moka.fmi.fmu;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

public class FMUResourceFactory implements Factory {

	@Override
	public Resource createResource(URI uri) {
		FMUResource res = new FMUResource();
		res.setURI(uri);
		return res;
	}

}
