/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.async.fuml.Semantics.Loci.LociL1;

import org.eclipse.papyrus.moka.async.fuml.Semantics.Classes.Kernel.AsyncObject_;
import org.eclipse.papyrus.moka.composites.Semantics.Loci.LociL3.CS_Locus;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;

// TODO: Auto-generated Javadoc
/**
 * Extends the original Locus class.
 *
 * @author CEA LIST (jt605650)
 */
public class AsyncLocus extends CS_Locus {

	/**
	 * Instantiate method now create AsyncObject_ instead of Object_.
	 *
	 * @param type
	 *            the type
	 * @return the object_
	 */
	@Override
	public Object_ instantiate(Class type) {
		// Instantiate the given class at this locus.
		Object_ object = null;
		if (type instanceof Behavior) {
			object = this.factory.createExecution((Behavior) type, null);
		} else {
			object = new AsyncObject_();
			object.types.add(type);
			object.createFeatureValues();
			this.add(object);
		}
		return object;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus#remove(org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ExtensionalValue)
	 */
	@Override
	public void remove(ExtensionalValue value) {
		synchronized (this) {
			super.remove(value);
		}
	}

}
