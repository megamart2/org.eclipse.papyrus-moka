package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import java.util.List;

import org.eclipse.uml2.uml.StructuralFeature;

public interface IFeatureValue {
	
	public Boolean hasEqualValues(IFeatureValue other);
	
	public IFeatureValue copy();
	
	public List<IValue> getValues();
	
	public void setValues(List<IValue> values);
	
	public StructuralFeature getFeature();
	
	public void setFeature(StructuralFeature feature);
	
	public Integer getPosition();
	
	public void setPosition(Integer position);
}
