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
package org.eclipse.papyrus.moka.debug.model.data.mapping.values;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugElement;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.variables.ItemVariableAdapter;

public class MokaValueList extends MokaDebugElement implements IValue, List<IValue> {

	protected List<IValue> values;

	protected List<IVariable> variables;

	public MokaValueList(MokaDebugTarget debugTarget) {
		super(debugTarget);
		this.values = new ArrayList<IValue>();
		this.variables = new ArrayList<IVariable>();
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return "";
	}

	@Override
	public String getValueString() throws DebugException {
		return "(size = " + this.values.size() + ")";
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if (this.variables.isEmpty()) {
			int index = 1;
			Iterator<IValue> valuesIterator = this.values.iterator();
			while (valuesIterator.hasNext()) {
				this.variables.add(new ItemVariableAdapter(this.debugTarget, index, valuesIterator.next()));
				index++;
			}
		}
		return this.variables.toArray(new IVariable[0]);
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return this.getVariables().length > 0;
	}

	@Override
	public int size() {
		return this.values.size();
	}

	@Override
	public boolean isEmpty() {
		return this.values.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.values.contains(o);
	}

	@Override
	public Iterator<IValue> iterator() {
		return this.values.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.values.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.values.toArray(a);
	}

	@Override
	public boolean add(IValue e) {
		return this.values.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return this.values.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.values.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends IValue> c) {
		return this.values.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends IValue> c) {
		return this.values.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.values.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.values.retainAll(c);
	}

	@Override
	public void clear() {
		this.values.clear();
	}

	@Override
	public IValue get(int index) {
		return this.values.get(index);
	}

	@Override
	public IValue set(int index, IValue element) {
		return this.values.set(index, element);
	}

	@Override
	public void add(int index, IValue element) {
		this.values.add(index, element);
	}

	@Override
	public IValue remove(int index) {
		return this.values.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.values.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.values.lastIndexOf(o);
	}

	@Override
	public ListIterator<IValue> listIterator() {
		return this.values.listIterator();
	}

	@Override
	public ListIterator<IValue> listIterator(int index) {
		return this.values.listIterator(index);
	}

	@Override
	public List<IValue> subList(int fromIndex, int toIndex) {
		return this.values.subList(fromIndex, toIndex);
	}

}
