package org.eclipse.papyrus.moka.timedfuml.semantics;

import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;

/**
 * @author ac221913
 *
 */
public class Timed_Object extends CS_Object {

	@Override
	public void startBehavior(Class classifier, List<IParameterValue> inputs) {
		// Overrides default startBehavior by instantiating a DE_ObjectActivation
		// Create an object activation for this object (if one does not already
		// exist) and start its behavior(s).
		// Debug.println("[startBehavior] On object...");
		if (this.objectActivation == null) {
			this.objectActivation = new Timed_ObjectActivation();
			this.objectActivation.setObject(this);
		}
		// Debug.println("[startBehavior] objectActivation = " +
		// objectActivation);
		this.objectActivation.startBehavior(classifier, inputs);
	}
	
}
