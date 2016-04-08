/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.cosimulation;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_Executor;
import org.eclipse.papyrus.moka.discreteevent.DEScheduler;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.cosimulation.semantics.CosimulationDefaultConstructStrategy;
import org.eclipse.papyrus.moka.fuml.cosimulation.semantics.CosimulationLocus;
import org.eclipse.papyrus.moka.timedfuml.TimedUmlExecutionEngine;
import org.eclipse.papyrus.moka.timedfuml.actions._displayCurrentTimeAction;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_ExecutionFactory;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_Locus;
import org.eclipse.uml2.uml.Behavior;

/**
 * @author ac221913
 *
 */
public class CosimulationExecutionEngine extends TimedUmlExecutionEngine {

	

	@Override
	public ILocus initializeLocus() {
		this.locus = new CosimulationLocus();
		this.locus.setFactory(new CosimulationFactory());
		this.locus.setExecutor(new CS_Executor());
		return  this.locus;
	}	

	
	@Override
	protected void registerSemanticStrategies(ILocus locus) {
		// TODO Auto-generated method stub
		super.registerSemanticStrategies(locus);
		locus.getFactory().setStrategy(new CosimulationDefaultConstructStrategy());
	
	}
	
	
	
}
