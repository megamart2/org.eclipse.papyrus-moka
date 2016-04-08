package org.eclipse.papyrus.moka.fuml.cosimulation.semantics;

import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_Locus;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_Object;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;

public class CosimulationLocus extends Timed_Locus {

	@Override
	public IObject_ instantiate(Class type) {
		// Instantiate the given class at this locus.
		
		IObject_ object = null;
		
		if (type instanceof Behavior) {
			object = super.instantiate(type);
		} else {
			if (type.getAppliedStereotype("FmiMLProfile::CS_FMU") != null) {
				object = new Fmu2ProxyService(type);
				object.createFeatureValues();
				this.add(object);
			} else {
				object = new Timed_Object();
				object.getTypes().add(type);
				object.createFeatureValues();
				this.add(object);
			}
		}
		return object;
	}

}
