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
package org.eclipse.papyrus.moka.datavisualization.service;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.papyrus.moka.xygraph.mapping.common.Variable.VariableID;

public class DataSourceVariableID extends VariableID{
	
	private ValueSeries dataSource;
	
	public DataSourceVariableID(ValueSeries dataSource){
		this.dataSource = dataSource;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( !(obj instanceof DataSourceVariableID) )
			return false;
		
		DataSourceVariableID other = (DataSourceVariableID)obj;
		EObject ds = other.getDataSource();

		//TODO Is there a better ID for the dataSource?
		return ds.equals(dataSource);
	}
	
	public EObject getDataSource() {
		return dataSource;
	}

	@Override
	public int hashCode() {
		return dataSource.getBase_Property().hashCode();
	}
}
