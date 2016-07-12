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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.moka.datavisualization.profile.VisualizationFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='DataVisualizationProfile'"
 * @generated
 */
public interface VisualizationPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2016 CEA LIST.\r\n\r\n All rights reserved. This program and the accompanying materials\r\n are made available under the terms of the Eclipse Public License v1.0\r\n which accompanies this distribution, and is available at\r\n http://www.eclipse.org/legal/epl-v10.html\r\n\r\n Contributors:\r\n  CEA LIST - Initial API and implementation";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "profile";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://www.eclipse.org/papyrus/datavisualization/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "visualization";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VisualizationPackage eINSTANCE = org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.DataSourceImpl <em>Data Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.DataSourceImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getDataSource()
	 * @generated
	 */
	int DATA_SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Base Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__BASE_DATA_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__VARIABLES = 1;

	/**
	 * The feature id for the '<em><b>Value Sets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE__VALUE_SETS = 2;

	/**
	 * The number of structural features of the '<em>Data Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Data Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VariableImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 1;

	/**
	 * The feature id for the '<em><b>Base Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__BASE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Series</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__SERIES = 1;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.ValueSeriesImpl <em>Value Series</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.ValueSeriesImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getValueSeries()
	 * @generated
	 */
	int VALUE_SERIES = 2;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SERIES__VARIABLE_NAME = 0;

	/**
	 * The number of structural features of the '<em>Value Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SERIES_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Get String Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SERIES___GET_STRING_VALUE__INT = 0;

	/**
	 * The operation id for the '<em>Get Size</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SERIES___GET_SIZE = 1;

	/**
	 * The number of operations of the '<em>Value Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_SERIES_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.DataValueSetImpl <em>Data Value Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.DataValueSetImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getDataValueSet()
	 * @generated
	 */
	int DATA_VALUE_SET = 3;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE_SET__METADATA = 0;

	/**
	 * The feature id for the '<em><b>Series</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE_SET__SERIES = 1;

	/**
	 * The number of structural features of the '<em>Data Value Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE_SET_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Data Value Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.StringSeriesImpl <em>String Series</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.StringSeriesImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getStringSeries()
	 * @generated
	 */
	int STRING_SERIES = 4;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SERIES__VARIABLE_NAME = VALUE_SERIES__VARIABLE_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SERIES__VALUES = VALUE_SERIES_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SERIES_FEATURE_COUNT = VALUE_SERIES_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get String Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SERIES___GET_STRING_VALUE__INT = VALUE_SERIES___GET_STRING_VALUE__INT;

	/**
	 * The operation id for the '<em>Get Size</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SERIES___GET_SIZE = VALUE_SERIES___GET_SIZE;

	/**
	 * The number of operations of the '<em>String Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SERIES_OPERATION_COUNT = VALUE_SERIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.DoubleSeriesImpl <em>Double Series</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.DoubleSeriesImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getDoubleSeries()
	 * @generated
	 */
	int DOUBLE_SERIES = 5;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_SERIES__VARIABLE_NAME = VALUE_SERIES__VARIABLE_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_SERIES__VALUES = VALUE_SERIES_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_SERIES_FEATURE_COUNT = VALUE_SERIES_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get String Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_SERIES___GET_STRING_VALUE__INT = VALUE_SERIES___GET_STRING_VALUE__INT;

	/**
	 * The operation id for the '<em>Get Size</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_SERIES___GET_SIZE = VALUE_SERIES___GET_SIZE;

	/**
	 * The number of operations of the '<em>Double Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_SERIES_OPERATION_COUNT = VALUE_SERIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.IntegerSeriesImpl <em>Integer Series</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.IntegerSeriesImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getIntegerSeries()
	 * @generated
	 */
	int INTEGER_SERIES = 6;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SERIES__VARIABLE_NAME = VALUE_SERIES__VARIABLE_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SERIES__VALUES = VALUE_SERIES_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SERIES_FEATURE_COUNT = VALUE_SERIES_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get String Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SERIES___GET_STRING_VALUE__INT = VALUE_SERIES___GET_STRING_VALUE__INT;

	/**
	 * The operation id for the '<em>Get Size</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SERIES___GET_SIZE = VALUE_SERIES___GET_SIZE;

	/**
	 * The number of operations of the '<em>Integer Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SERIES_OPERATION_COUNT = VALUE_SERIES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.BooleanSeriesImpl <em>Boolean Series</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.BooleanSeriesImpl
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getBooleanSeries()
	 * @generated
	 */
	int BOOLEAN_SERIES = 7;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SERIES__VARIABLE_NAME = VALUE_SERIES__VARIABLE_NAME;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SERIES__VALUES = VALUE_SERIES_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SERIES_FEATURE_COUNT = VALUE_SERIES_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get String Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SERIES___GET_STRING_VALUE__INT = VALUE_SERIES___GET_STRING_VALUE__INT;

	/**
	 * The operation id for the '<em>Get Size</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SERIES___GET_SIZE = VALUE_SERIES___GET_SIZE;

	/**
	 * The number of operations of the '<em>Boolean Series</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SERIES_OPERATION_COUNT = VALUE_SERIES_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataSource <em>Data Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Source</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataSource
	 * @generated
	 */
	EClass getDataSource();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataSource#getBase_DataType <em>Base Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Data Type</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataSource#getBase_DataType()
	 * @see #getDataSource()
	 * @generated
	 */
	EReference getDataSource_Base_DataType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataSource#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Variables</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataSource#getVariables()
	 * @see #getDataSource()
	 * @generated
	 */
	EReference getDataSource_Variables();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataSource#getValueSets <em>Value Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Value Sets</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataSource#getValueSets()
	 * @see #getDataSource()
	 * @generated
	 */
	EReference getDataSource_ValueSets();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.moka.datavisualization.profile.Variable#getBase_Property <em>Base Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Property</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.Variable#getBase_Property()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Base_Property();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.moka.datavisualization.profile.Variable#getSeries <em>Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.Variable#getSeries()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Series();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries <em>Value Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries
	 * @generated
	 */
	EClass getValueSeries();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries#getVariableName <em>Variable Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable Name</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries#getVariableName()
	 * @see #getValueSeries()
	 * @generated
	 */
	EAttribute getValueSeries_VariableName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries#getStringValue(int) <em>Get String Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get String Value</em>' operation.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries#getStringValue(int)
	 * @generated
	 */
	EOperation getValueSeries__GetStringValue__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries#getSize() <em>Get Size</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Size</em>' operation.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries#getSize()
	 * @generated
	 */
	EOperation getValueSeries__GetSize();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet <em>Data Value Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Value Set</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet
	 * @generated
	 */
	EClass getDataValueSet();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metadata</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getMetadata()
	 * @see #getDataValueSet()
	 * @generated
	 */
	EAttribute getDataValueSet_Metadata();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getSeries <em>Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet#getSeries()
	 * @see #getDataValueSet()
	 * @generated
	 */
	EReference getDataValueSet_Series();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.StringSeries <em>String Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.StringSeries
	 * @generated
	 */
	EClass getStringSeries();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.moka.datavisualization.profile.StringSeries#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.StringSeries#getValues()
	 * @see #getStringSeries()
	 * @generated
	 */
	EAttribute getStringSeries_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries <em>Double Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries
	 * @generated
	 */
	EClass getDoubleSeries();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries#getValues()
	 * @see #getDoubleSeries()
	 * @generated
	 */
	EAttribute getDoubleSeries_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.IntegerSeries <em>Integer Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.IntegerSeries
	 * @generated
	 */
	EClass getIntegerSeries();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.moka.datavisualization.profile.IntegerSeries#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.IntegerSeries#getValues()
	 * @see #getIntegerSeries()
	 * @generated
	 */
	EAttribute getIntegerSeries_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.moka.datavisualization.profile.BooleanSeries <em>Boolean Series</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Series</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.BooleanSeries
	 * @generated
	 */
	EClass getBooleanSeries();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.moka.datavisualization.profile.BooleanSeries#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.papyrus.moka.datavisualization.profile.BooleanSeries#getValues()
	 * @see #getBooleanSeries()
	 * @generated
	 */
	EAttribute getBooleanSeries_Values();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VisualizationFactory getVisualizationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.DataSourceImpl <em>Data Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.DataSourceImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getDataSource()
		 * @generated
		 */
		EClass DATA_SOURCE = eINSTANCE.getDataSource();

		/**
		 * The meta object literal for the '<em><b>Base Data Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_SOURCE__BASE_DATA_TYPE = eINSTANCE.getDataSource_Base_DataType();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_SOURCE__VARIABLES = eINSTANCE.getDataSource_Variables();

		/**
		 * The meta object literal for the '<em><b>Value Sets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_SOURCE__VALUE_SETS = eINSTANCE.getDataSource_ValueSets();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VariableImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Base Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__BASE_PROPERTY = eINSTANCE.getVariable_Base_Property();

		/**
		 * The meta object literal for the '<em><b>Series</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__SERIES = eINSTANCE.getVariable_Series();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.ValueSeriesImpl <em>Value Series</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.ValueSeriesImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getValueSeries()
		 * @generated
		 */
		EClass VALUE_SERIES = eINSTANCE.getValueSeries();

		/**
		 * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_SERIES__VARIABLE_NAME = eINSTANCE.getValueSeries_VariableName();

		/**
		 * The meta object literal for the '<em><b>Get String Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VALUE_SERIES___GET_STRING_VALUE__INT = eINSTANCE.getValueSeries__GetStringValue__int();

		/**
		 * The meta object literal for the '<em><b>Get Size</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VALUE_SERIES___GET_SIZE = eINSTANCE.getValueSeries__GetSize();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.DataValueSetImpl <em>Data Value Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.DataValueSetImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getDataValueSet()
		 * @generated
		 */
		EClass DATA_VALUE_SET = eINSTANCE.getDataValueSet();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_VALUE_SET__METADATA = eINSTANCE.getDataValueSet_Metadata();

		/**
		 * The meta object literal for the '<em><b>Series</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_VALUE_SET__SERIES = eINSTANCE.getDataValueSet_Series();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.StringSeriesImpl <em>String Series</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.StringSeriesImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getStringSeries()
		 * @generated
		 */
		EClass STRING_SERIES = eINSTANCE.getStringSeries();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_SERIES__VALUES = eINSTANCE.getStringSeries_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.DoubleSeriesImpl <em>Double Series</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.DoubleSeriesImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getDoubleSeries()
		 * @generated
		 */
		EClass DOUBLE_SERIES = eINSTANCE.getDoubleSeries();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_SERIES__VALUES = eINSTANCE.getDoubleSeries_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.IntegerSeriesImpl <em>Integer Series</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.IntegerSeriesImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getIntegerSeries()
		 * @generated
		 */
		EClass INTEGER_SERIES = eINSTANCE.getIntegerSeries();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_SERIES__VALUES = eINSTANCE.getIntegerSeries_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.moka.datavisualization.profile.impl.BooleanSeriesImpl <em>Boolean Series</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.BooleanSeriesImpl
		 * @see org.eclipse.papyrus.moka.datavisualization.profile.impl.VisualizationPackageImpl#getBooleanSeries()
		 * @generated
		 */
		EClass BOOLEAN_SERIES = eINSTANCE.getBooleanSeries();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_SERIES__VALUES = eINSTANCE.getBooleanSeries_Values();

	}

} //VisualizationPackage
