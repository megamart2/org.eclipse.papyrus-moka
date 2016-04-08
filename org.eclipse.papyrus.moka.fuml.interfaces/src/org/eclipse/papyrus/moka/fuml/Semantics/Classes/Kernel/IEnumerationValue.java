package org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;

public interface IEnumerationValue extends IValue {

	public void setType(Enumeration type);

	public Enumeration getType();

	public void setLiteral(EnumerationLiteral literal);

	public EnumerationLiteral getLiteral();

}
