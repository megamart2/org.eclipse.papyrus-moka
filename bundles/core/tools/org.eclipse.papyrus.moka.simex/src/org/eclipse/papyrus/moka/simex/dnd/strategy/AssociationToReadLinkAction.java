/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.moka.simex.dnd.strategy;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.papyrus.moka.simex.dnd.command.CreateAndDropLinkActionCommand;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy to create an ReadLinkAction from an Association drop.
 * LinkActions require specific handling of LinkEndData. 
 * Hence the {@link AbstractDropInActivityStrategy#getCreateAndDropObjectCommand(EObject, Activity, Point, EditPart)}
 * is overloaded to return a custom Command instead of the default generic drop command.
 * 
 * @author SR246418
 *
 */
public class AssociationToReadLinkAction extends AbstractDropInActivityStrategy {


	public AssociationToReadLinkAction() {
		super(UMLPackage.eINSTANCE.getAssociation(), UMLElementTypes.READ_LINK_ACTION, null);
	}

	@Override
	protected Command getCreateAndDropObjectCommand(EObject droppedObject, Activity targetActivity, Point location, GraphicalEditPart targetEditPart) {
		CreateAndDropLinkActionCommand command = new CreateAndDropLinkActionCommand(targetActivity, elementTypeToCreate,referenceToSet, droppedObject, location, targetEditPart);
		command.setPrefix("read");
		return new ICommandProxy(command);
	}
}
