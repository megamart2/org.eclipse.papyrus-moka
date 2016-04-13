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

package org.eclipse.papyrus.moka.fuml.Semantics.Actions.IntermediateActions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Actions.BasicActions.IActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ILink;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.LinkEndData;

public interface ILinkActionActivation extends IActionActivation {

	public Boolean linkMatchesEndData(ILink link, List<LinkEndData> endDataList);

	public Boolean endMatchesEndData(ILink link, LinkEndData endData);

	public Association getAssociation();
}
