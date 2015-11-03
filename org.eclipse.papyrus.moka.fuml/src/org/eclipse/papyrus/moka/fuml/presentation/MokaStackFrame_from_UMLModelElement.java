/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.presentation;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.MokaStackFrame;
import org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.ui.presentation.IPresentation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;

public abstract class MokaStackFrame_from_UMLModelElement extends MokaStackFrame implements IPresentation {

	public MokaStackFrame_from_UMLModelElement(Element modelElement) {
		super(FUMLExecutionEngine.eInstance.getDebugTarget());
		this.modelElement = modelElement;
	}

	// /////////////////////////////
	// Presentation
	// /////////////////////////////

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.ui.presentation.IPresentation#getLabel()
	 */
	abstract public String getLabel();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.ui.presentation.IPresentation#getDetails()
	 */
	public String getDetails() {
		// Not used
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.ui.presentation.IPresentation#getImage()
	 */
	public Image getImage() {
		return FUMLPresentationUtils.getImage(modelElement);
	}

	// /////////////////////////////
	// Debug
	// /////////////////////////////

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.debug.MokaStackFrame#hasVariables()
	 */
	@Override
	public boolean hasVariables() throws DebugException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.moka.debug.MokaStackFrame#getVariables()
	 */
	@Override
	public IVariable[] getVariables() throws DebugException {
		Object_ contextObject = this.getContextObject();
		IVariable[] variables;
		if (contextObject == null) {
			variables = new IVariable[] {};
		} else {
			MokaVariable_from_ContextObject var = new MokaVariable_from_ContextObject(contextObject);
			variables = new IVariable[] { var };
		}
		return variables;
	}

	/**
	 * Returns the context fUML Object_ for this stack frame
	 *
	 * @return the context fUML Object_ for this stack frame
	 */
	protected abstract Object_ getContextObject();

}
