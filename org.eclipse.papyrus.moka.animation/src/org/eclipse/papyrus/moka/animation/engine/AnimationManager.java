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
import org.eclipse.papyrus.moka.MokaConstants;
import org.eclipse.papyrus.moka.animation.utils.AnimationUtils;

/**
 * Some facilities to manage animation in diagrams.
 *
 */
public class AnimationManager implements IAnimationManager{

	// The diagram manager handles every action done by the animation regarding diagrams
	protected AnimatedDiagramManager diagramManager;
	
	// The static instance for this class
	protected static AnimationManager eInstance = null;

	// Maintain a map of model elements having makers
	// The only purpose of this map is too avoid a to global search in diagrams at some points of the execution
	protected Map<EObject, List<IPapyrusMarker>> modelElementMarkers = new HashMap<EObject, List<IPapyrusMarker>>();


	private AnimationManager() {
		// Constructor
		this.diagramManager = new AnimatedDiagramManager();
		this.modelElementMarkers = new HashMap<EObject, List<IPapyrusMarker>>();
	}
	
	private boolean hasMarker(EObject modelElement, AnimationKind kind){
		// Find out if a marker of the given kind is applied on the model element
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
			}else if(kind==AnimationKind.VISITED){
				found = type.equals(AnimationUtils.VISITED_MARKER_ID);
			}
			i++;
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	private IPapyrusMarker createMarker(final EObject modelElement,  final String markerID,  @SuppressWarnings("rawtypes") Map attributes){
		// Create a marker of the given type (c.f. markerID) and set the attributes
		// with the provided map
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
	
	private IPapyrusMarker deleteMarker(final EObject modelElement,  AnimationKind kind){
		// Find out a marker of the given type placed on a model element. This marker is 
		// then deleted. The deleted marker is returned by the operation.
		List<IPapyrusMarker> markers = this.modelElementMarkers.get(modelElement);
		Iterator<IPapyrusMarker> markersIterator = markers.iterator();
		IPapyrusMarker deletedMarker = null; 
		while(deletedMarker==null && markersIterator.hasNext()){
			IPapyrusMarker currentMarker = markersIterator.next();
			String markerType="";
			try {
				markerType = currentMarker.getType();
			} catch (CoreException e) {
				e.printStackTrace();
			}
			if(kind==AnimationKind.ANIMATED){
				deletedMarker = markerType.equals(AnimationUtils.ANIMATED_MARKER_ID) ? currentMarker : null;
			}else if(kind==AnimationKind.SUSPENDED){
				deletedMarker = markerType.equals(AnimationUtils.SUSPENDED_MARKER_ID) ? currentMarker : null;
			}else if(kind==AnimationKind.VISITED){
				deletedMarker = markerType.equals(AnimationUtils.VISITED_MARKER_ID) ? currentMarker : null;
			}
		}
		if(deletedMarker!=null){
			try {
				deletedMarker.delete();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return deletedMarker;
	}
	
	private IResource getWorkspaceResource(final EObject modelElement){
		IResource resource = null;
		IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
		if(workspace!=null){
			resource = workspace.getRoot().getFile(new Path(modelElement.eResource().getURI().toPlatformString(true)));
		}
		return resource;
	}
	
	public static AnimationManager getInstance() {
		// Provide a reference on the unique instance of the Animation manager
		if (eInstance == null) {
			eInstance = new AnimationManager();
		}
		return eInstance;
	}

	public void init(EObject modelElement){
		// Triggers diagrams search when called
		this.diagramManager.init(modelElement);
	}
	
	protected void preRendering(EObject modelElement){
		// This operation specifies pre-actions that need to be accomplished
		// before proceeding to the animation.
		if(MokaConstants.MOKA_OPEN_DIAGRAM_IN_AUTOMATIC_ANIMATION){
			if(!this.diagramManager.hasOpenedDiagram(modelElement)){
				this.diagramManager.openDiagrams(modelElement);
			}
		}
	}
	
	@Override
	public synchronized void startRendering(EObject modelElement, AnimationKind animationKind) {
		// A marker is only registered on a model element if
		// this latter has not already a marker applied on it
		// and if so this marker is not of the kind of the requested
		// marker
		if(modelElement!= null && this.isRenderable(modelElement)){
			this.preRendering(modelElement);
			IPapyrusMarker requestedMarker = null;
			// This is not the first time the element is visited
			if(this.hasMarker(modelElement, AnimationKind.VISITED)){
				IPapyrusMarker visitedMarker = this.deleteMarker(modelElement, AnimationKind.VISITED);
				if(visitedMarker!=null){
					this.modelElementMarkers.get(modelElement).remove(visitedMarker);
				}
			}
			// Apply requested marker
			if(!this.hasMarker(modelElement, animationKind)){
				Map<String, String> attributes = new HashMap<String, String>();
				attributes.put(EValidator.URI_ATTRIBUTE, EcoreUtil.getURI(modelElement).toString());
				// Defined which type of animation is expected
				if(animationKind.equals(AnimationKind.ANIMATED)){
					requestedMarker = this.createMarker(modelElement, AnimationUtils.ANIMATED_MARKER_ID, attributes);
				}else if(animationKind.equals(AnimationKind.SUSPENDED)){
					requestedMarker = this.createMarker(modelElement, AnimationUtils.SUSPENDED_MARKER_ID, attributes);
				}else{
					System.err.println("[startRendering] - animation kind not recognized");
				}
				// Update map of applied markers
				List<IPapyrusMarker> markerList = this.modelElementMarkers.get(modelElement);
				if(markerList==null){
					markerList = new ArrayList<IPapyrusMarker>();
					this.modelElementMarkers.put(modelElement, markerList);
				}
				if(requestedMarker!=null){
					markerList.add(requestedMarker);
				}
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
	public synchronized void stopRendering(EObject modelElement, AnimationKind kind) {
		// A marker can only be removed from a model element if it is applied on it.
		// As a model element can have multiple markers applied, only the one corresponding
		// to the specific animation kind is removed
		if(modelElement != null && this.isRenderable(modelElement)){
			if(this.hasMarker(modelElement, kind)){
				IPapyrusMarker marker = this.deleteMarker(modelElement, kind); 
				if(marker!=null){
					this.modelElementMarkers.get(modelElement).remove(marker);
				}
			}
			if(!this.hasMarker(modelElement, AnimationKind.VISITED)){
				Map<String, String> attributes = new HashMap<String, String>();
				attributes.put(EValidator.URI_ATTRIBUTE, EcoreUtil.getURI(modelElement).toString());
				IPapyrusMarker visitedMarker = this.createMarker(modelElement, AnimationUtils.VISITED_MARKER_ID, attributes);
				this.modelElementMarkers.get(modelElement).add(visitedMarker);
			}
		}
	}

	@Override
	public boolean isRenderable(EObject modelElement) {
		return this.diagramManager.isRenderable(modelElement);
	}

	@Override
	public void deleteAllMarkers() {
		// Make sure any marker placed by the framework over a model element is deleted when this operation is called
		// The operation is thread safe and lock a model element before starting to remove the markers
		synchronized(this.modelElementMarkers){
			for(EObject modelElement : this.modelElementMarkers.keySet()){
				for(IPapyrusMarker marker : this.modelElementMarkers.get(modelElement)){
					if(marker.exists()){
						try {
							marker.delete();
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
				this.modelElementMarkers.get(modelElement).clear();
			}
			this.modelElementMarkers.clear();
		}
	}

	public synchronized void clean(){
		// The clean operation is in charge of:
		// 1 - Releasing all model elements from their makers (if placed by the animation framework)
		// 2 - Releasing all diagram referenced by the animation diagram manager
		this.deleteAllMarkers();
		this.diagramManager.clean();
	}
}
