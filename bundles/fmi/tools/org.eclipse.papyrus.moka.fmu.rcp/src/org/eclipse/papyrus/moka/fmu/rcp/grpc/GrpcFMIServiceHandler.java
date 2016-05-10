package org.eclipse.papyrus.moka.fmu.rcp.grpc;

import org.eclipse.papyrus.moka.fmu.rcp.fmu.FMUInterface;
import org.eclipse.papyrus.moka.rcp.protobuff.FMIServiceGrpc.FMIService;
import org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage;
import org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest;
import org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse;
import org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse.Builder;

import io.grpc.stub.StreamObserver;

public class GrpcFMIServiceHandler implements FMIService{

	FMUInterface fmu;
	
	
	public GrpcFMIServiceHandler(FMUInterface fmu) {
		this.fmu = fmu;
		
	}
	
	private StepResponse getResponse(){
		fmu.updateMaps();
		Builder builder = StepResponse.newBuilder();
		
		
		builder.getMutableFmiBooleans().putAll(fmu.fmiGetBools());
		builder.getMutableFmiIntegers().putAll(fmu.fmiGetIntegers());
		builder.getMutableFmiReals().putAll(fmu.fmiGetReals());
		builder.getMutableFmiStrings().putAll(fmu.fmiGetStrings());
		
		return builder.build();
	}
	
	@Override
	public void init(EmptyMessage request, StreamObserver<EmptyMessage> responseObserver) {
		fmu.init();
		responseObserver.onNext(EmptyMessage.newBuilder().build());
		responseObserver.onCompleted();
		
	}

	@Override
	public void doStep(StepRequest request, StreamObserver<StepResponse> responseObserver) {
		fmu.doStep(request.getCurrentTime(), request.getStepSize());

		responseObserver.onNext(getResponse());
		responseObserver.onCompleted();
	}

	@Override
	public void getInitialValues(EmptyMessage request, StreamObserver<StepResponse> responseObserver) {
	
		responseObserver.onNext(getResponse());
		responseObserver.onCompleted();		
	}

	@Override
	public void terminate(EmptyMessage request, StreamObserver<EmptyMessage> responseObserver) {
		responseObserver.onNext(EmptyMessage.newBuilder().build());
		responseObserver.onCompleted();

	}

}
