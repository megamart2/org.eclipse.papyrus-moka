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
package org.eclipse.papyrus.moka.xygraph.common.ui;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.swt.graphics.Image;

public class XYGraphLabelProvider extends LabelProvider{

	private Image icon;
	
	public XYGraphLabelProvider(Image icon) {
		this.icon = icon;
	}
	
	@Override
	public Image getImage(Object element) {
		if( icon == null )
			return super.getImage(element);
		
		return icon;
	}

	@Override
	public String getText(Object element) {
		
		if( element instanceof XYGraphDescriptor ){
			return ((XYGraphDescriptor)element).getTitle();
		}
		
		return super.getText(element);
	}

	public void onGraphUpdated(XYGraphDescriptor gDesc) {
		LabelProviderChangedEvent event = new LabelProviderChangedEvent(this, gDesc);
		fireLabelProviderChanged(event);
	}
}
