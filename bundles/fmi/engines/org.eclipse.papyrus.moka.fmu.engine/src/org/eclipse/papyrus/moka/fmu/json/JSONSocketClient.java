package org.eclipse.papyrus.moka.fmu.json;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.eclipse.papyrus.moka.fmu.communication.FMUInterface;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JSONSocketClient extends Thread{


	FMUInterface fmu;
	int portNumber;
	public JSONSocketClient(int portNumber){
	
		this.portNumber= portNumber;
	}
	
	public void setFmu(FMUInterface fmu) {
		this.fmu = fmu;
	}


	@Override
	public void run() {
		
		JsonFactory factory = new JsonFactory();

		JsonParser parser= null;
		JsonGenerator generator= null;
		try (
				Socket socket = new Socket("localhost", portNumber);
				PrintWriter out =
						new PrintWriter(socket.getOutputStream(), true);
				DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
				DataInputStream inputStream = new DataInputStream(socket.getInputStream());
				BufferedReader in =
						new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
				){

			parser = factory.createParser(in);
			generator = factory.createGenerator(out);



			while(parser.nextToken() == JsonToken.START_OBJECT){
				JSONRequest request = new JSONRequest();
				parseRequest(parser, request);

				JSONResponse response = handleRequest(request);
				sendResponse(generator, response);


			}
			parser.close();
			generator.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		








	}


	private void sendResponse(JsonGenerator generator, JSONResponse response) {
		
		try {
			generator.writeStartObject();
			generator.writeStringField(JSONResponse.STATUS, response.status);
			
			if (response.boolVRs !=null){
				generator.writeFieldName(JSONRequest.BOOL_VRS);
				writeIntArray(generator, response.boolVRs);
			}
			if (response.bools != null){
				generator.writeFieldName(JSONRequest.BOOLS);
				writeBoolArray(generator, response.bools);
			}
			if (response.intVRs != null){
				generator.writeFieldName(JSONRequest.INT_VRS);
				writeIntArray(generator, response.intVRs);
			}
			if (response.ints != null){
				generator.writeFieldName(JSONRequest.INTS);
				writeIntArray(generator, response.ints);
			}
			if (response.doubleVRs != null){
				generator.writeFieldName(JSONRequest.DOUBLE_VRS);
				writeIntArray(generator, response.doubleVRs);
			}
			if (response.doubles != null){
				generator.writeFieldName(JSONRequest.DOUBLES);
				writeDoubleArray(generator, response.doubles);
			}
			if (response.stringVRs != null){
				generator.writeFieldName(JSONRequest.STRING_VRS);
				writeIntArray(generator, response.stringVRs);
			}
			if (response.strings != null){
				generator.writeFieldName(JSONRequest.STRINGS);
				writeStringArray(generator, response.strings);
			}
			generator.writeEndObject();
			generator.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		  
		
	}


	private void writeIntArray(JsonGenerator generator,  int[] array) {
		try {
			generator.writeStartArray();
			for (int value: array){
				generator.writeNumber(value);
			}
			generator.writeEndArray();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void writeBoolArray(JsonGenerator generator,  boolean[] array) {
		try {
			generator.writeStartArray();
			for (boolean value: array){
				generator.writeBoolean(value);
			}
			generator.writeEndArray();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void writeDoubleArray(JsonGenerator generator,  double[] array) {
		try {
			generator.writeStartArray();
			for (double value: array){
				generator.writeNumber(value);
			}
			generator.writeEndArray();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void writeStringArray(JsonGenerator generator,  String[] array) {
		try {
			generator.writeStartArray();
			for (String value: array){
				generator.writeString(value);
			}
			generator.writeEndArray();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void parseRequest(JsonParser parser, JSONRequest request) {
		try {
			while (parser.nextToken() != JsonToken.END_OBJECT) {	
				String fieldName = parser.getCurrentName();
				parser.nextToken();

				if (fieldName.equals(JSONRequest.INIT)) {
					request.init = true;
				} else if (fieldName.equals(JSONRequest.TERMINATE) ){
					request.terminate = true;
				} else if (fieldName.equals(JSONRequest.STEP_TIME)) {
					request.t = parser.getValueAsDouble();
				} else if (fieldName.equals(JSONRequest.STEP_SIZE)) {
					request.h = parser.getValueAsDouble();
				} else if (fieldName.equals(JSONRequest.INT_VRS)) {
					parseIntArray(parser, request.intVRs);
				}else if (fieldName.equals(JSONRequest.INTS)) {
					parseIntArray(parser, request.ints);
				}else if (fieldName.equals(JSONRequest.DOUBLE_VRS)) {
					parseIntArray(parser, request.doubleVRs);
				}else if (fieldName.equals(JSONRequest.DOUBLES)) {
					parseDoubleArray(parser, request.doubles);
				}else if (fieldName.equals(JSONRequest.BOOL_VRS)) {
					parseIntArray(parser, request.boolVRs);
				}else if (fieldName.equals(JSONRequest.BOOLS)) {
					parseBoolArray(parser, request.bools);
				}else if (fieldName.equals(JSONRequest.STRING_VRS)) {
					parseIntArray(parser, request.stringVRs);
				}else if (fieldName.equals(JSONRequest.STRINGS)) {
					parseStringArray(parser, request.strings);
				}
				

			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void parseIntArray(JsonParser parser, int[] table) {
		try {
			//we consume first start array tocken
			parser.nextToken();
			int index =0;
			while(!parser.nextToken().equals(JsonToken.END_ARRAY)){
				table[index++] = parser.getValueAsInt();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void parseBoolArray(JsonParser parser, boolean[] table) {
		try {
			//we consume first start array tocken
			parser.nextToken();
			int index =0;
			while(!parser.nextToken().equals(JsonToken.END_ARRAY)){
				table[index++] = parser.getValueAsBoolean();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void parseStringArray(JsonParser parser, String[] table) {
		try {
			//we consume first start array tocken
			parser.nextToken();
			int index =0;
			while(!parser.nextToken().equals(JsonToken.END_ARRAY)){
				table[index++] = parser.getValueAsString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void parseDoubleArray(JsonParser parser, double[] table) {
		try {
			//we consume first start array tocken
			parser.nextToken();
			int index =0;
			while(!parser.nextToken().equals(JsonToken.END_ARRAY)){
				table[index++] = parser.getValueAsDouble();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private JSONResponse handleRequest(JSONRequest request) {
		JSONResponse resp = new JSONResponse();
		if (request.init == true){
			updateValues(request);
			fmu.init();
			fmu.updateMaps();
			updateResponse(resp);
			resp.status = "ok";
		}else if (request.terminate){
			fmu.terminate();
			resp.status = "ok";
		}else {
			//else do step;
			updateValues(request);
			fmu.doStep(request.t, request.h);
			fmu.updateMaps();
			updateResponse(resp);
			resp.status = "ok";
		}
		return resp;
	}


	private void updateResponse(JSONResponse resp) {

		int index=0;
		if (!fmu.fmiGetBools().isEmpty()){
			resp.boolVRs = new int[fmu.fmiGetBools().size()];
			resp.bools = new boolean[fmu.fmiGetBools().size()];

			for(Map.Entry<Integer, Boolean> entry :  fmu.fmiGetBools().entrySet()){
				resp.boolVRs[index] = entry.getKey();
				resp.bools[index] = entry.getValue();
				index++;
			}
			fmu.fmiGetBools().clear();
		}

		if (!fmu.fmiGetStrings().isEmpty()){
			resp.stringVRs = new int[fmu.fmiGetStrings().size()];
			resp.strings = new String[fmu.fmiGetStrings().size()];

			index =0;
			for(Map.Entry<Integer, String> entry : fmu.fmiGetStrings().entrySet()){
				resp.stringVRs[index] = entry.getKey();
				resp.strings[index] = entry.getValue();
				index++;
			}
			fmu.fmiGetStrings().clear();
		}

		if (!fmu.fmiGetReals().isEmpty()){
			resp.doubleVRs = new int[fmu.fmiGetReals().size()];
			resp.doubles = new double[fmu.fmiGetReals().size()];

			index =0;
			for(Map.Entry<Integer, Double> entry : fmu.fmiGetReals().entrySet()){
				resp.doubleVRs[index] = entry.getKey();
				resp.doubles[index] = entry.getValue();
				index++;
			}
			fmu.fmiGetReals().clear();
		}


		if (!fmu.fmiGetIntegers().isEmpty()){
			resp.intVRs = new int[fmu.fmiGetIntegers().size()];
			resp.ints = new int[fmu.fmiGetIntegers().size()];
			index =0;
			for(Map.Entry<Integer, Integer> entry : fmu.fmiGetIntegers().entrySet()){
				resp.intVRs[index] = entry.getKey();
				resp.ints[index] = entry.getValue();
				index++;
			}
			fmu.fmiGetIntegers().clear();
		}
	}


	private void updateValues(JSONRequest request) {
		if (request.boolVRs != null){
		
			for(int i =0; i< request.boolVRs.length; i++){
				fmu.fmiGetBools().put(request.boolVRs[i], request.bools[i]);
			}
		}
		if (request.doubleVRs != null){
			
			for(int i =0; i< request.doubleVRs.length; i++){
				fmu.fmiGetReals().put(request.doubleVRs[i], request.doubles[i]);
			}
		}
		if (request.intVRs != null){
			
			for(int i =0; i< request.intVRs.length; i++){
				fmu.fmiGetIntegers().put(request.intVRs[i], request.ints[i]);
			}
		}
		if (request.stringVRs != null){
			Map<Integer, String> stringMap = fmu.fmiGetStrings();
			for(int i =0; i< request.stringVRs.length; i++){
				stringMap.put(request.stringVRs[i], request.strings[i]);
			}
		}

	}
}
