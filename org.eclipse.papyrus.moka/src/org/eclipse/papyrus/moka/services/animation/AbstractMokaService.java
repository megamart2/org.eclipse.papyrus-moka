/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.services.animation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.services.IMokaService;

public class AbstractMokaService implements IMokaService {

	@Override
	public void init(EObject modelElement) {
		// By default do nothing - convenience for services which do not need to know
		// the model that is currently related to this instance of the execution
	}
	
	public void dispose(){
		
	}

}
