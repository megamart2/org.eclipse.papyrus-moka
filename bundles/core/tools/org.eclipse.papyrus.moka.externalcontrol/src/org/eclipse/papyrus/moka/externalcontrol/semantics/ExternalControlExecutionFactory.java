/*****************************************************************************
 * 
 * Copyright (c) 2017 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.moka.externalcontrol.semantics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.moka.externalcontrol.advice.IControllerAdvice;
import org.eclipse.papyrus.moka.externalcontrol.advice.IControllerAdviceFactory;
import org.eclipse.papyrus.moka.externalcontrol.controller.ExternalController;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutionFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.BasicActions.ActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.BasicActions.CallActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityEdgeInstance;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.ExecutionFactory;
import org.eclipse.uml2.uml.Element;

/**
 * @author sr246418
 *
 */
public class ExternalControlExecutionFactory extends ExecutionFactory {

	IExecutionFactory delegatedExecutionFactory;
	ExternalController controller;
	List<IControllerAdviceFactory> adviceFactories = new ArrayList<>();


	public ExternalControlExecutionFactory(IExecutionFactory delegatedExecutionFactory, ExternalController controller, IControllerAdviceFactory... adviceFactories) {
		init(delegatedExecutionFactory, controller, Arrays.asList(adviceFactories));
	}

	public ExternalControlExecutionFactory(IExecutionFactory delegatedExecutionFactory, ExternalController controller, List<IControllerAdviceFactory> adviceFactories) {
		init(delegatedExecutionFactory, controller, adviceFactories);
	}


	private void init(IExecutionFactory delegatedExecutionFactory, ExternalController controller, List<IControllerAdviceFactory> adviceFactories) {
		if (delegatedExecutionFactory == null) {
			throw new RuntimeException("Delegated Execution Factory can't be null");
		}
		this.delegatedExecutionFactory = delegatedExecutionFactory;

		if (controller == null) {
			throw new RuntimeException("The external controller can't be null");
		}
		this.controller = controller;

		for (IControllerAdviceFactory adviceFactory : adviceFactories) {
			this.adviceFactories.add(adviceFactory);
		}
	}

	@Override
	public ISemanticVisitor instantiateVisitor(Element element) {
		
		
		ISemanticVisitor delegatedVisitor = delegatedExecutionFactory.instantiateVisitor(element);

		List<IControllerAdvice> adviceList = getControllerAdvices(element, delegatedVisitor);
		if(delegatedVisitor instanceof ActivityExecution){
			return new ExternallyControlledActivityExecution((ActivityExecution)delegatedVisitor, controller, adviceList);
		}else if (delegatedVisitor instanceof CallActionActivation) {
			return new ExternallyControlledCallActionActivation((CallActionActivation) delegatedVisitor, controller, adviceList);
		}
		
		if (!adviceList.isEmpty()) {
			if (delegatedVisitor instanceof ActivityEdgeInstance) {
				return new ExternallyControlledActivityEdgeInstance((IActivityEdgeInstance) delegatedVisitor, controller, adviceList);

			} else if (delegatedVisitor instanceof ActionActivation) {
				return new ExternallyControlledActionActivation((ActionActivation) delegatedVisitor, controller, adviceList);
			}
		}

		return delegatedVisitor;
	}


	private List<IControllerAdvice> getControllerAdvices(Element element, ISemanticVisitor delegatedVisitor) {
		List<IControllerAdvice> adviceList = new ArrayList<>();
		for (IControllerAdviceFactory factory : adviceFactories) {
			IControllerAdvice advice = factory.getControllerAdvice(element, delegatedVisitor);
			if (advice != null) {
				adviceList.add(advice);
			}
		}
		return adviceList;
	}



}
