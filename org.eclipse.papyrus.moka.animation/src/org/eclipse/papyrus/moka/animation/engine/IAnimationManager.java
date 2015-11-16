package org.eclipse.papyrus.moka.animation.engine;

import org.eclipse.emf.ecore.EObject;

public interface IAnimationManager {
	
	public void startRendering(EObject modelElement, AnimationKind animationKind);
	
	public void render(EObject modelElement, AnimationKind animationKind, int renderingDuration);
	
	public void stopRendering(EObject modelElement, AnimationKind animationKind);
	
	public boolean isRenderable(EObject modelElement);
	
	public void deleteAllMarkers();
	
	public void clean();
	
}
