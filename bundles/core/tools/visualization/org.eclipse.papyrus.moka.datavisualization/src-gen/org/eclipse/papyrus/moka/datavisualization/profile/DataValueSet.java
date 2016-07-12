/**
 * Copyright (c) 2016 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *   CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.moka.datavisualization.profile;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Value Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getSeries <em>Series</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.moka.datavisualization.profile.VisualizationPackage#getDataValueSet()
 * @model
 * @generated
 */
public interface DataValueSet extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2016 CEA LIST.\r\n\r\n All rights reserved. This program and the accompanying materials\r\n are made available under the terms of the Eclipse Public License v1.0\r\n which accompanies this distribution, and is available at\r\n http://www.eclipse.org/legal/epl-v10.html\r\n\r\n Contributors:\r\n  CEA LIST - Initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata</em>' attribute.
	 * @see #setMetadata(String)
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.VisualizationPackage#getDataValueSet_Metadata()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getMetadata <em>Metadata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata</em>' attribute.
	 * @see #getMetadata()
	 * @generated
	 */
	void setMetadata(String value);

	/**
	 * Returns the value of the '<em><b>Series</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Series</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Series</em>' containment reference list.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.VisualizationPackage#getDataValueSet_Series()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ValueSeries> getSeries();

} // DataValueSet
