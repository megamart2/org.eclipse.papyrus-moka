/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.datavisualization.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForActionHandlers;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.papyrus.moka.datavisualization.csv.CSVResource;
import org.eclipse.papyrus.moka.datavisualization.util.VisualizationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.util.UMLUtil;

public class LoadCSVCommand extends RecordingCommand {

	Package contextPackage;
	ResourceSet resSet;
	IEvaluationContext evaluationContext;
	
	public LoadCSVCommand(TransactionalEditingDomain domain, Package contextPackage, IEvaluationContext context) {
		super(domain);
		this.contextPackage = contextPackage;
		this.evaluationContext = context;
		
		if(contextPackage.eResource() != null && contextPackage.eResource().getResourceSet() != null){
			resSet = contextPackage.eResource().getResourceSet();
		}else {
			resSet = new ResourceSetImpl();
		}
	}
	
	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	protected void doExecute() {
		
		URI contextURI = null;
		
		if( contextPackage.eResource() != null) {
			contextURI = contextPackage.eResource().getURI().trimSegments(1);
		}
		
		LoadCSVResourceDialog resourceDialog = new LoadCSVResourceDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				"Select CSV file to import",
				SWT.MULTI,
				contextURI);
		
		if(	resourceDialog.open() == Window.OK){
//			ResourceSet resSet = UMLUtil.getResourceSet(contextPackage);
//			if (resSet == null){
//				resSet = new ResourceSetImpl();
//			}
			
			ResourceSet resSet = new ResourceSetImpl();
			
			for (URI uri :resourceDialog.getURIs()){
				Resource res = resSet.createResource(uri);
				Map<Object, Object> options = new HashMap<>();
				options.put(CSVResource.OPTION_TARGET_PACKAGE, contextPackage);
				try {
					res.load(options);
				} catch (IOException e1) {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Could not load file : " + uri.toString()+" as CSV resource"));
					
				}
				
				if (res !=null && !res.getContents().isEmpty() && res.getContents().get(0) instanceof Model){
					DataType dataSource = null; 
					for (Element elem: ((Model)res.getContents().get(0)).getPackagedElements()){
						if (elem instanceof DataType){
							dataSource = (DataType) elem;
							break;
						}
					}
					if (dataSource != null){
						
						MoveRequest moveReq = new MoveRequest(contextPackage, dataSource);
						IElementEditService provider = ElementEditServiceUtils.getCommandProvider(contextPackage);
						if (provider != null){
							ICommand command = provider.getEditCommand(moveReq);
							if (command != null&& command.canExecute()){
								try {
									command.execute(null, null);
								} catch (ExecutionException e) {
									Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to move imported DataType from CSV resource to destination package"));
								}
							}
							
						}
					}
					
					
				}else {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Could not load file : " + uri.toString()+" as CSV resource"));
				}
				
				
			}
		}
	}
	

	

	

	
}
