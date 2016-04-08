package org.eclipse.papyrus.moka.timedfuml.semantics;

import org.eclipse.papyrus.moka.composites.Semantics.impl.Loci.LociL3.CS_Locus;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;

/**
 * @author ac221913
 *
 */
public class Timed_Locus extends CS_Locus {

	@Override
	public IObject_ instantiate(Class type) {
		// Overrides PSCS by instantiating a DE_Object
		// instead of a CS_Object

		IObject_ object = null;

		if (type instanceof Behavior) {
			object = super.instantiate(type);
		} else {
			object = new Timed_Object();
			object.getTypes().add(type);
			object.createFeatureValues();
			this.add(object);
		}

		return object;
	}
	
}
