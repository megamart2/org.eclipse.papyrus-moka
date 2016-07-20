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
package org.eclipse.papyrus.moka.xygraph.common.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenableWithContainer;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;

public class XYGraphAdapterFactory implements IAdapterFactory{

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if( adapterType == IOpenable.class){
			if( adaptableObject instanceof XYGraphDescriptor ){
				XYGraphDescriptor xy = (XYGraphDescriptor) adaptableObject;
				return new IOpenableWithContainer.Openable(adaptableObject, xy.getContext());
			}
		}
		
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IOpenable.class };
	}
}
