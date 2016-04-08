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

public interface IForkedToken extends IToken {
	
	public void setBaseToken(IToken baseToken);
	
	public IToken getBaseToken();
	
	public void setRemainingOffersCount(Integer count);
	
	public Integer getRemainingOffersCount();
	
	public void setBaseTokenWithDrawn(Boolean status);
	
	public Boolean isBaseTokenWithdrawn();
	
}