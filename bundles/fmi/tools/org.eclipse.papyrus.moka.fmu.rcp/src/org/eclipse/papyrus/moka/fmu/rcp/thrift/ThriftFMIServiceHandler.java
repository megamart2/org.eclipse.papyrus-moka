package org.eclipse.papyrus.moka.fmu.rcp.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.eclipse.papyrus.moka.fmu.rcp.fmu.FMUInterface;

public class ThriftFMIServiceHandler implements FMIService.Iface{

	StepResponse response = new StepResponse();
	
	FMUInterface fmu;
	
	public ThriftFMIServiceHandler(FMUInterface fmu) {
		this.fmu = fmu;
		
		response.setFmiBooleans(fmu.fmiGetBools());
		response.setFmiIntegers(fmu.fmiGetIntegers());
		response.setFmiReals(fmu.fmiGetReals());
		response.setFmiStrings(fmu.fmiGetStrings());
		
	}
	


	@Override
	public StepResponse doStep(StepRequest req) throws TException {
		double currentTime = req.getCurrentTime();
		double stepSize = req.getStepSize();
		
		fmu.fmiSetBools(req.getSetBooleanRequests());
		fmu.fmiSetReals(req.getSetRealRequests());
		fmu.fmiSetStrings(req.getSetStringRequests());
		fmu.fmiSetIntegers(req.getSetIntegerRequests());

		fmu.doStep(currentTime, stepSize);
		
		fmu.updateMaps();
		
		return response;
	}

	


	

	@Override
	public void terminate() throws TException {
		System.out.println("bye bye!");
		//System.exit(0);
		
	}

	@Override
	public void init() throws TException {
		
		fmu.init();
		System.out.println("Thrift service initialized");
	}

	@Override
	public StepResponse getInitialValues() throws TException {
		fmu.updateMaps();
		return response;
	}
	
	

}
