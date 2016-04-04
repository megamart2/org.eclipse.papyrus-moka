package org.eclipse.papyrus.moka.debug.engine;

import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public interface IMokaDebugTarget extends IDebugTarget {
	
	public boolean isNewThread(IObject_ object);
	
	public void registerThread(IObject_ object);
	
	public void unregisterThread(IObject_ object);
	
	public boolean isSuspensionRequired(IObject_ object, ISemanticVisitor nodeVisitor);
	
	public void suspend(IObject_ object, ISemanticVisitor visitor);
	
	public void update(IObject_ object, ISemanticVisitor visitor);
	
	public boolean hasSuspendedThread();
	
}
