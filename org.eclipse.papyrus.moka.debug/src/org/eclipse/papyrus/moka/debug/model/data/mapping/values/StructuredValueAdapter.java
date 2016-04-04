package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import java.util.Iterator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.FeatureValueVariableAdapter;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStructuredValue;
import org.eclipse.uml2.uml.Classifier;

public class StructuredValueAdapter extends MokaValueAdapter {

	// Value that is adapted in the debug model context
	protected IStructuredValue value;
	
	public StructuredValueAdapter(MokaDebugTarget debugTarget, IStructuredValue value) {
		super(debugTarget);
		this.value = value;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		String type = "[";
		Iterator<Classifier> typeIterator = this.value.getTypes().iterator();
		while(typeIterator.hasNext()){
			Classifier classifier = typeIterator.next();
			type += classifier.getName();
			if(typeIterator.hasNext()){
				type += ", ";
			}
		}
		type += "]";
		return type;
	}

	@Override
	public String getValueString() throws DebugException {
		return value.toString();
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if(this.variables.isEmpty()){
			Iterator<IFeatureValue> featureValueIterator = this.value.getFeatureValues().iterator();
			while(featureValueIterator.hasNext()){
				this.variables.add(new FeatureValueVariableAdapter(this.debugTarget, value, featureValueIterator.next()));
			}
		}
		return this.variables.toArray(new IVariable[0]);
	}
}
