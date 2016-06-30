/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.ui.handlers;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.moka.composites.utils.handlers.AbstractCompositeUtilsHandler;
import org.eclipse.papyrus.moka.composites.utils.handlers.Utils;
import org.eclipse.papyrus.moka.fmi.exporter.FMUExporter;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.papyrus.moka.fmi.ui.Activator;
import org.eclipse.papyrus.moka.fmi.ui.dialogs.ExportFMUDialog;
import org.eclipse.papyrus.moka.fmi.ui.dialogs.ImportFMUDialog;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;


public class ExportFMUHandler extends AbstractCompositeUtilsHandler {

	@Override
	public RecordingCommand getUpdateCommand(Class context, TransactionalEditingDomain domain) {
		return new ExportFMUCommand(context, domain);
	}
	
	@Override
	public boolean isEnabled() {
		Element selectedElement = Utils.getSelection();
		if (selectedElement != null) {
			if (selectedElement instanceof Class && !(selectedElement instanceof Behavior)) {
				return FMIProfileUtil.isCS_FMU((Class)selectedElement) ;
			}
		}
		return false;
	}



	/**
	 * Command that exports the FMU for a given CS_FMU.
	 *
	 */
	protected class ExportFMUCommand extends RecordingCommand {

		protected Class context;

		public ExportFMUCommand(Class context, TransactionalEditingDomain domain) {
			super(domain);
			this.context = context;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor
		 * , org.eclipse.core.runtime.IAdaptable)
		 */
		@Override
		protected void doExecute() {
			Display display = Display.getDefault() ;
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					
					
					ExportFMUDialog dialog = new ExportFMUDialog(Display.getCurrent().getActiveShell(), context.getName()) ;
//					IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//					dialog.setPreferenceStore(store);
					int status = dialog.open() ;
					if (status != SWT.ABORT) {
						FMUExporter.generateFMU(context, dialog.getFmuName(),dialog.getSelectedDir(),dialog.getSelectedPlatform(),dialog.getSelectedJrePath());
					}
				}
			});
		}
	}
}
