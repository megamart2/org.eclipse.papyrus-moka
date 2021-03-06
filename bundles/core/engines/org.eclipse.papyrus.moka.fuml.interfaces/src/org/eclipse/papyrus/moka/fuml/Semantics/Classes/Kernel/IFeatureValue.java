/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import java.util.List;

import org.eclipse.uml2.uml.StructuralFeature;

public interface IFeatureValue {

	public Boolean hasEqualValues(IFeatureValue other);

	public IFeatureValue copy();

	public List<IValue> getValues();

	public void setValues(List<IValue> values);

	public StructuralFeature getFeature();

	public void setFeature(StructuralFeature feature);

	public Integer getPosition();

	public void setPosition(Integer position);
}
