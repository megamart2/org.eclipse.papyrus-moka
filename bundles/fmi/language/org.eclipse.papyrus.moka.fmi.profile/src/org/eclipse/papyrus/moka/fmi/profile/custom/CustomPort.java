package org.eclipse.papyrus.moka.fmi.profile.custom;

import org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfileFactory;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfilePackage;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection;
import org.eclipse.papyrus.moka.fmi.fmiprofile.InitialKind;
import org.eclipse.papyrus.moka.fmi.fmiprofile.VariabilityKind;
import org.eclipse.papyrus.moka.fmi.fmiprofile.impl.PortImpl;
import org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;

public class CustomPort extends PortImpl{


	@Override
	public Fmi2ScalarVariable getFmiVariable() {
		if (fmiVariable != null){
			return fmiVariable;
		}else {
			return FMIProfileUtil.getFMIVariable(base_Property);
		}
	}
	@Override
	public String getDescription() {
		if (getFmiVariable() != null){
			return getFmiVariable().getDescription();
		}
		return super.getDescription();
	}

	@Override
	public VariabilityKind getVariability() {
		if (getFmiVariable() != null){
			return (VariabilityKind) FMIProfileFactory.eINSTANCE.createFromString(FMIProfilePackage.eINSTANCE.getVariabilityKind(), getFmiVariable().getVariability().getLiteral());
		}
		return super.getVariability();
	}

	@Override
	public InitialKind getInitial() {
		if (getFmiVariable() != null){
			return (InitialKind) FMIProfileFactory.eINSTANCE.createFromString(FMIProfilePackage.eINSTANCE.getInitialKind(), getFmiVariable().getInitial().getLiteral());
		}
		return super.getInitial();
	}

	@Override
	public int getValueReference() {
		if (getFmiVariable() != null){
			return (int)getFmiVariable().getValueReference();
		}
		return super.getValueReference();
	}

	@Override
	public FlowDirection getDirection() {
		if (getFmiVariable() != null){
			switch( getFmiVariable().getCausality()){
			case INPUT:
				return FlowDirection.IN;
			case OUTPUT:
				return FlowDirection.OUT;
			default:
				return null;			
			}
	}
		return super.getDirection();
	}
	
}
