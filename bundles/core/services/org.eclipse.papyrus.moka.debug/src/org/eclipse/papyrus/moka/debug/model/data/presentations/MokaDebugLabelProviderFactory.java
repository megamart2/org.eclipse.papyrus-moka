/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.debug.model.data.presentations;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.EventPoolVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.ExecutionContextVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.FeatureValueVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.ItemVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.MokaVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.SuspensionPointVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.TokensVariableAdapter;
import org.eclipse.papyrus.moka.debug.model.data.presentations.providers.EventPoolVariableLabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.presentations.providers.ExecutionContextVariableLabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.presentations.providers.FeatureValueVariableLabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.presentations.providers.ItemVariableLabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.presentations.providers.SuspensionPointVariableLabelProvider;
import org.eclipse.papyrus.moka.debug.model.data.presentations.providers.TokensVariableLabelProvider;

public class MokaDebugLabelProviderFactory {

	private static MokaDebugLabelProviderFactory INSTANCE;

	private MokaDebugLabelProviderFactory() {

	}

	public static MokaDebugLabelProviderFactory getInstance(){
		if(INSTANCE == null){
			INSTANCE = new MokaDebugLabelProviderFactory();
		}
		return INSTANCE;
	}

	public ILabelProvider instantiate(MokaVariableAdapter variable) {
		ILabelProvider provider = null; 
		if(variable instanceof ExecutionContextVariableAdapter){
			provider = new ExecutionContextVariableLabelProvider();
		}else if(variable instanceof SuspensionPointVariableAdapter){
			provider = new SuspensionPointVariableLabelProvider();
		}else if(variable instanceof EventPoolVariableAdapter){
			provider = new EventPoolVariableLabelProvider();
		}else if(variable instanceof FeatureValueVariableAdapter){
			provider = new FeatureValueVariableLabelProvider();
		}else if(variable instanceof ItemVariableAdapter){
			provider = new ItemVariableLabelProvider();
		}else if(variable instanceof TokensVariableAdapter){
			provider = new TokensVariableLabelProvider();
		}
		return provider;
	}

}
