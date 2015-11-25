/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.moka.composites.Semantics.Actions.CompleteActions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.CompleteActions.ReadIsClassifiedObjectActionActivation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;

public class CS_ReadIsClassifiedObjectActionActivation extends ReadIsClassifiedObjectActionActivation {

	@Override
	public boolean checkAllParents(Classifier type, Classifier classifier) {
		// If the given classifier is not an Interface, behaves like in fUML.
		// Otherwise, check if the given type (or one of its direct or indirect ancestors)
		// has an InterfaceRealization relationships with the given classifier.
		boolean matched = false;
		if (!(classifier instanceof Interface)) {
			matched = super.checkAllParents(type, classifier);
		} else if (!(type instanceof Class)) {
			matched = false;
		} else if (this.realizesInterface((Class) type, (Interface) classifier)) {
			matched = true;
		} else {
			List<Classifier> directParents = type.getGenerals();
			int i = 1;
			while (!matched & i <= directParents.size()) {
				Classifier directParent = directParents.get(i - 1);
				matched = this.checkAllParents(directParent, classifier);
				i = i + 1;
			}
		}
		return matched;
	}

	public Boolean realizesInterface(Class type, Interface interface_) {
		// Checks if the given type has an InterfaceRealization relationship
		// with the given interface or a descendant of the interface.
		List<InterfaceRealization> realizations = type.getInterfaceRealizations();
		boolean realized = false;
		int i = 1;
		while (i <= realizations.size() && !realized) {
			InterfaceRealization realization = realizations.get(i - 1);
			Interface contract = realization.getContract();
			if (contract == interface_) {
				realized = true;
			} else if (this.isDescendant(contract, interface_)) {
				realized = true;
			}
			i = i + 1;
		}
		return realized;
	}

	public Boolean isDescendant(Interface contract, Interface interface_) {
		// Checks if the given contract is a descendant of the given interface_
		boolean matched = false;
		List<Classifier> descendants = contract.getGenerals();
		int i = 1;
		while (i <= descendants.size() && !matched) {
			if (descendants.get(i - 1) instanceof Interface) {
				Interface descendant = (Interface) descendants.get(i - 1);
				if (descendant == interface_) {
					matched = true;
				} else {
					matched = this.isDescendant(descendant, interface_);
				}
			}
			i = i + 1;
		}
		return matched;
	}
}
