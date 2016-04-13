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

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.StructuralFeature;

public interface IStructuredValue extends IValue {

	public void addFeatureValues(List<IFeatureValue> oldFeatureValues);

	public void addFeatureValuesForType(Classifier type, List<IFeatureValue> oldFeatureValues);

	public void createFeatureValues();

	public List<IFeatureValue> getMemberValues();

	public List<IValue> getValues(NamedElement feature, List<IFeatureValue> featureValues);

	public IFeatureValue getFeatureValue(StructuralFeature feature);

	public void setFeatureValue(StructuralFeature feature, List<IValue> values, Integer position);

	public List<IFeatureValue> getFeatureValues();

	public void setFeatureValues(List<IFeatureValue> featureValues);
}
