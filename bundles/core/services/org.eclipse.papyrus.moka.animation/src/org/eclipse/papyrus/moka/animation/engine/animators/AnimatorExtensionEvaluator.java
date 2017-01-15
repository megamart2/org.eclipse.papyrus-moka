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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.moka.animation.engine.rendering.AnimationEngine;

public class AnimatorExtensionEvaluator {
	
	private static final String ANIMATOR_ID = "org.eclipse.papyrus.moka.animation.animator";
	
	private static final String PRIORITY_ATTR = "priority";
	
	private static final String CLASS_ATTR = "class";

	public static List<Animator> evaluateAnimators(AnimationEngine engine){
		// Evaluate all contributions to the ANIMATOR extension points. The evaluation
		// process includes the instantiation of the contributed animator classes as
		// well as the association each animator to its specified priority.
		List<Animator> animators = new ArrayList<Animator>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configuration = registry.getConfigurationElementsFor(ANIMATOR_ID);
		for(int i=0; i < configuration.length; i++){
			IConfigurationElement contribution = configuration[i];
			Animator animator = null;
			try {
				animator = (Animator) contribution.createExecutableExtension(CLASS_ATTR);
				animator.setAnimationEngine(engine);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			if(animator != null){
				String prioritySpecification = contribution.getAttribute(PRIORITY_ATTR);
				if(prioritySpecification != null && !prioritySpecification.isEmpty()){
					int priority = 0;
					try{
						priority = Integer.parseInt(prioritySpecification);
					}catch(NumberFormatException e){
						e.printStackTrace();
					}
					animator.setPriority(priority);
				}else{
					animator.setPriority(0);
				}
				animators.add(animator);
			}
		}
		return animators;
	}
}
