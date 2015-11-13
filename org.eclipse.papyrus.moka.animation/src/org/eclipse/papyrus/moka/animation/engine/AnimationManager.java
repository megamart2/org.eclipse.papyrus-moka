/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.animation.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.services.markerlistener.IPapyrusMarker;
import org.eclipse.papyrus.infra.services.markerlistener.PapyrusMarkerAdapter;
import org.eclipse.papyrus.moka.animation.utils.AnimationUtils;

/**
 * Some facilities to manage animation in diagrams.
 *
 */
public class AnimationManager implements IAnimationManager{

	// The diagram manager handles every action done by the animation regarding diagrams
	AnimatedDiagramManager diagramManager;
	
	// The static instance for this class
	protected static AnimationManager eInstance = null;

	// Maintain a map of model elements having makers
	// The only purpose of this map is too avoid a to global search in diagrams at some points of the execution
	protected Map<EObject, List<IPapyrusMarker>> modelElementMarkers = new HashMap<EObject, List<IPapyrusMarker>>();


	private AnimationManager() {
		this.diagramManager = new AnimatedDiagramManager();
		this.modelElementMarkers = new HashMap<EObject, List<IPapyrusMarker>>();
	}
	
	private boolean hasMarker(EObject modelElement, AnimationKind kind){
		if(modelElement==null){
			return false;
		}
		List<IPapyrusMarker> markers = this.modelElementMarkers.get(modelElement);
		if(markers==null || markers.isEmpty()){
			return false;
		}
		boolean found = false;
		int i = 0;
		while(!found && i < markers.size()){
			IPapyrusMarker marker = markers.get(i);
			String type = "";
			try {
				type = marker.getType();
			} catch (CoreException e) {
				e.printStackTrace();
			}
			if(kind==AnimationKind.ANIMATED){
				found = type.equals(AnimationUtils.ANIMATED_MARKER_ID);
			}else if(kind==AnimationKind.SUSPENDED){
				found = type.equals(AnimationUtils.SUSPENDED_MARKER_ID);
			}
			i++;
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	private IPapyrusMarker createMarker(final EObject modelElement,  final String markerID,  @SuppressWarnings("rawtypes") Map attributes){
		IPapyrusMarker marker = null;
		// Assert that the model element is usable
		if(modelElement==null || modelElement.eResource()==null){
			return null;
		}
		final String uri = EcoreUtil.getURI(modelElement).toString();
		if(uri==null || uri.isEmpty()){
			return null;
		}
		// Assert we are able to access the resource on which the marker will be placed
		IResource resource = this.getWorkspaceResource(modelElement); 
		if(resource!=null && resource.exists()){
			IMarker newMarker = null;
			try {
				newMarker = resource.createMarker(markerID);
				newMarker.setAttributes(attributes);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			if(newMarker!=null){
				// Place the Papyrus marker
				marker = PapyrusMarkerAdapter.wrap(modelElement.eResource(), newMarker, attributes);
			}
		}
		return marker;
	}
	
	private IResource getWorkspaceResource(final EObject modelElement){
		IResource resource = null;
		IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
		if(workspace!=null){
			resource = workspace.getRoot().getFile(new Path(modelElement.eResource().getURI().toPlatformString(true)));
		}
		return resource;
	}
	
	/**
	 * Returns an instance of AnimationManager. Guarantees that AnimationManager is instantiated only once.
	 *
	 * @return An instance of AnimationManagers
	 */
	public static AnimationManager getInstance() {
		if (eInstance == null) {
			eInstance = new AnimationManager();
		}
		return eInstance;
	}

	/**
	 * Initialize the animation manager
	 *  
	 * @param modelElement
	 */
	public void init(EObject modelElement){
		// Triggers diagrams search when called
		this.diagramManager.init(modelElement);
	}
	
	@Override
	public void startRendering(EObject modelElement, AnimationKind animationKind) {
		// A marker is only registered on a model element if
		// this latter has not already a marker applied on it
		// and if so this marker is not of the kind of the requested
		// marker
		if(!this.hasMarker(modelElement, animationKind)){
			IPapyrusMarker marker = null; 
			// Build attributes
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put(EValidator.URI_ATTRIBUTE, EcoreUtil.getURI(modelElement).toString());
			// Defined which type of animation is expected
			if(animationKind.equals(AnimationKind.ANIMATED)){
				marker = this.createMarker(modelElement, AnimationUtils.ANIMATED_MARKER_ID, attributes);
			}else if(animationKind.equals(AnimationKind.SUSPENDED)){
				marker = this.createMarker(modelElement, AnimationUtils.SUSPENDED_MARKER_ID, attributes);
			}else{
				System.err.println("[startRendering] - animation kind not recognized");
			}
			// Update map of applied markers
			List<IPapyrusMarker> markerList = this.modelElementMarkers.get(modelElement);
			if(markerList==null){
				markerList = new ArrayList<IPapyrusMarker>();
				markerList.add(marker);
				this.modelElementMarkers.put(modelElement, markerList);
			}else{
				markerList.add(marker);
			}
		}
	}

	@Override
	public void render(EObject modelElement, AnimationKind animationKind, int renderingDuration) {
		// Place the marker to trigger the user view to change
		this.startRendering(modelElement, animationKind);
		// The duration for which the marker is in place
		try {
			Thread.sleep(renderingDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Remove the marker to trigger the user view to change
		this.stopRendering(modelElement, animationKind);
	}

	@Override
	public void stopRendering(EObject modelElement, AnimationKind kind) {
		// A 
		if(this.hasMarker(modelElement, kind)){
			List<IPapyrusMarker> markers = this.modelElementMarkers.get(modelElement);
			Iterator<IPapyrusMarker> markersIterator = markers.iterator();
			IPapyrusMarker marker = null; 
			while(marker==null && markersIterator.hasNext()){
				IPapyrusMarker currentMarker = markersIterator.next();
				String markerType="";
				try {
					markerType = currentMarker.getType();
				} catch (CoreException e) {
					e.printStackTrace();
				}
				if(kind==AnimationKind.ANIMATED){
					marker = markerType.equals(AnimationUtils.ANIMATED_MARKER_ID) ? currentMarker : null;
				}else if(kind==AnimationKind.SUSPENDED){
					marker = markerType.equals(AnimationUtils.SUSPENDED_MARKER_ID) ? currentMarker : null;
				}
			}
			if(marker!=null){
				this.modelElementMarkers.get(modelElement).remove(marker);
				try {
					marker.delete();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stopRendering(EObject modelElement){
		
	}
	
	@Override
	public boolean isRenderable(EObject modelElement) {
		// TODO Auto-generated method stub
		return false;
	}

}
