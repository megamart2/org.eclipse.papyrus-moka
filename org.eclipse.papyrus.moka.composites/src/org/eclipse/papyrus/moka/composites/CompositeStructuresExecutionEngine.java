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
package org.eclipse.papyrus.moka.composites;

import org.eclipse.papyrus.moka.composites.Semantics.CommonBehaviors.Communications.CS_DispatchOperationOfInterfaceStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.CommonBehaviors.Communications.CS_NameBased_StructuralFeatureOfInterfaceAccessStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.InvocationActions.CS_DefaultConstructStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.CompositeStructures.InvocationActions.CS_DefaultRequestPropagationStrategy;
import org.eclipse.papyrus.moka.composites.Semantics.Loci.LociL3.CS_ExecutionFactory;
import org.eclipse.papyrus.moka.composites.Semantics.Loci.LociL3.CS_Executor;
import org.eclipse.papyrus.moka.composites.Semantics.Loci.LociL3.CS_Locus;
import org.eclipse.papyrus.moka.fuml.FUMLExecutionEngineForMoka;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus;
import org.eclipse.uml2.uml.Behavior;

public class CompositeStructuresExecutionEngine extends FUMLExecutionEngineForMoka {

	// Starts the execution of the given behavior
	@Override
	public void start(Behavior behavior) {
		if (behavior != null) {

			main = behavior;

			// creates the locus, executor and execution factory
			this.locus = new CS_Locus();
			locus.setExecutor(new CS_Executor());
			locus.setFactory(new CS_ExecutionFactory());

			// initializes built-in primitive types
			this.initializeBuiltInPrimitiveTypes(locus);

			// Initializes opaque behavior executions
			this.registerOpaqueBehaviorExecutions(locus);

			// Initializes semantic strategies
			this.registerSemanticStrategies(locus);

			// Initializes system services
			this.registerSystemServices(locus);

			// Initializes arguments
			this.initializeArguments(this.args);

			// Finally launches the execution
			this.started = true;

			// Finally launches the execution
			locus.executor.execute(main, null, this.arguments);
		}
	}

	// Register semantic strategies available in the environment
	@Override
	protected void registerSemanticStrategies(Locus locus) {
		super.registerSemanticStrategies(locus);
		locus.factory.setStrategy(new CS_DispatchOperationOfInterfaceStrategy());
		locus.factory.setStrategy(new CS_NameBased_StructuralFeatureOfInterfaceAccessStrategy());
		locus.factory.setStrategy(new CS_DefaultRequestPropagationStrategy());
		locus.factory.setStrategy(new CS_DefaultConstructStrategy());
	}
}
