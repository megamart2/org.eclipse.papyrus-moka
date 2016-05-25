/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.fmi.master.masterlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.moka.composites.Semantics.impl.CompositeStructures.StructuredClasses.CS_Object;
import org.eclipse.papyrus.moka.composites.interfaces.Semantics.CompositeStructures.StructuredClasses.ICS_Object;
import org.eclipse.papyrus.moka.fmi.fmiprofile.FlowDirection;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Parameters;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Port;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2ScalarVariable;
import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StructuredClassifier;

public class CoSimEnvironment {
	// UML model objects
	private ICS_Object container = new CS_Object();
	private ArrayList<Fmu2ProxyService> fmus = new ArrayList<Fmu2ProxyService>();
	private List<IObject_> locals = new ArrayList<IObject_>();
	private ArrayList<Connector> connectors = new ArrayList<Connector>();
	private ArrayList<Dependency> ioDependencies = new ArrayList<Dependency>();



	public CoSimEnvironment(List<IExtensionalValue> locus) {
		// TODO Auto-generated method stub
		ArrayList<Fmu2ProxyService> fmusToAdd = new ArrayList<Fmu2ProxyService>();
		for (IExtensionalValue e : locus) {

			for (Classifier type : e.getTypes()) {
				// if CS_graph stereotype is applied then this extensional value represents the co-simulation graph

				if (type.getAppliedStereotype(FMIProfileUtil.CS_GRAPH_STEREO_QUALIFIED_NAME) != null) {
					this.container = ((CS_Object) e);
					for (Connector con : ((CS_Object) e).types.get(0).getOwnedConnectors()) {
						this.connectors.add(con);
					}

					// for (FeatureValue featureValue : e.featureValues) {
					// if (isPartOrReference(featureValue)) {
					// List<Value> values = featureValue.values;
					// for (Value value : values) {
					// if (value instanceof Reference) {
					// Reference partValue = (Reference) value;
					// Object_ referent = partValue.referent; // The value (Object) of the part or reference
					//
					// for (Classifier partType : referent.getTypes()){
					// if (partType.getAppliedStereotype("FmiMLProfile::CS_FMU") != null){ //Imported FMU
					// if (referent instanceof Fmu2ProxyService){
					// fmus.add((Fmu2ProxyService) referent);
					// } else {
					// System.err.println("Invalid type for imported FMU: "+referent.getClass().getName());
					// }
					// } else if (partType.getAppliedStereotype("FmiMLProfile::UML_class") != null){ //Local UML Class
					// locals.add(referent);
					// } //Else: ignore
					// }
					//
					// } // Other cases?
					// }
					// }
					// }

					// for (Property p: ((AsyncObject_)e).types.get(0).getOwnedAttributes()){
					//
					// if (p.getType().getAppliedStereotype("FmiMLProfile::CS_FMU") != null){
					// Fmi2Parameters parameters = new Fmi2Parameters( (Classifier) p.getType(),p.getType().getAppliedStereotype("FmiMLProfile::CS_FMU"));
					//
					// ((Fmu2ProxyService) p.getType()).setParameters(parameters);
					// this.fmus.add((Fmu2ProxyService) p);
					// }
					// }

					break;
				}
				if (type.getAppliedStereotype(FMIProfileUtil.CS_FMU_STEREO_QUALIFIED_NAME) != null) {
					Fmi2Parameters parameters = new Fmi2Parameters(type, type.getAppliedStereotype(FMIProfileUtil.CS_FMU_STEREO_QUALIFIED_NAME));
					((Fmu2ProxyService) e).setParameters(parameters);
					fmusToAdd.add((Fmu2ProxyService) e);
					break;
				}

			}

		}

		this.fmus.addAll(fmusToAdd);
		// retrieve all dependencies from the UML model (from the generated package import)
		this.setupIoDependencies();
		//this.setupFmus();
	}

	protected boolean isPartOrReference(IFeatureValue featureValue) {
		return featureValue != null && featureValue.getFeature() != null && featureValue.getFeature() instanceof Property && featureValue.getFeature().getType() instanceof StructuredClassifier;
	}

//	public void setupFmus() {
//		for (Fmu2ProxyService fmu : fmus) {
//			for (Property p : fmu.types.get(0).getOwnedAttributes()) {
//				Stereotype st = p.getAppliedStereotypes().get(0);
//				if (st != null) {
//					// String variableType = p.getType().getName();
//					Fmi2ScalarVariable variable = new Fmi2ScalarVariable(fmu, p, st);
//					fmu.variables.add(variable);
//
//					// if (variableType.equals(Fmi2VariableType.fmi2Real)){
//					//
//					// }else if (variableType.equals(Fmi2VariableType.fmi2Boolean)){
//					// Fmi2BooleanVariable variable = new Fmi2BooleanVariable(fmu, p, st);
//					// fmu.variables.booleanVariables.add(variable);
//					// }else if (variableType.equals(Fmi2VariableType.fmi2Integer)){
//					// Fmi2IntegerVariable variable = new Fmi2IntegerVariable(fmu, p, st);
//					// fmu.variables.integerVariables.add(variable);
//					// }else if (variableType.equals(Fmi2VariableType.fmi2String)){
//					// Fmi2StringVariable variable = new Fmi2StringVariable(fmu, p, st);
//					// fmu.variables.stringVariables.add(variable);
//					// }
//				}
//			}
//		}
//	}


	/**
	 * creates the port mapping, to be used in doStep() procedure
	 * to retrieve outputs that belong to a given input port
	 * the mapping has the following structure <input port, output port>
	 **/
	public HashMap<Fmi2Port, Fmi2Port> setupPortMapping() {
		Boolean sourceFound = false, targetFound = false;
		HashMap<Fmi2Port, Fmi2Port> P = new HashMap<Fmi2Port, Fmi2Port>();
		if (this.connectors.size() > 0) {
			for (Connector c : this.connectors) {
				Port sourcePort = null;
				Port targetPort = null;
				sourcePort = (Port) c.getEnds().get(0).getRole();
				Stereotype st = sourcePort.getAppliedStereotype(FMIProfileUtil.PORT_STEREO_QUALIFIED_NAME);
				if (st != null) {
					org.eclipse.papyrus.moka.fmi.fmiprofile.Port fmiPort = (org.eclipse.papyrus.moka.fmi.fmiprofile.Port) sourcePort.getStereotypeApplication(st);
					if (fmiPort.getDirection() == FlowDirection.OUT) {
						targetPort = (Port) c.getEnds().get(1).getRole();
					} else {
						sourcePort = (Port) c.getEnds().get(1).getRole();
						targetPort = (Port) c.getEnds().get(0).getRole();
					}
				}
				String sourceFmuName = sourcePort.getFeaturingClassifiers().get(0).getName();
				String targetFmuName = targetPort.getFeaturingClassifiers().get(0).getName();
				Fmu2ProxyService sourceFmu = null, targetFmu = null;
				for (Fmu2ProxyService f : this.fmus) {
					if (f.types.get(0).getName().equals(sourceFmuName)) {
						sourceFmu = f;
						sourceFound = true;
					}
					if (f.types.get(0).getName().equals(targetFmuName)) {
						targetFmu = f;
						targetFound = true;
					}
					if (sourceFound && targetFound)
						continue;
				}

				Fmi2Port key = new Fmi2Port(targetFmu, targetPort, targetPort.getAppliedStereotype(FMIProfileUtil.PORT_STEREO_QUALIFIED_NAME));
				Fmi2Port value = new Fmi2Port(sourceFmu, sourcePort, sourcePort.getAppliedStereotype(FMIProfileUtil.PORT_STEREO_QUALIFIED_NAME));
				P.put(key, value);
				targetFmu.inputPorts.add(key);
				sourceFmu.outputPorts.add(value);
				// if (portType.equals(Fmi2VariableType.fmi2Real)){
				// Fmi2RealPort key = new Fmi2RealPort(targetFmu,targetPort, targetPort.getAppliedStereotype("FmiMLProfile::Port"));
				// Fmi2RealPort value = new Fmi2RealPort(sourceFmu,sourcePort, sourcePort.getAppliedStereotype("FmiMLProfile::Port"));
				// P.put(key, value);
				// targetFmu.inputs.realPorts.add(key);
				// sourceFmu.outputs.realPorts.add(value);
				// } else if (portType.equals(Fmi2VariableType.fmi2Integer)){
				// Fmi2IntegerPort key = new Fmi2IntegerPort(targetFmu,targetPort, targetPort.getAppliedStereotype("FmiMLProfile::Port"));
				// Fmi2IntegerPort value = new Fmi2IntegerPort(sourceFmu,sourcePort, sourcePort.getAppliedStereotype("FmiMLProfile::Port"));
				// P.put(key, value);
				// targetFmu.inputs.integerPorts.add(key);
				// sourceFmu.outputs.integerPorts.add(value);
				// } else if(portType.equals(Fmi2VariableType.fmi2Boolean)){
				// Fmi2BooleanPort key = new Fmi2BooleanPort(targetFmu,targetPort, targetPort.getAppliedStereotype("FmiMLProfile::Port"));
				// Fmi2BooleanPort value = new Fmi2BooleanPort(sourceFmu,sourcePort, sourcePort.getAppliedStereotype("FmiMLProfile::Port"));
				// P.put(key, value);
				// targetFmu.inputs.booleanPorts.add(key);
				// sourceFmu.outputs.booleanPorts.add(value);
				// } else if(portType.equals(Fmi2VariableType.fmi2String)){
				// Fmi2StringPort key = new Fmi2StringPort(targetFmu,targetPort, targetPort.getAppliedStereotype("FmiMLProfile::Port"));
				// Fmi2StringPort value = new Fmi2StringPort(sourceFmu,sourcePort, sourcePort.getAppliedStereotype("FmiMLProfile::Port"));
				// P.put(key, value);
				// targetFmu.inputs.stringPorts.add(key);
				// sourceFmu.outputs.stringPorts.add(value);
				// }
			}
		}
		return P;
	}

	/**
	 * Retrieve dependencies from Papyrus package import.
	 * For each fmu, the dependencies are retrieved from the corresponding package import given the fmu name
	 * Dependencies we are interested in are those annotated with <<outputDependency>> stereotype
	 **/
	public void setupIoDependencies() {
		Model nameSpace = (Model) this.container.getTypes().get(0).getNamespace();
		EList<PackageImport> imports = nameSpace.getPackageImports();
		boolean packageFound = false;
		for (Fmu2ProxyService fmu : this.fmus) {
			Class fmuClass = (Class) fmu.getTypes().get(0);
			
			for (Property fmuProp : fmuClass.getOwnedAttributes()){
				for (Dependency dep : fmuProp.getClientDependencies()){
					if (dep.getAppliedStereotype(FMIProfileUtil.OUTPUT_DEPENDENCY_STEREO_QUALIFIED_NAME) != null) {
						this.ioDependencies.add(dep);
					}
				}
			}
			
		}
	}


	public ICS_Object getContainer() {
		return container;
	}

	public void setContainer(ICS_Object container) {
		this.container = container;
	}

	public ArrayList<Fmu2ProxyService> getFmus() {
		return fmus;
	}

	public void setFmus(ArrayList<Fmu2ProxyService> fmus) {
		this.fmus = fmus;
	}

	public ArrayList<Connector> getConnectors() {
		return connectors;
	}

	public void setConnectors(ArrayList<Connector> connectors) {
		this.connectors = connectors;
	}

	public ArrayList<Dependency> getIoDependencies() {
		return ioDependencies;
	}


	public void setIoDependencies(ArrayList<Dependency> ioDependencies) {
		this.ioDependencies = ioDependencies;
	}


	// /**
	// * creates the dependency graph from the coSimgraph
	// **/
	// public DependencyGraph setupDependencyGraph(HashMap<Fmi2PortVariable, Fmi2PortVariable> P){
	// //create the edges of the dependency graph
	// //Port mapping --> Output 2 Input
	// //Dependencies --> Input 2 Output
	// DependencyGraph depGraph = new DependencyGraph();
	// Node sourceNode = null;
	// Node targetNode = null;
	// for (Fmi2PortVariable key : P.keySet()){
	// Fmi2PortVariable value = P.get(key);
	// //create an edge from the output to the input
	// //this correspond to create an edge from value to key
	// // the key is an input port --> target
	// //the value is an output port --> source
	// sourceNode = new Node(value);
	// targetNode = new Node(key);
	// Edge edge = new Edge(sourceNode,targetNode);
	// depGraph.addNode(sourceNode);
	// depGraph.addNode(targetNode);
	// depGraph.addEdge(edge);
	// }
	//
	//
	// for (Dependency d : ioDependencies){
	// Port client = (Port)d.getClients().get(0); //an output port --> target node,
	// Port supplier = (Port)d.getSuppliers().get(0); //an input port --> source node
	// for (Node n :depGraph.getNodes()){
	// if ((n.getVariable().getPort()).equals(client)){
	// targetNode = n;
	// }
	// if ((n.getVariable().getPort()).equals(supplier)){
	// sourceNode = n;
	// }
	// }
	// Edge edge = new Edge(sourceNode,targetNode);
	// depGraph.addEdge(edge);
	// }
	// return depGraph;
	// }


}
