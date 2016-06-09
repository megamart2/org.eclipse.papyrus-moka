package org.eclipse.papyrus.moka.fmu.engine.semantics;

import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_Locus;
import org.eclipse.uml2.uml.Class;

public class FMULocus extends Timed_Locus {

	@Override
	public IObject_ instantiate(Class type) {
		IObject_ object ;
		if (FMIProfileUtil.isCS_FMU(type)) {
			object = new FMUObject() ;
			object.addType(type);
			object.createFeatureValues();
			this.add(object);
		}
		else {
			object = super.instantiate(type) ;
		}
		return object ;
	}

}
