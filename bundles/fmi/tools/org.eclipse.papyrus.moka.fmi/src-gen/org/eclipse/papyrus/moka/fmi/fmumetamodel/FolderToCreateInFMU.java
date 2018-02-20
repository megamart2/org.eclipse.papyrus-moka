/**
 */
package org.eclipse.papyrus.moka.fmi.fmumetamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder To Create In FMU</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.moka.fmi.fmumetamodel.FolderToCreateInFMU#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.moka.fmi.fmumetamodel.FmumetamodelPackage#getFolderToCreateInFMU()
 * @model
 * @generated
 */
public interface FolderToCreateInFMU extends AbstractFile {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.moka.fmi.fmumetamodel.AbstractFile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.papyrus.moka.fmi.fmumetamodel.FmumetamodelPackage#getFolderToCreateInFMU_Children()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<AbstractFile> getChildren();

} // FolderToCreateInFMU
