package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.EventPoolVariableAdapter;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.IObjectActivation;

public class ObjectValueAdapter extends StructuredValueAdapter {
	
	protected IVariable eventPoolVariable;
	
	public ObjectValueAdapter(MokaDebugTarget debugTarget, IObject_ object) {
		super(debugTarget, object);
	}

	@Override
	public String getValueString() throws DebugException {
		return ((IObject_)this.value).getIdentifier();
	}
	
	@Override
	public IVariable[] getVariables() throws DebugException {
		super.getVariables();
		if(this.eventPoolVariable==null){
			IObjectActivation objectActivation = ((IObject_)this.value).getObjectActivation();
			if(objectActivation!=null){
				this.eventPoolVariable = new EventPoolVariableAdapter(this.debugTarget, objectActivation);
				this.variables.add(0, this.eventPoolVariable);
			}
		}

		return this.variables.toArray(new IVariable[0]); 
	}
}
