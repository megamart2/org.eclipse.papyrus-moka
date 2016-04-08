package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Stereotype;

public class Fmi2Parameters {
	    //private Fmu2ProxyService fmu = new Fmu2ProxyService();	
	    private String fmiVersion = "2.0";
		private String modelName = "";
		private String guid = "";
		private String sourceFiles = "";
		private Boolean canBeInstantiatedOnlyOncePerProcess = false;
		private Boolean canGetAndSetFMUstate = false;
		private Boolean canHandleVariableCommunicationStepSize = false;
		private Boolean canInterpolateInputs = false;
		private Boolean canRunAsynchronuously = false;
		private Boolean canNotUseMemoryManagementFunctions = false;
		private Boolean canSerializeFMUstate = false;
		private int maxOutputDerivativeOrder = 0;
		private String modelIdentifier = "";
		private Boolean needsExecutionTool = false;
		private Boolean providesDirectionalDerivative = false;
		private double startTime = 0;
		private double stopTime = 0;
		private double stepSize = 0;
		private double tolerance = 0;
		private String dllPath = "";
		private String fmuLocation = "";
			
		public Fmi2Parameters(Classifier type, Stereotype st) {
			//this.fmu = fmu;
			
			if (type.getValue(st, "modelName")!=null){
				 this.modelName = type.getValue(st, "modelName").toString();
			}
			
			if (type.getValue(st, "guid")!= null){
				this.guid = type.getValue(st, "guid").toString();
			}
			
			if (type.getValue(st, "sourceFiles")!= null){
				this.sourceFiles = type.getValue(st, "sourceFiles").toString();
			}
			
			if (type.getValue(st, "canBeInstantiatedOnlyOncePerProcess")!= null){
				this.canBeInstantiatedOnlyOncePerProcess = Boolean.parseBoolean(type.getValue(st, "canBeInstantiatedOnlyOncePerProcess").toString());
			}
			
			if (type.getValue(st, "canGetAndSetFMUstate")!= null){
				this.canGetAndSetFMUstate = Boolean.parseBoolean(type.getValue(st, "canGetAndSetFMUstate").toString());
			}
			
			if (type.getValue(st, "canHandleVariableCommunicationStepSize")!= null){
				this.canHandleVariableCommunicationStepSize= Boolean.parseBoolean(type.getValue(st, "canHandleVariableCommunicationStepSize").toString());
			}

			if (type.getValue(st, "canInterpolateInputs")!= null){
				this.canInterpolateInputs = Boolean.parseBoolean(type.getValue(st, "canInterpolateInputs").toString());
			}
			
			if (type.getValue(st, "canRunAsynchronuously")!= null){
				this.canRunAsynchronuously = Boolean.parseBoolean(type.getValue(st, "canRunAsynchronuously").toString());
			}
			
			if (type.getValue(st, "canNotUseMemoryManagementFunctions")!= null){
				this.canNotUseMemoryManagementFunctions = Boolean.parseBoolean(type.getValue(st, "canNotUseMemoryManagementFunctions").toString());
			}
			
			if (type.getValue(st, "canSerializeFMUstate")!= null){
				this.canSerializeFMUstate = Boolean.parseBoolean(type.getValue(st, "canSerializeFMUstate").toString());
			}
			
			if (type.getValue(st, "maxOutputDerivativeOrder")!= null){
				this.maxOutputDerivativeOrder = Integer.parseInt(type.getValue(st, "maxOutputDerivativeOrder").toString());
			}
			
			if (type.getValue(st, "modelIdentifier")!= null){
				this.modelIdentifier = type.getValue(st, "modelIdentifier").toString();
			}
			
			if (type.getValue(st, "needsExecutionTool")!= null){
				this.needsExecutionTool = Boolean.parseBoolean(type.getValue(st, "needsExecutionTool").toString());
			}
			
			if (type.getValue(st, "providesDirectionalDerivative")!= null){
				this.providesDirectionalDerivative = Boolean.parseBoolean(type.getValue(st, "providesDirectionalDerivative").toString());
			}
			
			if (type.getValue(st, "startTime")!= null){
				this.startTime = Double.parseDouble(type.getValue(st, "startTime").toString());
			}
			
			if (type.getValue(st, "stopTime")!= null){
				this.stopTime = Double.parseDouble(type.getValue(st, "stopTime").toString());
			}
			
			if (type.getValue(st, "stepSize")!= null){
				this.stepSize = Double.parseDouble(type.getValue(st, "stepSize").toString());
			}
			
			if (type.getValue(st, "tolerance")!= null){
				this.tolerance = Double.parseDouble(type.getValue(st, "tolerance").toString());
			}
			
			if (type.getValue(st, "dllPath")!= null){
				this.dllPath = type.getValue(st, "dllPath").toString();
			}
			
			if (type.getValue(st, "fmuLocation")!= null){
				this.fmuLocation = type.getValue(st, "fmuLocation").toString();
			}
			
//			this.modelName = fmu.types.get(0).getValue(type, "modelName").toString();
//			this.guid = fmu.types.get(0).getValue(type, "guid").toString();
//			this.sourceFiles = fmu.types.get(0).getValue(type, "sourceFiles").toString();
//			this.canBeInstantiatedOnlyOncePerProcess = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canBeInstantiatedOnlyOncePerProcess").toString());
//			this.canGetAndSetFMUstate = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canGetAndSetFMUstate").toString());
//			this.canHandleVariableCommunicationStepSize= Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canHandleVariableCommunicationStepSize").toString());
//			this.canInterpolateInputs = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canInterpolateInputs").toString());
//			this.canRunAsynchronuously = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canRunAsynchronuously").toString());
//			this.canNotUseMemoryManagementFunctions = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canNotUseMemoryManagementFunctions").toString());
//			this.canSerializeFMUstate = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "canSerializeFMUstate").toString());
//			this.maxOutputDerivativeOrder = Integer.parseInt(fmu.types.get(0).getValue(type, "maxOutputDerivativeOrder").toString());
//			this.modelIdentifier = fmu.types.get(0).getValue(type, "modelIdentifier").toString();
//			this.needsExecutionTool = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "needsExecutionTool").toString());
//			this.providesDirectionalDerivative = Boolean.parseBoolean(fmu.types.get(0).getValue(type, "providesDirectionalDerivative").toString());
//			this.startTime = Double.parseDouble(fmu.types.get(0).getValue(type, "startTime").toString());
//			this.stopTime = Double.parseDouble(fmu.types.get(0).getValue(type, "stopTime").toString());
//			this.stepSize = Double.parseDouble(fmu.types.get(0).getValue(type, "stepSize").toString());
//			this.tolerance = Double.parseDouble(fmu.types.get(0).getValue(type, "tolerance").toString());
//			this.dllPath = fmu.types.get(0).getValue(type, "dllPath").toString();
//			this.fmuLocation = fmu.types.get(0).getValue(type, "fmuLocation").toString();
		}
		public String getFmiVersion() {
			return fmiVersion;
		}
		public void setFmiVersion(String fmiVersion) {
			this.fmiVersion = fmiVersion;
		}
		public String getModelName() {
			return modelName;
		}
		public void setModelName(String modelName) {
			this.modelName = modelName;
		}
		public String getGuid() {
			return guid;
		}
		public void setGuid(String guid) {
			this.guid = guid;
		}
		public String getSourceFiles() {
			return sourceFiles;
		}
		public void setSourceFiles(String sourceFiles) {
			this.sourceFiles = sourceFiles;
		}
		public Boolean getCanBeInstantiatedOnlyOncePerProcess() {
			return canBeInstantiatedOnlyOncePerProcess;
		}
		public void setCanBeInstantiatedOnlyOncePerProcess(Boolean canBeInstantiatedOnlyOncePerProcess) {
			this.canBeInstantiatedOnlyOncePerProcess = canBeInstantiatedOnlyOncePerProcess;
		}
		public Boolean getCanGetAndSetFMUstate() {
			return canGetAndSetFMUstate;
		}
		public void setCanGetAndSetFMUstate(Boolean canGetAndSetFMUstate) {
			this.canGetAndSetFMUstate = canGetAndSetFMUstate;
		}
		public Boolean getCanHandleVariableCommunicationStepSize() {
			return canHandleVariableCommunicationStepSize;
		}
		public void setCanHandleVariableCommunicationStepSize(Boolean canHandleVariableCommunicationStepSize) {
			this.canHandleVariableCommunicationStepSize = canHandleVariableCommunicationStepSize;
		}
		public Boolean getCanInterpolateInputs() {
			return canInterpolateInputs;
		}
		public void setCanInterpolateInputs(Boolean canInterpolateInputs) {
			this.canInterpolateInputs = canInterpolateInputs;
		}
		public Boolean getCanRunAsynchronuously() {
			return canRunAsynchronuously;
		}
		public void setCanRunAsynchronuously(Boolean canRunAsynchronuously) {
			this.canRunAsynchronuously = canRunAsynchronuously;
		}
		public Boolean getCanNotUseMemoryManagementFunctions() {
			return canNotUseMemoryManagementFunctions;
		}
		public void setCanNotUseMemoryManagementFunctions(Boolean canNotUseMemoryManagementFunctions) {
			this.canNotUseMemoryManagementFunctions = canNotUseMemoryManagementFunctions;
		}
		public Boolean getCanSerializeFMUstate() {
			return canSerializeFMUstate;
		}
		public void setCanSerializeFMUstate(Boolean canSerializeFMUstate) {
			this.canSerializeFMUstate = canSerializeFMUstate;
		}
		public int getMaxOutputDerivativeOrder() {
			return maxOutputDerivativeOrder;
		}
		public void setMaxOutputDerivativeOrder(int maxOutputDerivativeOrder) {
			this.maxOutputDerivativeOrder = maxOutputDerivativeOrder;
		}
		public String getModelIdentifier() {
			return modelIdentifier;
		}
		public void setModelIdentifier(String modelIdentifier) {
			this.modelIdentifier = modelIdentifier;
		}
		public Boolean getNeedsExecutionTool() {
			return needsExecutionTool;
		}
		public void setNeedsExecutionTool(Boolean needsExecutionTool) {
			this.needsExecutionTool = needsExecutionTool;
		}
		public Boolean getProvidesDirectionalDerivative() {
			return providesDirectionalDerivative;
		}
		public void setProvidesDirectionalDerivative(Boolean providesDirectionalDerivative) {
			this.providesDirectionalDerivative = providesDirectionalDerivative;
		}
		public double getStartTime() {
			return startTime;
		}
		public void setStartTime(double startTime) {
			this.startTime = startTime;
		}
		public double getStopTime() {
			return stopTime;
		}
		public void setStopTime(double stopTime) {
			this.stopTime = stopTime;
		}
		public double getStepSize() {
			return stepSize;
		}
		public void setStepSize(double stepSize) {
			this.stepSize = stepSize;
		}
		public double getTolerance() {
			return tolerance;
		}
		public void setTolerance(double tolerance) {
			this.tolerance = tolerance;
		}
		public String getDllPath() {
			return dllPath;
		}
		public void setDllPath(String dllPath) {
			this.dllPath = dllPath;
		}
		public String getFmuLocation() {
			return fmuLocation;
		}
		public void setFmuLocation(String fmuLocation) {
			this.fmuLocation = fmuLocation;
		}
		
		
}
	
