package org.eclipse.papyrus.moka.fmi.master.masterlibrary;

import java.util.ArrayList;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Port;


public class Node {
	private Fmi2Port variable;
	private ArrayList<Edge> incomings = new ArrayList<Edge>();
	private ArrayList<Edge> outgoings = new ArrayList<Edge>();
	
	public Node(Fmi2Port variable){
		this.variable =variable;
	}
	
	public Node() {
		// TODO Auto-generated constructor stub
	}


	public ArrayList<Edge> getIncomings() {
		return incomings;
	}

	public void setIncomings(ArrayList<Edge> incomings) {
		this.incomings = incomings;
	}

	public ArrayList<Edge> getOutgoings() {
		return outgoings;
	}

	public void setOutgoings(ArrayList<Edge> outgoings) {
		this.outgoings = outgoings;
	
	}

	public Fmi2Port getVariable() {
		return variable;
	}

	public void setVariable(Fmi2Port variable) {
		this.variable = variable;
	}
	
	
}
