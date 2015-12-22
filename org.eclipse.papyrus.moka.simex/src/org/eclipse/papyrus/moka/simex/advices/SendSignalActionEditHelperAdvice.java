package org.eclipse.papyrus.moka.simex.advices;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.AbstractStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLContainerContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.papyrus.uml.tools.util.UMLProviderHelper;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.UMLPackage;

public class SendSignalActionEditHelperAdvice extends AbstractEditHelperAdvice{


	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		if ( request.getElementToConfigure() instanceof SendSignalAction){
			final SendSignalAction sendSignal = (SendSignalAction) request.getElementToConfigure();
			
			ICommand ret = new AbstractTransactionalCommand(request.getEditingDomain(), "Complete send signal action creation", Collections.EMPTY_LIST) {
				
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					Activity activity = sendSignal.containingActivity();
					if (activity != null && activity.getContext() instanceof EncapsulatedClassifier){
						EncapsulatedClassifier context = (EncapsulatedClassifier) activity.getContext();
						List<Port> ports = context.getOwnedPorts();
						
						if (ports.size() == 1){
							sendSignal.setOnPort(ports.get(0));
						}else {
							
							TreeSelectorDialog dialog = new TreeSelectorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
							dialog.setTitle("Select port");
							dialog.setDescription("Select the port through which the signal should be sent");
						    
						    //dialog.setInput(context);
						    UMLContentProvider provider = new UMLContentProvider(sendSignal, UMLPackage.eINSTANCE.getInvocationAction_OnPort());
						    dialog.setContentProvider(provider);
							dialog.setLabelProvider(new UMLLabelProvider());
							dialog.setInitialElementSelections(context.getOwnedPorts());

							if (dialog.open() == Window.OK) {
								Object[] result = dialog.getResult();
								if (result != null && result.length > 0 && result[0] instanceof Port) {
									sendSignal.setOnPort((Port)result[0]);
								}
								
							}
							
							
						}
						
						
					}
					
					return CommandResult.newOKCommandResult();
				}
			};
			
			return ret;
			
		}
		return null;
	}
	
	
}

