package org.eclipse.papyrus.moka.animation.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;

public class AnimatedDiagramManager {

	// The set of diagrams identified for a given model
	protected HashSet<Diagram> modelDiagrams;
	
	// The cache linking a model element to the of diagram in which it appears
	protected HashMap<EObject, HashSet<Diagram>> modelDiagramMapping;
	
	public AnimatedDiagramManager(){
		this.modelDiagrams = new HashSet<Diagram>();
		this.modelDiagramMapping = new HashMap<EObject, HashSet<Diagram>>();
	}
	
	public AnimatedDiagramManager(Model model){
		this();
		this.init(model);
	}
	
	/**
	 * Initialize the animated diagrams manager. The initialize process consist in:
	 * 		1 - Identifying diagrams located in a model
	 *      2 - Build a cache linking a model element to a list of diagrams in which it appears
	 *       
	 * @param modelElement - the model element used as a starting point for the initialization
	 */
	public void init(EObject modelElement){
		if(modelElement instanceof Element){
			// Find all diagrams available in this model
			Job diagramsLoading = new InitiliazeDiagramManagerJob(((Element)modelElement).getModel());
			diagramsLoading.schedule();
			try {
				diagramsLoading.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("here");
		}
	}
	
	/**
	 * Find the set of diagrams in which in the given model element appears 
	 * 
	 * @param modelElement - the model element for which the diagrams are searched
	 * 
	 * @param notationResource - the resource (.notation) in which the associated diagrams can be found
	 * 
	 * @return associatedDiagrams - the list of diagrams associated to the given model element
	 */
	private List<Diagram> getAssociatedDiagrams(EObject modelElement, Resource notationResource){
		List<Diagram> associatedDiagrams = new ArrayList<Diagram>();
		if(notationResource!=null && modelElement!=null){
			 return DiagramUtils.getAssociatedDiagramsFromNotationResource(modelElement, notationResource);
		}
		return associatedDiagrams;	
	}
	
	/**
	 * Identify all diagrams that are available in a model. The unique place from which this operation
	 * is called  is the job in charge of executing the search.
	 * 
	 * @param model - The model in which the diagrams should identified
	 * 
	 * @param monitor - The monitor used to report progress of our search
	 */
	private void searchDiagrams(Model model, IProgressMonitor monitor){
		// Initialization
		Resource resource = model.eResource();
		ResourceSet resourceSet = resource.getResourceSet();
		// Load the resource corresponding to the notation
		final String resourceNotationURI = model.eResource().getURI().toString().replaceAll("\\.uml$", ".notation");
		Resource notationResource = resourceSet.getResource(URI.createURI(resourceNotationURI), true);
		// Iterate over all model elements in order to discover all diagrams
		Iterator<EObject> modelContentIterator = model.eAllContents();
		while(modelContentIterator.hasNext()){
			EObject currentModelElement = modelContentIterator.next();
			// Find the associated diagrams
			List<Diagram> diagrams = this.getAssociatedDiagrams(currentModelElement, notationResource);
			// Build the cache to relate the model element the set of diagrams where it is shown
			if(!diagrams.isEmpty()){
				if(this.modelDiagramMapping.get(currentModelElement)==null){
					HashSet<Diagram> setOfDiagrams = new HashSet<Diagram>();
					setOfDiagrams.addAll(diagrams);
					this.modelDiagramMapping.put(currentModelElement, setOfDiagrams);
				}else{
					this.modelDiagramMapping.get(currentModelElement).addAll(diagrams);
				}
				// Add newly found diagrams to the set 
				this.modelDiagrams.addAll(diagrams);
			}
			monitor.worked(1);
		}
	}
	
	/**
	 * The diagrams search is executed in a separate job. 
	 */
	class InitiliazeDiagramManagerJob extends Job{

		private Model model;
		
		public InitiliazeDiagramManagerJob(Model model) {
			super("Diagrams lookup");
			this.model = model;
			this.setUser(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			monitor.beginTask("Diagrams lookup", IProgressMonitor.UNKNOWN);
			AnimatedDiagramManager.this.searchDiagrams(this.model, monitor);
			monitor.done();
			return Status.OK_STATUS;
		}
		
	}
	
	/**
	 * A model element is considered as being "renderable" if it has diagrams in which it appears  
	 * 
	 * @param modelElement - the model element that we assess as being renderable
	 * 
	 * @return true if the the element is present in the cache; false otherwise
	 */
	public boolean isRenderable(EObject modelElement){
		return this.modelDiagramMapping.get(modelElement)!=null;
	}
}
