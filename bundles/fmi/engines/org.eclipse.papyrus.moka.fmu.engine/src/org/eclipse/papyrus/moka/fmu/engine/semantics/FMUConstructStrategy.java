package org.eclipse.papyrus.moka.fmu.engine.semantics;

import java.util.List;

import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.InvocationActions.CS_DefaultConstructStrategy;
import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_Object;
import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_Reference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

public class FMUConstructStrategy extends CS_DefaultConstructStrategy {

	@Override
	public void addStructuralFeatureValue(ICS_Reference context, Property feature, IValue value) {
		IFeatureValue featureValue = context.getFeatureValue(feature);
		if (featureValue != null) {
			List<IValue> values = featureValue.getValues();
			if (feature instanceof Port && !(value instanceof ICS_Object)) {
				values.add(value);
			} else {
				super.addStructuralFeatureValue(context, feature, value);
			}
		}
	}
}