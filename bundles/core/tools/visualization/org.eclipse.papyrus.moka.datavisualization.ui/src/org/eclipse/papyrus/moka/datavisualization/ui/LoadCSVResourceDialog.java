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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class LoadCSVResourceDialog extends ResourceDialog {

	
	public LoadCSVResourceDialog(Shell parent, String title, int style, URI context) {
		super(parent, title, style, context);

	}

	protected void prepareBrowseFileSystemButton(Button browseFileSystemButton)
	{
		browseFileSystemButton.addSelectionListener
		(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				FileDialog fileDialog = new FileDialog(getShell(), style);
				fileDialog.setFilterExtensions(new String[]{"*.csv"});
				fileDialog.setFilterIndex(0);
				fileDialog.open();

				String filterPath = fileDialog.getFilterPath();
				if (isMulti())
				{
					String[] fileNames = fileDialog.getFileNames();
					StringBuffer uris = new StringBuffer();

					for (int i = 0, len = fileNames.length; i < len; i++)
					{
						uris.append(URI.createFileURI(filterPath + File.separator + fileNames[i]).toString());
						uris.append("  ");
					}             
					uriField.setText((uriField.getText() + "  " + uris.toString()).trim());
				}
				else
				{
					String fileName = fileDialog.getFileName();
					if (fileName != null)
					{
						uriField.setText(URI.createFileURI(filterPath + File.separator + fileName).toString());
					}
				}
			}
		});
	}


	protected void prepareBrowseWorkspaceButton(Button browseWorkspaceButton)
	{
		browseWorkspaceButton.addSelectionListener
		(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getCurrent().getActiveShell());
				dialog.setTitle("Import CSV files");
				WorkspaceContentProvider contentProvider=new WorkspaceContentProvider();
				Map<String, String > extensionFilter = new HashMap<String, String>();
				extensionFilter.put("csv", "*.csv");
				contentProvider.setExtensionFilters(extensionFilter);
				dialog.setContentProvider(contentProvider);
				dialog.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());

				dialog.open();
				Object[] selection = dialog.getResult();
				if (dialog.getReturnCode() == Window.OK && selection.length > 0 ) {
					StringBuffer uris = new StringBuffer();
					for (Object obj : selection){

						if (obj instanceof IFile){

							uris.append(URI.createPlatformResourceURI(((IFile)obj).getFullPath().toString(), true));
							uris.append("  ");
						}
					}
					uriField.setText((uriField.getText() + "  " + uris.toString()).trim());
				}

			}
		});      
	}
}
