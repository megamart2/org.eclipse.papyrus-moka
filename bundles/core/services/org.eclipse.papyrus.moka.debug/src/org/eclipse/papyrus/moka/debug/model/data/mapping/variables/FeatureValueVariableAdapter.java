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
package org.eclipse.papyrus.moka.debug.model.data.mapping.variables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.papyrus.moka.debug.engine.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.model.data.mapping.values.MokaValueAdapterFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.ILink;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IReference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStructuredValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuralFeature;

public class FeatureValueVariableAdapter extends MokaVariableAdapter {

	// Feature value that is adapted in the debug model context
	protected IFeatureValue featureValue;

	protected IStructuredValue featureValueOwner;

	public FeatureValueVariableAdapter(MokaDebugTarget debugTarget, IStructuredValue owner, IFeatureValue featureValue) {
		super(debugTarget);
		this.featureValue = featureValue;
		this.featureValueOwner = owner;
		this.value = null;
	}

	@Override
	public org.eclipse.debug.core.model.IValue getValue() throws DebugException {
		if (this.value == null) {
			int upperBound = this.featureValue.getFeature().getUpper();
			if (upperBound == 1) {
				Association association = null;
				if (this.featureValueOwner instanceof IExtensionalValue) {
					StructuralFeature feature = this.featureValue.getFeature();
					if (feature instanceof Property) {
						association = ((Property) feature).getAssociation();
					}
				}
				IValue fumlValue = null;
				if (association != null) {
					List<ILink> links = this.getMatchingLinks(association,
							this.featureValue.getFeature(),
							this.featureValueOwner,
							((IExtensionalValue) this.featureValueOwner).getLocus());
					List<IValue> resultValues = new ArrayList<IValue>();
					for (int i = 0; i < links.size(); i++) {
						ILink link = links.get(i);
						resultValues.add(link.getFeatureValue(this.featureValue.getFeature()).getValues().iterator().next());
					}
					Iterator<IValue> valuesIterator = resultValues.iterator();
					if (valuesIterator.hasNext()) {
						fumlValue = valuesIterator.next();
					}
				} else {
					Iterator<IValue> valuesIterator = this.featureValue.getValues().iterator();
					if (valuesIterator.hasNext()) {
						fumlValue = valuesIterator.next();
					}
				}
				this.value = MokaValueAdapterFactory.getInstance().instantiate(fumlValue, this.debugTarget);
			} else {

			}
		}
		return this.value;
	}

	@Override
	public String getName() throws DebugException {
		Feature feature = this.featureValue.getFeature();
		if (feature != null) {
			return feature.getName();
		}
		return "";
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValue(org.eclipse.debug.core.model.IValue value) throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsValueModification() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyValue(org.eclipse.debug.core.model.IValue value) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	protected List<ILink> getMatchingLinks(Association association, StructuralFeature end, IValue oppositeValue, ILocus locus) {
		return this.getMatchingLinksForEndValue(association, end, oppositeValue, null, locus);
	}

	private List<ILink> getMatchingLinksForEndValue(Association association, StructuralFeature end, IValue oppositeValue, IValue endValue, ILocus locus) {
		Property oppositeEnd = this.getOppositeEnd(association, end);
		List<IExtensionalValue> extent = locus.getExtent(association);
		List<ILink> links = new ArrayList<ILink>();
		for (int i = 0; i < extent.size(); i++) {
			IExtensionalValue link = extent.get(i);
			IValue value = link.getFeatureValue(oppositeEnd).getValues().get(0);
			if (value instanceof IReference) {
				value = ((IReference) value).getReferent();
			}
			if (value.equals(oppositeValue)) {
				boolean matches = true;
				if (endValue != null) {
					matches = link.getFeatureValue(end).getValues().get(0).equals(endValue);
				}
				if (matches) {
					if (!end.isOrdered() | links.size() == 0) {
						links.add((ILink) link);
					} else {
						int n = link.getFeatureValue(end).getPosition();
						boolean continueSearching = true;
						int j = 0;
						while (continueSearching & j < links.size()) {
							j = j + 1;
							continueSearching = links.get(j - 1).getFeatureValue(end).getPosition() < n;
						}
						if (continueSearching) {
							links.add((ILink) link);
						} else {
							links.add(j - 1, (ILink) link);
						}
					}
				}
			}
		}
		return links;
	}

	private Property getOppositeEnd(Association association, StructuralFeature end) {
		// Get the end of a binary association opposite to the given end.
		Property oppositeEnd = association.getMemberEnds().get(0);
		if (oppositeEnd == end) {
			oppositeEnd = association.getMemberEnds().get(1);
		}
		return oppositeEnd;
	}

}
