package org.eclipse.papyrus.moka.animation.presentation.control;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.moka.animation.presentation.data.DiagramAnimationNode;

public class AnimationControlViewFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof DiagramAnimationNode){
			return !((DiagramAnimationNode)element).children.isEmpty();
		}
		return true;
	}

}
