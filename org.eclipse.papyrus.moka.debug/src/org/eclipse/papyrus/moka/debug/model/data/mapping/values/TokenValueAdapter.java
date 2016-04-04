package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;

public class TokenValueAdapter extends MokaValueAdapter {
	
	protected IToken token;
	
	private final String REFERENCE_TYPE_NAME = "";

	public TokenValueAdapter(MokaDebugTarget debugTarget, IToken token) {
		super(debugTarget);
		this.token = token;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return this.REFERENCE_TYPE_NAME;
	}

	@Override
	public String getValueString() throws DebugException {
		return token.toString();
	}

}
