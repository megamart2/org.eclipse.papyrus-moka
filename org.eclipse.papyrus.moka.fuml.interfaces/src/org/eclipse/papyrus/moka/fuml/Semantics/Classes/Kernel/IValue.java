/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ValueSpecification;

public interface IValue extends ISemanticVisitor {

	public ValueSpecification specify();

	public Boolean equals(IValue otherValue);

	public IValue copy();

	public IValue new_();

	public List<Classifier> getTypes();

	public Boolean hasType(Classifier type);

}
