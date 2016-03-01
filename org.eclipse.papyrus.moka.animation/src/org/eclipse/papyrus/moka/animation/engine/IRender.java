package org.eclipse.papyrus.moka.animation.engine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.services.animation.events.AnimationKind;

public interface IRender {
	
	public void init(EObject modelElement);
	
	public void startRendering(EObject modelElement, IObject_ animator, AnimationKind animationKind);
	
	public void render(EObject modelElement, IObject_ animator, AnimationKind animationKind, int renderingDuration);
	
	public void stopRendering(EObject modelElement, IObject_ animator, AnimationKind animationKind);
	
	public boolean isRenderable(EObject modelElement, IObject_ animator);
	
	public void deleteAllMarkers();
	
	public void clean();
	
}
