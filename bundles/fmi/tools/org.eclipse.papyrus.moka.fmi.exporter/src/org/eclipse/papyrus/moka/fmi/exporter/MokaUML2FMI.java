package org.eclipse.papyrus.moka.fmi.exporter;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.papyrus.moka.fmi.fmiprofile.CS_FMU;
import org.eclipse.papyrus.moka.fmi.fmiprofile.CalculatedParameter;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Independent;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Local;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Parameter;
import org.eclipse.papyrus.moka.fmi.fmiprofile.Port;
import org.eclipse.papyrus.moka.fmi.fmiprofile.ScalarVariable;
import org.eclipse.papyrus.moka.fmi.modeldescription.BooleanType;
import org.eclipse.papyrus.moka.fmi.modeldescription.CausalityType;
import org.eclipse.papyrus.moka.fmi.modeldescription.CoSimulationType;
import org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.modeldescription.Fmi2VariableDependency;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiFactory;
import org.eclipse.papyrus.moka.fmi.modeldescription.FmiModelDescriptionType;
import org.eclipse.papyrus.moka.fmi.modeldescription.InitialType;
import org.eclipse.papyrus.moka.fmi.modeldescription.IntegerType;
import org.eclipse.papyrus.moka.fmi.modeldescription.ModelStructureType;
import org.eclipse.papyrus.moka.fmi.modeldescription.ModelVariablesType;
import org.eclipse.papyrus.moka.fmi.modeldescription.RealType;
import org.eclipse.papyrus.moka.fmi.modeldescription.StringType;
import org.eclipse.papyrus.moka.fmi.modeldescription.UnknownType1;
import org.eclipse.papyrus.moka.fmi.modeldescription.VariabilityType;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.papyrus.moka.utils.UMLPrimitiveTypesUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Property;

public class MokaUML2FMI {

	public static void setDefaultValuesForMokaFMU(Class umlClass) {
		CS_FMU csFMU = FMIProfileUtil.getCSFMU(umlClass);

		csFMU.setCanHandleVariableCommunicationStepSize(true);
		csFMU.setCanInterpolateInputs(false);
		csFMU.setCanRunAsynchronuously(false);
		csFMU.setMaxOutputDerivativeOrder(0);
		csFMU.setNeedsExecutionTool(false);
		csFMU.setFmiVersion("2.0");
		csFMU.setGenerationTool("Moka Exporter");

	}

	public static FmiModelDescriptionType getModelDescription(Class umlClass, String modelIdentifier) {
		CS_FMU csFMU = FMIProfileUtil.getCSFMU(umlClass);

		if (csFMU != null) {
			FmiModelDescriptionType result = FmiFactory.eINSTANCE.createFmiModelDescriptionType();
			result.setGenerationTool("Moka FMU exporter");
			result.setFmiVersion("2.0");

			GregorianCalendar date = new GregorianCalendar();
			date.setTime(new Date());
			XMLGregorianCalendar xmlDate;
			try {
				xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
				result.setGenerationDateAndTime(xmlDate);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}

			result.setModelName(umlClass.getLabel());
			result.setGuid(UUID.randomUUID().toString());

			CoSimulationType cosim = FmiFactory.eINSTANCE.createCoSimulationType();
			result.getCoSimulation().add(cosim);
			cosim.setCanGetAndSetFMUstate(false);
			cosim.setCanHandleVariableCommunicationStepSize(true);
			cosim.setCanBeInstantiatedOnlyOncePerProcess(true);
			cosim.setCanInterpolateInputs(false);
			cosim.setCanNotUseMemoryManagementFunctions(true);
			cosim.setCanSerializeFMUstate(false);
			cosim.setCanRunAsynchronuously(false);
			cosim.setNeedsExecutionTool(false);
			cosim.setProvidesDirectionalDerivative(false);
			cosim.setMaxOutputDerivativeOrder(0);

			if (modelIdentifier == null) {
				modelIdentifier = umlClass.getName();
			}
			cosim.setModelIdentifier(modelIdentifier);

			List<ScalarVariable> umlVariables = FMIProfileUtil.collectScalarVariables(umlClass);
			List<Port> outputUMLVariables = new ArrayList<Port>();
			if (!umlVariables.isEmpty()) {
				ModelVariablesType modelVariables = FmiFactory.eINSTANCE.createModelVariablesType();
				result.setModelVariables(modelVariables);
				for (ScalarVariable umlVariable : umlVariables) {
					modelVariables.getScalarVariable().add(createFMIScalarVariable(umlVariable));
					if (umlVariable instanceof Port && ((Port) umlVariable).getDirection().equals(FlowDirection.OUT)) {
						outputUMLVariables.add((Port) umlVariable);
					}
				}

			}

			if (!outputUMLVariables.isEmpty()) {

				ModelStructureType modelStructure = FmiFactory.eINSTANCE.createModelStructureType();
				result.setModelStructure(modelStructure);
				Fmi2VariableDependency outputs = FmiFactory.eINSTANCE.createFmi2VariableDependency();
				modelStructure.setOutputs(outputs);
				for (Port outputPort : outputUMLVariables) {
					outputs.getUnknown().add(createUnknown(outputPort, umlVariables));
				}

			}

			return result;
		} else {
			return null;
		}

	}

	private static UnknownType1 createUnknown(Port outputPort, List<ScalarVariable> umlVariables) {
		UnknownType1 result = FmiFactory.eINSTANCE.createUnknownType1();
		result.setIndex(umlVariables.indexOf(outputPort) + 1);
		return result;
	}

	private static Fmi2ScalarVariable createFMIScalarVariable(ScalarVariable umlVariable) {
		Fmi2ScalarVariable result = FmiFactory.eINSTANCE.createFmi2ScalarVariable();
		Property baseProperty = umlVariable.getBase_Property();
		if (baseProperty == null && umlVariable instanceof Port) {
			baseProperty = ((Port) umlVariable).getBase_Port();
		}
		result.setName(baseProperty.getName());
		result.setValueReference(umlVariable.getValueReference());

		if (!baseProperty.getOwnedComments().isEmpty()) {
			String description = "";
			for (Comment comment : baseProperty.getOwnedComments()) {
				if (comment.getBody() != null) {
					description += comment.getBody() + "\n";
				}
			}
			result.setDescription(description);
		}

		result.setVariability(VariabilityType.DISCRETE);

		CausalityType causality = computeCausality(umlVariable);
		result.setCausality(causality);
		updateTypeAndInitial(result, baseProperty, causality);

		return result;
	}

	private static void updateTypeAndInitial(Fmi2ScalarVariable result, Property baseProperty,
			CausalityType causality) {
		if (baseProperty.getType().equals(UMLPrimitiveTypesUtils.getReal(baseProperty))) {
			RealType fmiReal = FmiFactory.eINSTANCE.createRealType();
			result.setReal(fmiReal);
			if (causality == CausalityType.INPUT) {
				result.setInitial(InitialType.APPROX);
			} else {
				result.setInitial(InitialType.EXACT);
			}
			String defaultValue = baseProperty.getDefault();
			if (defaultValue != null) {
				fmiReal.setStart(Double.parseDouble(defaultValue));
			}

		} else if (baseProperty.getType().equals(UMLPrimitiveTypesUtils.getInteger(baseProperty))) {
			IntegerType fmiInteger = FmiFactory.eINSTANCE.createIntegerType();
			result.setInteger(fmiInteger);
			if (causality == CausalityType.INPUT) {
				result.setInitial(InitialType.APPROX);
			} else {
				result.setInitial(InitialType.EXACT);
			}
			String defaultValue = baseProperty.getDefault();
			if (defaultValue != null) {
				fmiInteger.setStart(Integer.decode(defaultValue));
			}

		} else if (baseProperty.getType().equals(UMLPrimitiveTypesUtils.getBoolean(baseProperty))) {
			BooleanType fmiBoolean = FmiFactory.eINSTANCE.createBooleanType();
			result.setBoolean(fmiBoolean);
			if (causality == CausalityType.INPUT) {
				result.setInitial(InitialType.APPROX);
			} else {
				result.setInitial(InitialType.EXACT);
			}
			String defaultValue = baseProperty.getDefault();
			if (defaultValue != null) {
				fmiBoolean.setStart(Boolean.getBoolean(defaultValue));
			}

		} else {
			StringType fmiString = FmiFactory.eINSTANCE.createStringType();
			result.setString(fmiString);
			if (causality == CausalityType.INPUT) {
				result.setInitial(InitialType.APPROX);
			} else {
				result.setInitial(InitialType.EXACT);
			}
			String defaultValue = baseProperty.getDefault();
			if (defaultValue != null) {
				fmiString.setStart(defaultValue);
			}
		}

	}

	public static CausalityType computeCausality(ScalarVariable umlVariable) {
		if (umlVariable instanceof Local) {
			return CausalityType.LOCAL;
		} else if (umlVariable instanceof CalculatedParameter) {
			return CausalityType.CALCULATED_PARAMETER;
		} else if (umlVariable instanceof Port) {
			FlowDirection direction = ((Port) umlVariable).getDirection();
			if (direction.equals(FlowDirection.IN)) {
				return CausalityType.INPUT;
			} else {
				return CausalityType.OUTPUT;
			}
		} else if (umlVariable instanceof Parameter) {
			return CausalityType.PARAMETER;
		} else if (umlVariable instanceof Independent) {
			return CausalityType.INDEPENDENT;
		}

		return null;
	}
}
