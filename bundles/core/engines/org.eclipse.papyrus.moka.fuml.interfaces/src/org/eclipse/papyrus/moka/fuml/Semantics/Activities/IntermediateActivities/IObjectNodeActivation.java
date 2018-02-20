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

package org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities;

import java.util.List;


public interface IObjectNodeActivation extends IActivityNodeActivation {

	public Integer countOfferedValues();

	public void sendUnofferedTokens();

	public Integer countUnofferedTokens();

	public List<IToken> getUnofferedTokens();

	public List<IToken> takeUnofferedTokens();

}
