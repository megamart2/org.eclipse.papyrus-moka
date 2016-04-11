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

public interface IOffer {

	public Integer countOfferedValues();

	public List<IToken> getOfferedTokens();

	public void removeOfferedValues(Integer count);

	public void removeWithdrawnTokens();

	public Boolean hasTokens();

	public List<IToken> getTokens();

}
