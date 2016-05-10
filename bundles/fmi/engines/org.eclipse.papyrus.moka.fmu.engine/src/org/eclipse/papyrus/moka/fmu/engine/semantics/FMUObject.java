package org.eclipse.papyrus.moka.fmu.engine.semantics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.InvocationActions.CS_DefaultConstructStrategy;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.papyrus.moka.fmu.rcp.fmu.FMUInterface;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IRealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.RealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.simex.utils.UMLPrimitiveTypesUtils;
import org.eclipse.papyrus.moka.timedfuml.semantics.Timed_Object;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuralFeature;

public class FMUObject extends Timed_Object implements FMUInterface, IObject_ {

	// FIXME indexes in maps shall be implement with Long
	protected Map<Integer, Double> realMap = new HashMap<Integer, Double>();
	protected Map<Integer, Integer> integerMap = new HashMap<Integer, Integer>();
	protected Map<Integer, Boolean> booleanMap = new HashMap<Integer, Boolean>();
	protected Map<Integer, String> stringMap = new HashMap<Integer, String>();

	protected Map<Integer, Property> indexToUMLProperty ;
	protected Map<Property, Integer> UMLPropertyToIndex ;

	@Override
	public void doStep(double currentCommunicationTime, double stepSize) {
		// TODO
		// FIXME There may be some "rounding" problems with double. 
		// We need to make sure that the current time in the DEEngine equals the currentCommunicationTime,
		// and possibly perform some corrections on the DEEngine side (e.g., update the current DEEngine time, and possibly time on events in 
		// its pool)
	}

	@Override
	public void updateMaps() {
		// TODO Equivalent de synchronize with maps.
	}

	@Override
	public void init() {
		// TODO
		// Pour la phase d'initialisation, l'API FMI distingue :
		// - fmi2SetupExperiment
		// - fmi2EnterInitializationMode
		// - fmi2ExitInitializationMode
		// FIXME Est-ce qu'on doit distinguer aussi dans FMUInterface.java ?

		// The hypothesis is that the this object has been instantiated by the FMU Execution Engine,
		// but it is not constructed yet (in the sense of PSCS Construct Strategy),
		// and its classifier behavior has not been started.
		// This is done prior to the first doStep
		CS_DefaultConstructStrategy strategy = (CS_DefaultConstructStrategy)this.locus.getFactory().getStrategy("constructStrategy") ;
		strategy.constructObject(this, this.types.get(0)) ;
		for (IFeatureValue fv : this.getFeatureValues()) {
			this.synchronizeFeatureValueWithValueMaps(fv.getFeature(), fv.getValues());
		}
	}

	@Override
	public void terminate() {
		// TODO
		// FIXME in the FMI API, terminate() does not "kill" the FMU (this is done by freeInstance()). Is there anything special to do here ?
		// Should we have freeInstance() in FMUInterface.java?
	}

	@Override
	public void setFeatureValue(StructuralFeature feature, List<IValue> values, Integer position) {
		super.setFeatureValue(feature, values, position);
		this.synchronizeFeatureValueWithValueMaps(feature, values) ;
	}

	protected void synchronizeFeatureValueWithValueMaps(StructuralFeature feature, List<IValue> values) {
		if (this.indexToUMLProperty == null && this.UMLPropertyToIndex == null) {
			this.indexToUMLProperty = new HashMap<Integer, Property>() ;
			this.UMLPropertyToIndex = new HashMap<Property, Integer>() ;
			for (Property p : this.types.get(0).allAttributes()) {
				if (FMUEngineUtils.isFMUPort(p)) {
					int index = FMUEngineUtils.getValueReference(p) ;
					if (index != -1) {
						this.indexToUMLProperty.put(index, p) ;
						this.UMLPropertyToIndex.put(p, index) ;
					}
				}
			}
		}
		if (FMUEngineUtils.isFMUPort(feature)) {
			int key = UMLPropertyToIndex.get(feature) ;
			if (feature.getType() == UMLPrimitiveTypesUtils.getReal(feature)) {
				IRealValue realValue = values.isEmpty() ? null : (IRealValue)values.get(0) ;
				Double value = realValue == null ? null : realValue.getValue() ;
				realMap.put(key, value) ;
			}
			else if (feature.getType() == UMLPrimitiveTypesUtils.getInteger(feature)) {
				IIntegerValue integerValue = values.isEmpty() ? null : (IIntegerValue)values.get(0) ;
				Integer value = integerValue == null ? null : integerValue.getValue() ;
				integerMap.put(key, value) ;
			}
			else if (feature.getType() == UMLPrimitiveTypesUtils.getBoolean(feature)) {
				IBooleanValue booleanValue = values.isEmpty() ? null : (IBooleanValue)values.get(0) ;
				Boolean value = booleanValue == null ? null : booleanValue.getValue() ;
				booleanMap.put(key, value) ;
			}
			else if (feature.getType() == UMLPrimitiveTypesUtils.getString(feature)) {
				IStringValue stringValue = values.isEmpty() ? null : (IStringValue)values.get(0) ;
				String value = stringValue == null ? null : stringValue.getValue() ;
				stringMap.put(key, value) ;
			}
			else {
				// FIXME unsupported type. What do we do?
			}
		}
	}

	@Override
	public Map<Integer, Double> fmiGetReals() {
		return realMap;
	}

	@Override
	public Map<Integer, Integer> fmiGetIntegers() {
		return integerMap;
	}

	@Override
	public Map<Integer, Boolean> fmiGetBools() {
		return booleanMap;
	}

	@Override
	public Map<Integer, String> fmiGetStrings() {
		return stringMap;
	}

	@Override
	public void fmiSetReals(Map<Integer, Double> realsMap) {
		this.realMap.putAll(realsMap);
		// updates feature values
		for (Iterator<Integer> i = realMap.keySet().iterator() ; i.hasNext() ; ) {
			Integer key = i.next() ;
			Property p = indexToUMLProperty.get(key) ;
			IFeatureValue fv = this.getFeatureValue(p) ;
			IRealValue realValue = fv.getValues().isEmpty() ? null : (IRealValue)fv.getValues().get(0) ;
			if (realValue == null) {
				realValue = new RealValue() ;
				realValue.setType((PrimitiveType)UMLPrimitiveTypesUtils.getReal(p)) ;
				fv.getValues().add(realValue) ;
			}
			realValue.setValue(realsMap.get(key)) ;
		}
		// TODO Deal with change events once the initialization mode is terminated (i.e., after fmi2ExitInitialization() has been called)
	}

	@Override
	public void fmiSetIntegers(Map<Integer, Integer> integersMap) {
		this.integerMap.putAll(integersMap);
		// updates feature values
		for (Iterator<Integer> i = integerMap.keySet().iterator() ; i.hasNext() ; ) {
			Integer key = i.next() ;
			Property p = indexToUMLProperty.get(key) ;
			IFeatureValue fv = this.getFeatureValue(p) ;
			IIntegerValue integerValue = fv.getValues().isEmpty() ? null : (IIntegerValue)fv.getValues().get(0) ;
			if (integerValue == null) {
				integerValue = new IntegerValue() ;
				integerValue.setType((PrimitiveType)UMLPrimitiveTypesUtils.getInteger(p)) ;
				fv.getValues().add(integerValue) ;
			}
			integerValue.setValue(integerMap.get(key)) ;
		}
		// TODO Deal with change events once the initialization mode is terminated (i.e., after fmi2ExitInitialization() has been called)
	}

	@Override
	public void fmiSetBools(Map<Integer, Boolean> boolsMap) {
		this.booleanMap.putAll(boolsMap);
		// updates feature values
		for (Iterator<Integer> i = booleanMap.keySet().iterator() ; i.hasNext() ; ) {
			Integer key = i.next() ;
			Property p = indexToUMLProperty.get(key) ;
			IFeatureValue fv = this.getFeatureValue(p) ;
			IBooleanValue booleanValue = fv.getValues().isEmpty() ? null : (IBooleanValue)fv.getValues().get(0) ;
			if (booleanValue == null) {
				booleanValue = new BooleanValue() ;
				booleanValue.setType((PrimitiveType)UMLPrimitiveTypesUtils.getBoolean(p)) ;
				fv.getValues().add(booleanValue) ;
			}
			booleanValue.setValue(booleanMap.get(key)) ;
		}
		// TODO Deal with change events once the initialization mode is terminated (i.e., after fmi2ExitInitialization() has been called)
	}

	@Override
	public void fmiSetStrings(Map<Integer, String> stringsMap) {
		this.stringMap.putAll(stringsMap);
		// updates feature values
		for (Iterator<Integer> i = stringMap.keySet().iterator() ; i.hasNext() ; ) {
			Integer key = i.next() ;
			Property p = indexToUMLProperty.get(key) ;
			IFeatureValue fv = this.getFeatureValue(p) ;
			IStringValue stringValue = fv.getValues().isEmpty() ? null : (IStringValue)fv.getValues().get(0) ;
			if (stringValue == null) {
				stringValue = new StringValue() ;
				stringValue.setType((PrimitiveType)UMLPrimitiveTypesUtils.getString(p)) ;
				fv.getValues().add(stringValue) ;
			}
			stringValue.setValue(stringMap.get(key)) ;
		}
		// TODO Deal with change events once the initialization mode is terminated (i.e., after fmi2ExitInitialization() has been called)
	}

	public Map<Integer, Property> getIndexToUMLProperty() {
		return indexToUMLProperty;
	}
}
