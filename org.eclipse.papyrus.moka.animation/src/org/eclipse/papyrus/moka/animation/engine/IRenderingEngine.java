package org.eclipse.papyrus.moka.animation.engine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;

public interface IRenderingEngine {
	
	public boolean isRenderingRuleApplied(EObject modelElement, AnimationKind kind);
	
	public void removeRenderingRules(EObject modelElement);
	
	public void startRendering(EObject modelElement, IObject_ animator, AnimationKind kind);
	
	public void stopRendering(EObject modelElement, IObject_ animator, AnimationKind kind);
	
}
