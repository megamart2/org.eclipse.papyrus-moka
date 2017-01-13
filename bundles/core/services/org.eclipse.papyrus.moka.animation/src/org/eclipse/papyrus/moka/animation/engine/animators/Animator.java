/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.animation.engine.animators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationEngine;
import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationKind;
import org.eclipse.papyrus.moka.animation.engine.rendering.IAnimation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.service.IMokaExecutionListener;

public abstract class Animator implements IMokaExecutionListener, IAnimation{

	protected AnimationEngine engine;
	
	public Animator(AnimationEngine engine){
		this.engine = engine;
	}
	
	public abstract boolean accept(ISemanticVisitor visitor);
	
	@Override
	public void valueCreated(IValue value) {
		// Do nothing
	}

	@Override
	public void valueDestroyed(IValue value) {
		// Do nothing
	}
	
	@Override
	public void renderAs(EObject modelElement, IObject_ object, AnimationKind targetStyle) {
		// Apply the style  to model element if the context is allowed to perform animation.
		this.engine.removeRenderingRules(modelElement);
		this.engine.startRendering(modelElement, object, targetStyle);
	}

	@Override
	public void renderAs(EObject modelElement, IObject_ object, AnimationKind sourceStyle, AnimationKind targetStyle, int duration) {
		// Apply the source style to model element during the specified duration. As soon as this period of time as elapsed then
		// the target style is applied to the model element.
		this.engine.removeRenderingRules(modelElement);
		this.engine.startRendering(modelElement, object, sourceStyle);
		if(duration >= 25){	
			try {
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.engine.stopRendering(modelElement, object, sourceStyle);
		this.engine.startRendering(modelElement, object, targetStyle);
	}
	
}
