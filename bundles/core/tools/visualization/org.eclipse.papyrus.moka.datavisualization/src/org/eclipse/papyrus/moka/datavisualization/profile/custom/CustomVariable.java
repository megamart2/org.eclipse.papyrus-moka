/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.datavisualization.profile.custom;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.datavisualization.profile.DataSource;
import org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.impl.VariableImpl;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;

public class CustomVariable extends VariableImpl {

	@Override
	public EList<ValueSeries> getSeries() {
		EList<ValueSeries> ret = new BasicEList<ValueSeries>();
		Property baseProperty = getBase_Property();
		if (baseProperty != null){
			String variableName = baseProperty.getName() ;
			if (variableName != null){
				DataType owningDataType = baseProperty.getDatatype();
				if (owningDataType != null){
					for (EObject stereotypeApplication : owningDataType.getStereotypeApplications()){
						if (stereotypeApplication instanceof DataSource){
							DataSource owningDataSource = (DataSource) stereotypeApplication;
							for (DataValueSet valueSet : owningDataSource.getValueSets()){
								for (ValueSeries valueSerie : valueSet.getSeries()){
									if(variableName.equals(valueSerie.getVariableName())){
										ret.add(valueSerie);
									}
								}
							}
						}
					}
				}
			}
		}



		return ret;
	}

}
