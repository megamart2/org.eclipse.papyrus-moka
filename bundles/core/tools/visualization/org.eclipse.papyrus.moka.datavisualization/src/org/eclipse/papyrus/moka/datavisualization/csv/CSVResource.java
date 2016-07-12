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
package org.eclipse.papyrus.moka.datavisualization.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.moka.datavisualization.profile.BooleanSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.DataValueSet;
import org.eclipse.papyrus.moka.datavisualization.profile.DoubleSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.IntegerSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.StringSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.ValueSeries;
import org.eclipse.papyrus.moka.datavisualization.profile.VisualizationFactory;
import org.eclipse.papyrus.moka.datavisualization.profile.VisualizationPackage;

public class CSVResource extends ResourceImpl {




	private String separator=";";


	public static final String OPTION_INIT_DATATYPE= "DefiningDataType";
	public static final String OPTION_SEPARATOR= "separator";



	private static final String VALUES_FEATURE_NAME = "values";





	private VisualizationFactory eFactory = (VisualizationFactory) VisualizationPackage.eINSTANCE.getEFactoryInstance();
	private DataValueSet valueSet = eFactory.createDataValueSet();
	




	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {

		valueSet.eSetDeliver(false);
		String name ="default";
		if (getURI() != null){
			name = getURI().toString();
		}
		valueSet.setMetadata("CSV file Name:" + name );

		//first release does not support options
		//initializeOptions(options);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String line =bufferedReader.readLine();

		//we skip emptyLines
		while (line != null && line.trim().isEmpty()){
			line = bufferedReader.readLine();
		}
		if (line != null){
			List<String> variableNames = getVariableNames(line);

			line = bufferedReader.readLine();
			//we skip emptyLines
			while (line != null && line.trim().isEmpty()){
				line = bufferedReader.readLine();
			}
			initializeSeries(line, variableNames);

			while ((line = bufferedReader.readLine()) != null){
				populateValues(line);
			}

		}
			
		bufferedReader.close();

		getContents().add(valueSet);
		valueSet.eSetDeliver(true);
		for (ValueSeries serie : valueSet.getSeries()){
			serie.eSetDeliver(true);
		}

		
	}



//	private void initializeOptions(Map<?, ?> options) {
//		if (options != null){
//
//			Object optionSeparator = options.get(OPTION_SEPARATOR);
//			if (optionSeparator instanceof String){
//				separator = (String) optionSeparator;
//			}
//
//			Object optionDataDef = options.get(OPTION_INIT_DATATYPE);
//			if (Boolean.TRUE.equals(optionDataDef)){
//				initDataType = true;
//			}
//		}
//		
//	}



	private void initializeSeries(String line, List<String> variableNames) {
		String[] tokens = line.split(separator);
		if (tokens != null){
			for(int i = 0; i< tokens.length; i++){
				String token = tokens[i];
				ValueSeries serie = getValueSeries(guessType(token));
				serie.setVariableName(variableNames.get(i));
				 valueSet.getSeries().add(serie);		
				 addInSeries(serie, token);
			}
		}
	}


	private List<String> getVariableNames(String line) {
		List<String> ret = new ArrayList<String>();
		String[] tokens = line.split(separator);
		if (tokens != null){
			for(int i = 0; i< tokens.length; i++){
				ret.add(tokens[i]);
			}
		}
		return ret;
	}




	private int computeAlternateType(String token, int unSuccessfulID){
		switch (unSuccessfulID){

		case VisualizationPackage.BOOLEAN_SERIES :
			//if we parsed "true or false" once and then failed
			//the only possible alternative is String 
			return EcorePackage.ESTRING;

		case VisualizationPackage.INTEGER_SERIES :
			//we parsed an int once and then failed, maybe it's a double
			if(isDouble(token)){
				return EcorePackage.EDOUBLE;
			}else {
				//if it's not a double, it's a string
				return EcorePackage.ESTRING;
			}
		case VisualizationPackage.DOUBLE_SERIES :
			//if we parsed a double once and then failed,
			//we fall back to String
			return EcorePackage.ESTRING;
		}

		return EcorePackage.ESTRING;
	}

	private int guessType(String token){
		if (isBoolean(token)){
			return EcorePackage.EBOOLEAN;
		}else if (isInteger(token)){
			return EcorePackage.EINT;
		}else if (isDouble(token)){
			return 	EcorePackage.EDOUBLE;
		}else {
			return  EcorePackage.ESTRING;
		}

	}



	private boolean isDouble(String token) {
		try{
			Double.parseDouble(token);
			return true;
		}catch(NumberFormatException exDouble){
			return false;
		}
	}

	private boolean isInteger(String token) {
		try{
			Integer.decode(token);
			return true;
		}catch(NumberFormatException exDouble){
			return false;
		}
	}



	private boolean isBoolean(String token) {
		return "TRUE".equalsIgnoreCase(token) || "FALSE".equalsIgnoreCase(token);
	}


	private boolean parseBoolean(String token) throws NumberFormatException{
		if ("TRUE".equalsIgnoreCase(token)){
			return true;
		}else if ("FALSE".equalsIgnoreCase(token)){
			return false;
		}else {
			throw new NumberFormatException();
		}
	}

	private ValueSeries getValueSeries(int dataTypeID){
		ValueSeries ret= null;
		switch (dataTypeID){
		case EcorePackage.EDOUBLE :
			ret = eFactory.createDoubleSeries();
			break;
		case EcorePackage.EBOOLEAN:
			ret=  eFactory.createBooleanSeries();
			break;
		case EcorePackage.EINT :
			ret=  eFactory.createIntegerSeries();
			break;
		default:
			ret= eFactory.createStringSeries();
			break;
		}
		ret.eSetDeliver(false);
		return ret;
	}


	private void addInSeries(ValueSeries serie, String token){
		try{
			int id = serie.eClass().getClassifierID();
			if ( VisualizationPackage.DOUBLE_SERIES== id){
				((DoubleSeries)serie).getValues().add(Double.parseDouble(token));
			}else if (VisualizationPackage.STRING_SERIES == id){
				((StringSeries)serie).getValues().add(token);
			}else if ( VisualizationPackage.INTEGER_SERIES == id){
				((IntegerSeries)serie).getValues().add(Integer.decode(token));
			}else {
				((BooleanSeries)serie).getValues().add(parseBoolean(token));
			}
			
		}catch(NumberFormatException exeption){
			reinitializeSeries(serie, token);
		}

	}


	private void reinitializeSeries(ValueSeries serie, String token) {

		ValueSeries newSerie = getValueSeries(computeAlternateType(token, serie.eClass().getClassifierID()));

		@SuppressWarnings("rawtypes")
		List values = (List) serie.eGet(serie.eClass().getEStructuralFeature(VALUES_FEATURE_NAME));

		for (Object value : values){
			addInSeries(newSerie, value.toString());
		}
		int originalSerieIndex =  valueSet.getSeries().indexOf(serie);
		valueSet.getSeries().remove(originalSerieIndex);
		valueSet.getSeries().add(originalSerieIndex, newSerie);
		newSerie.setVariableName(serie.getVariableName());
		addInSeries(newSerie, token);

		EcoreUtil.delete(serie);

	}



	private void populateValues(String line) throws IOException {
		String[] tokens = line.split(separator);
		for (int i = 0; i< tokens.length; i++){
			//we skip incorrect lines
			if (tokens.length ==  valueSet.getSeries().size()){
				addInSeries( valueSet.getSeries().get(i), tokens[i]);
			}
		}

	}




	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		//first release does not support options
		//initializeOptions(options);
		updateValueSet();
		if (valueSet != null && !valueSet.getSeries().isEmpty()){
			BufferedWriter writter = new BufferedWriter(new PrintWriter(outputStream));
			
			List<ValueSeries> series = valueSet.getSeries();
			int seriesNumber = series.size();
			
			for(int i=0; i< seriesNumber; i++){
				writter.append(series.get(i).getVariableName());
				if (i < seriesNumber -1){
					writter.append(separator);
				}
			}
			writter.newLine();
			
		
			int lineNumber = valueSet.getSeries().get(0).getSize();
			
			for (int lineIndex  =0 ; lineIndex< lineNumber; lineIndex++ ){
				for(int serieIndex  = 0; serieIndex <seriesNumber; serieIndex++){
					writter.append( valueSet.getSeries().get(serieIndex).getStringValue(lineIndex));
					if (serieIndex < seriesNumber-1){
						writter.append(separator);
					}
				}
				if (lineIndex < lineNumber -1){
					writter.newLine();
				}
			
			}
			
			writter.close();
		
			
			
		}
		
	}



	private void updateValueSet() {
		for (EObject content : getContents()){
			if( content instanceof DataValueSet){
				valueSet = (DataValueSet) content;
			}
		}
		
	}


}
