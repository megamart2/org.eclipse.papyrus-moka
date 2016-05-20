/*
 * fmuWrapper.cpp
 *
 *  Created on: Feb 23, 2016
 *      Author: seb
 */


#include <sstream>
#include <memory>

#include "fmi2Functions.h"
#include "JSONFMIComponent.h"

using std::string;



fmi2Component fmi2Instantiate(fmi2String instanceName, fmi2Type fmuType, fmi2String fmuGUID,
		fmi2String fmuResourceLocation, const fmi2CallbackFunctions* callbacks,
		fmi2Boolean visible, fmi2Boolean loggingOn) {

	JSONFMIComponent* ret = new JSONFMIComponent();
	int result = ret->init(fmuResourceLocation);
	if (result != 0){
		return NULL;
	}
	return ret;
}




const char* fmi2GetTypesPlatform() {
	return "default";
}

const char* fmi2GetVersion() {
	return "2.0";
}

fmi2Status fmi2SetDebugLogging(fmi2Component component, fmi2Boolean loggingOn, size_t numberOfCategories,
		const fmi2String categories[]) {
	return fmi2OK;
}



void fmi2FreeInstance(fmi2Component component) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	delete(fmiCmp);
}


fmi2Status fmi2SetupExperiment(fmi2Component component, fmi2Boolean toleranceDefined, fmi2Real tolerance,
		fmi2Real startTime, fmi2Boolean stopTimeDefined, fmi2Real stopTime) {
	return fmi2OK;
}

fmi2Status fmi2EnterInitializationMode(fmi2Component component) {
	return fmi2OK;
}

fmi2Status fmi2ExitInitializationMode(fmi2Component component) {
	return fmi2OK;
}

fmi2Status fmi2Terminate(fmi2Component component) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
		fmiCmp->terminate();
		return fmi2OK;
}

fmi2Status fmi2Reset(fmi2Component component) {
	return fmi2OK;
}

fmi2Status fmi2GetReal(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		fmi2Real values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->getReal(valueReference,numberOfValues, values);
	return fmi2OK;
}

fmi2Status fmi2GetInteger(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		fmi2Integer values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->getInteger(valueReference,numberOfValues, values);
	return fmi2OK;
}

fmi2Status fmi2GetBoolean(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		fmi2Boolean values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->getBoolean(valueReference,numberOfValues, values);
	return fmi2OK;
}

fmi2Status fmi2GetString(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		fmi2String values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->getString(valueReference,numberOfValues, values);
	return fmi2OK;
}

fmi2Status fmi2SetReal(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		const fmi2Real values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->setReal(valueReference,numberOfValues, values);
	return fmi2OK;

}

fmi2Status fmi2SetInteger(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		const fmi2Integer values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->setInteger(valueReference,numberOfValues, values);
	return fmi2OK;

}

fmi2Status fmi2SetBoolean(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		const fmi2Boolean values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->setBoolean(valueReference,numberOfValues, values);
	return fmi2OK;

}

fmi2Status fmi2SetString(fmi2Component component, const fmi2ValueReference valueReference[], size_t numberOfValues,
		const fmi2String values[]) {
	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->setString(valueReference,numberOfValues, values);
	return fmi2OK;

}

fmi2Status fmi2GetFMUstate(fmi2Component component, fmi2FMUstate* state) {
	return fmi2Error;
}

fmi2Status fmi2SetFMUstate(fmi2Component component, fmi2FMUstate state) {
	return fmi2Error;
}

fmi2Status fmi2FreeFMUstate(fmi2Component component, fmi2FMUstate* state) {
	return fmi2Error;
}

fmi2Status fmi2SerializedFMUstateSize(fmi2Component component, fmi2FMUstate, size_t* stateSize) {
	return fmi2Error;
}

fmi2Status fmi2SerializeFMUstate(fmi2Component component, fmi2FMUstate state, fmi2Byte serializedState[],
		size_t serializedStateSize) {
	return fmi2Error;
}

fmi2Status fmi2DeSerializeFMUstate(fmi2Component component, const fmi2Byte serializedState[], size_t size,
		fmi2FMUstate* state) {
	return fmi2Error;
}

fmi2Status fmi2GetDirectionalDerivative(fmi2Component component, const fmi2ValueReference unknownValueReferences[],
		size_t numberOfUnknowns, const fmi2ValueReference knownValueReferences[],
		size_t numberOfKnowns, const fmi2Real knownDifferential[],
		fmi2Real unknownDifferential[]) {
	return fmi2Error;
}

fmi2Status fmi2SetRealInputDerivatives(fmi2Component component, const fmi2ValueReference valueReferences[],
		size_t numberOfValueReferences, const fmi2Integer orders[], const fmi2Real values[]) {
	return fmi2Error;
}

fmi2Status fmi2GetRealOutputDerivatives(fmi2Component component, const fmi2ValueReference valueReference[],
		size_t numberOfValues, const fmi2Integer order[], fmi2Real values[]) {
	return fmi2Error;
}

fmi2Status fmi2DoStep(fmi2Component component, fmi2Real currentComunicationTime, fmi2Real stepSize,
		fmi2Boolean noSetFmuFmuStatePriorToCurrentPoint) {

	AbstractFMIComponent* fmiCmp = reinterpret_cast<AbstractFMIComponent*>(component);
	fmiCmp->doStep(currentComunicationTime, stepSize);
	return fmi2OK;
}

fmi2Status fmi2CancelStep(fmi2Component component) {

	return fmi2OK;
}

fmi2Status fmi2GetStatus(fmi2Component component, const fmi2StatusKind kind, fmi2Status* status) {
	return fmi2Error;
}

fmi2Status fmi2GetRealStatus(fmi2Component component, const fmi2StatusKind kind, fmi2Real* value) {
	return fmi2Error;
}

fmi2Status fmi2GetIntegerStatus(fmi2Component component, const fmi2StatusKind kind, fmi2Integer* value) {
	return fmi2Error;
}

fmi2Status fmi2GetBooleanStatus(fmi2Component component, const fmi2StatusKind kind, fmi2Boolean* value) {
	return fmi2Error;
}

fmi2Status fmi2GetStringStatus(fmi2Component component, const fmi2StatusKind kind, fmi2String* value) {
	return fmi2Error;
}
