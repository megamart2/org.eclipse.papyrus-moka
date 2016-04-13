package org.eclipse.papyrus.moka.simex.advices;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecificationAction;

public class ValueSpecificationActionEditHelperAdvice extends AbstractEditHelperAdvice {

	protected Type _integer = null ;
	protected Type _boolean = null ;
	protected Behavior _lowerThan = null ; 
	protected Behavior _add = null ;

	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		ValueSpecificationAction node = (ValueSpecificationAction)request.getElementToConfigure() ;
		return new ConfigureElementCommand(request){
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				buildValueSpecificationAction(node) ;
				return CommandResult.newOKCommandResult(node);
			}
			
		};
	}

	protected void buildValueSpecificationAction(ValueSpecificationAction node) {
		node.createResult("result", null) ;
	}
}


