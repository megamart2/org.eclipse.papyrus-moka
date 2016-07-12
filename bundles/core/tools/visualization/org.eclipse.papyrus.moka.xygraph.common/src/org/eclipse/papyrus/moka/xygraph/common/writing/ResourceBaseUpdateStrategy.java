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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;

public class ResourceBaseUpdateStrategy{
	
	protected void appendSetCommand(CompoundCommand cc, EditingDomain domain, EObject owner, EStructuralFeature feature, Object newValue){
		SetCommand cmd = new SetCommand(domain, owner, feature, newValue);
		if( cmd.canExecute() )
			cc.append(cmd);
	}
	
	protected TransactionalEditingDomain getTransactionalEditingDomain(Resource resource){
		TransactionalEditingDomain domain = null;
		try {
			domain = ServiceUtilsForResource.getInstance().getTransactionalEditingDomain(resource);
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
		
		return domain;
	}
}
