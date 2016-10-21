package org.eclipse.papyrus.moka.composites.interfaces.Semantics.Classes.Kernel;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IEvaluation;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;

public interface ICS_OpaqueExpressionEvaluation extends IEvaluation{
	
	public List<IValue> executeExpressionBehavior(); 

}
