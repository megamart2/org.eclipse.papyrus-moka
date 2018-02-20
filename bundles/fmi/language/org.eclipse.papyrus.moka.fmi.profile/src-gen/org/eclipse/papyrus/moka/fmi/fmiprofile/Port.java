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
package org.eclipse.papyrus.moka.fmi.fmiprofile;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.moka.fmi.fmiprofile.Port#getBase_Port <em>Base Port</em>}</li>
 *   <li>{@link org.eclipse.papyrus.moka.fmi.fmiprofile.Port#getDirection <em>Direction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfilePackage#getPort()
 * @model
 * @generated
 */
public interface Port extends ScalarVariable {
	/**
	 * Returns the value of the '<em><b>Base Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Port</em>' reference.
	 * @see #setBase_Port(org.eclipse.uml2.uml.Port)
	 * @see org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfilePackage#getPort_Base_Port()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	org.eclipse.uml2.uml.Port getBase_Port();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.moka.fmi.fmiprofile.Port#getBase_Port <em>Base Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Port</em>' reference.
	 * @see #getBase_Port()
	 * @generated
	 */
	void setBase_Port(org.eclipse.uml2.uml.Port value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection
	 * @see #setDirection(FlowDirection)
	 * @see org.eclipse.papyrus.moka.fmi.fmiprofile.FMIProfilePackage#getPort_Direction()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	FlowDirection getDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.moka.fmi.fmiprofile.Port#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(FlowDirection value);

} // Port
