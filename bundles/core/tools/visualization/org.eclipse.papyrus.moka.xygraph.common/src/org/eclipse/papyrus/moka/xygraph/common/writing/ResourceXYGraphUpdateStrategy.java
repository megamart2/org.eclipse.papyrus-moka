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
package org.eclipse.papyrus.moka.xygraph.common.writing;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphBinder;
import org.eclipse.papyrus.moka.xygraph.mapping.common.XYGraphMappingHelper;
import org.eclipse.papyrus.moka.xygraph.mapping.writing.XYGraphUpdateStrategy;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphDescriptor;
import org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphPackage;

public class ResourceXYGraphUpdateStrategy extends ResourceBaseUpdateStrategy implements XYGraphUpdateStrategy{

	@Override
	public void updateXYGraph(XYGraph xy, XYGraphBinder map) {
		XYGraphDescriptor gDesc = map.getXYGraphDescriptor();
		
		TransactionalEditingDomain domain = getTransactionalEditingDomain(map.getXYGraphDescriptor().eResource());
		
		CompoundCommand cc = new CompoundCommand();
		
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_Title(), xy.getTitle());
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_TitleColor(), XYGraphMappingHelper.mapColor(xy.getTitleColor()));
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_TitleFont(), XYGraphMappingHelper.mapFontData(xy.getTitleFontData()));
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_ZoomType(), XYGraphMappingHelper.mapZoomType(xy.getZoomType()));
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_ShowLegend(), xy.isShowLegend());
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_ShowTitle(), xy.isShowTitle());
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_Transparent(), xy.isTransparent());
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_ShowPlotAreaBorder(), xy.getPlotArea().isShowBorder());
		appendSetCommand(cc, domain, gDesc, XYGraphPackage.eINSTANCE.getXYGraphDescriptor_PlotAreaBackgroundColor(), XYGraphMappingHelper.mapColor(xy.getPlotArea().getBackgroundColor()));
		
		if( cc.canExecute() )
			domain.getCommandStack().execute(cc);
	}

}
