package org.eclipse.papyrus.moka.debug.engine;

import org.eclipse.debug.core.model.IThread;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;

public interface IMokaThread extends IThread {
	
	public void setLogicalThread(IObject_ object);
	
	public IObject_ getLogicalThread();

	public void suspend(ISemanticVisitor visitor);
	
	public ISemanticVisitor getSuspensionPoint();
	
	public void setSuspensionPoint(ISemanticVisitor visitor);

	public boolean getSuspensionFlag();
	
	public void setSuspensionFlag(boolean mustSuspend);
	
}
