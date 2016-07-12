/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST)
 *  Sebastien REVOL (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.xygraph.mapping.util;

import java.util.HashMap;
import java.util.Set;

public class LUT<K, V> {
	private HashMap<K, V> key2Value;
	private HashMap<V, K> value2Key;
	
	public LUT(){
		key2Value = new HashMap<>();
		value2Key = new HashMap<>();
	}
	
	public void put(K key, V value){
		key2Value.put(key, value);
		value2Key.put(value, key);
	}
	
	public V getValueOf(K key){
		return key2Value.get(key);
	}
	
	public K getKeyOf(V value){
		return value2Key.get(value);
	}
	public Set<V> valueSet(){	
		return value2Key.keySet();
	}
	
	public Set<K> keySet(){
		return key2Value.keySet();
	}
	
}
