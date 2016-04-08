package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import org.eclipse.uml2.uml.DataType;

public interface IDataValue extends ICompoundValue {
	public DataType getType();

	public void setType(DataType type);


}
