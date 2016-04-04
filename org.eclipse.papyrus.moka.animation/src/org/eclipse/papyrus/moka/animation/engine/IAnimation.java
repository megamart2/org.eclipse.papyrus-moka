package org.eclipse.papyrus.moka.animation.engine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;

public interface IAnimation {
	
	// Render the specified model element using the specified style. Existing style
	// are removed in order to ensure that the specified style is used. Moving from
	// a style to another style just consists in two successive call to this operation 
	public void renderAs(EObject modelElement, IObject_ animator, AnimationKind targetStyle);
	
	public void renderAs(EObject modelElement, IObject_ animator, AnimationKind sourceStyle, AnimationKind targetStyle, int duration);
	
}
