/**
 */
package org.eclipse.papyrus.moka.xygraph.model.xygraph;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.moka.xygraph.model.xygraph.XYGraphPackage
 * @generated
 */
public interface XYGraphFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	XYGraphFactory eINSTANCE = org.eclipse.papyrus.moka.xygraph.model.xygraph.impl.XYGraphFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Descriptor</em>'.
	 * @generated
	 */
	XYGraphDescriptor createXYGraphDescriptor();

	/**
	 * Returns a new object of class '<em>Axis Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Axis Descriptor</em>'.
	 * @generated
	 */
	AxisDescriptor createAxisDescriptor();

	/**
	 * Returns a new object of class '<em>Trace Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Trace Descriptor</em>'.
	 * @generated
	 */
	TraceDescriptor createTraceDescriptor();

	/**
	 * Returns a new object of class '<em>Font Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Font Descriptor</em>'.
	 * @generated
	 */
	FontDescriptor createFontDescriptor();

	/**
	 * Returns a new object of class '<em>Color Descriptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Color Descriptor</em>'.
	 * @generated
	 */
	ColorDescriptor createColorDescriptor();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	XYGraphPackage getXYGraphPackage();

} //XYGraphFactory
