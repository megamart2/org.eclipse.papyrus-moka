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
package org.eclipse.papyrus.moka.datavisualization.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.moka.xygraph.mapping.util.DataBatch;

public class DataVisualizationService implements IService{
	
	private List<DataPort> dataPorts;
	private HashMap<String, DataSampleBuffer<Double, Double>> dataBuffers;
	
	private DataVisualizationService(){
		dataPorts = new ArrayList<>();
		dataBuffers = new HashMap<>();
	}
	
	private static DataVisualizationService instance = new DataVisualizationService();
	
	public static DataVisualizationService getInstance(){
		return instance;
	}
	
	static class DataSample<T, V>{
		public T time;
		public V value;
		
		public DataSample(T time, V value) {
			super();
			this.time = time;
			this.value = value;
		}
		
		public DataSample() {
			super();
		}
	}
	
	static class DataSampleBuffer<T, V>{
		private List<DataSample<T, V>> buffer;
		
		public DataSampleBuffer(){
			buffer = new ArrayList<>();			
		}
		
		public void addSample( T t, V v ){
			buffer.add(new DataSample<T, V>(t, v));
		}
		
		public void addBatch(List<T> t, List<V> v){
			for( int i = 0 ; i < t.size(); i++)
				addSample(t.get(i), v.get(i));
		}
		
		public int size(){
			return buffer.size();
		}
		
		public List<T> getTimes(){
			List<T> times = new ArrayList<>(size());
			for( int i = 0; i < buffer.size(); i++)
				times.add( buffer.get(i).time );
			
			return times;
		}
		
		public List<V> getValue(){
			List<V> values = new ArrayList<>(size());
			for( int i = 0; i < buffer.size(); i++)
				values.add( buffer.get(i).value );
			
			return values;
		}
		
		public boolean isEmpty(){
			return buffer.size() == 0;
		}

		public void clear() {
			buffer.clear();
		}
	}
	
	public void registerVariable(String v){
		if( hasVariable(v) )
			return;
		
		//dataPorts.put(v, new ArrayList<>());
		dataBuffers.put(v, new DataSampleBuffer<>());
	}
	
	public boolean hasVariable(String id){
		return dataBuffers.keySet().stream().anyMatch(v -> v.equalsIgnoreCase(id));
	}
	
	private List<DataPort> getDataPortsFor(String v){
//		List<DataPort> ports = dataPorts.get(v);
//		
//		if( ports == null ) //Exception 
//			throw new RuntimeException("Variable unregistered [" + v + "]");
//		
//		return ports;
		
		return dataPorts;
	}
	
	private boolean hasDataPorts(String variable){
		return getDataPortsFor(variable).size() > 0;
	}
	
	public boolean hasBufferedData(String variable){
		if( !hasVariable(variable))
			return false;
		
		return !dataBuffers.get(variable).isEmpty();
	}
	
//	public void addDataPortToVariable(String v, DataPort port){
//		List<DataPort> ports = getDataPortsFor(v);
//		ports.add(port);
//	}
	
	public void addDataPort(DataPort port){
		if( dataPorts.contains(port) )
			return;
		
		dataPorts.add(port);
	}
	
	public void addSample( String v, double t, double value ){
		if( !hasDataPorts(v) ){
			bufferSample(v, t, value);
			return;
		}
		
		flushNewValue(v, t, value);
	}
	
	private void bufferSample(String v, Double t, Double value){
		DataSampleBuffer< Double, Double > buffer = dataBuffers.get(v);
		
		if( buffer == null ) //Variable not registered.
			throw new RuntimeException("Variable unregistered [" + v + "]");
		
		buffer.addSample(t, value);
	}
	
	public void addBatch(String v, List<Double> times, List<Double> values) {
		if( !hasDataPorts(v) ){
			bufferBatch(v, times, values);
			return;
		}
		
		DataBatch tBatch = DataBatch.fromDouble(times);
		DataBatch vBatch = DataBatch.fromDouble(values);
		flushBatch(v, tBatch, vBatch);
	}
	
	private void bufferBatch(String v, List<Double> t, List<Double> valueBatch) {
		DataSampleBuffer< Double, Double > buffer = dataBuffers.get(v);
		
		if( buffer == null ) //Variable not registered.
			throw new RuntimeException("Variable unregistered [" + v + "]");
		
		buffer.addBatch(t, valueBatch);
	}

	public void flushBuffers(){
		for( String variable : dataBuffers.keySet() )
			flushBuffer(variable);
	}
	
	private void flushBuffer(String variable){
		DataSampleBuffer<Double, Double> buffer = dataBuffers.get(variable);
		
		if( buffer == null || buffer.isEmpty())
			return;
		
		List<Double> times = buffer.getTimes();
		List<Double> values = buffer.getValue();
		
		DataBatch tBatch = DataBatch.fromDouble(times);
		DataBatch vBatch = DataBatch.fromDouble(values);
		
		flushBatch(variable, tBatch, vBatch);
		buffer.clear();
	}
	
	private void flushNewValue(String v, Double t, Double value){
		for( DataPort p : getDataPortsFor(v) )
			p.receiveNewValue(v, t, value);
	}
	
	private void flushBatch(String v, DataBatch t, DataBatch valueBatch){
		for( DataPort p : getDataPortsFor(v) )
			p.receiveNewBatch(v, t, valueBatch);
	}
	
	public void addToNotation(){
		
	}

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		//adknasd servicesRegistry.getService(ModelSet.class);
	}

	@Override
	public void startService() throws ServiceException {
		// TODO Auto-generated method stub
	}

	@Override
	public void disposeService() throws ServiceException {
		// TODO Auto-generated method stub
	}
}
