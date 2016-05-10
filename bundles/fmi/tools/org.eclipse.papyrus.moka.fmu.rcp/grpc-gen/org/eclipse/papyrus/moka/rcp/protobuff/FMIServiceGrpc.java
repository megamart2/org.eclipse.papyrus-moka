package org.eclipse.papyrus.moka.rcp.protobuff;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class FMIServiceGrpc {

  private FMIServiceGrpc() {}

  public static final String SERVICE_NAME = "FMIService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage,
      org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> METHOD_INIT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "FMIService", "init"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest,
      org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> METHOD_DO_STEP =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "FMIService", "doStep"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage,
      org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> METHOD_GET_INITIAL_VALUES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "FMIService", "getInitialValues"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage,
      org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> METHOD_TERMINATE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "FMIService", "terminate"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage.getDefaultInstance()));

  public static FMIServiceStub newStub(io.grpc.Channel channel) {
    return new FMIServiceStub(channel);
  }

  public static FMIServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FMIServiceBlockingStub(channel);
  }

  public static FMIServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FMIServiceFutureStub(channel);
  }

  public static interface FMIService {

    public void init(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> responseObserver);

    public void doStep(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> responseObserver);

    public void getInitialValues(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> responseObserver);

    public void terminate(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> responseObserver);
  }

  public static interface FMIServiceBlockingClient {

    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage init(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request);

    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse doStep(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest request);

    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse getInitialValues(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request);

    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage terminate(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request);
  }

  public static interface FMIServiceFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> init(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request);

    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> doStep(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest request);

    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> getInitialValues(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request);

    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> terminate(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request);
  }

  public static class FMIServiceStub extends io.grpc.stub.AbstractStub<FMIServiceStub>
      implements FMIService {
    private FMIServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FMIServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FMIServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FMIServiceStub(channel, callOptions);
    }

    @java.lang.Override
    public void init(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INIT, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void doStep(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DO_STEP, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void getInitialValues(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INITIAL_VALUES, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void terminate(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TERMINATE, getCallOptions()), request, responseObserver);
    }
  }

  public static class FMIServiceBlockingStub extends io.grpc.stub.AbstractStub<FMIServiceBlockingStub>
      implements FMIServiceBlockingClient {
    private FMIServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FMIServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FMIServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FMIServiceBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage init(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INIT, getCallOptions(), request);
    }

    @java.lang.Override
    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse doStep(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DO_STEP, getCallOptions(), request);
    }

    @java.lang.Override
    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse getInitialValues(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INITIAL_VALUES, getCallOptions(), request);
    }

    @java.lang.Override
    public org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage terminate(org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TERMINATE, getCallOptions(), request);
    }
  }

  public static class FMIServiceFutureStub extends io.grpc.stub.AbstractStub<FMIServiceFutureStub>
      implements FMIServiceFutureClient {
    private FMIServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FMIServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FMIServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FMIServiceFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> init(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INIT, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> doStep(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DO_STEP, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse> getInitialValues(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INITIAL_VALUES, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage> terminate(
        org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TERMINATE, getCallOptions()), request);
    }
  }

  private static final int METHODID_INIT = 0;
  private static final int METHODID_DO_STEP = 1;
  private static final int METHODID_GET_INITIAL_VALUES = 2;
  private static final int METHODID_TERMINATE = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FMIService serviceImpl;
    private final int methodId;

    public MethodHandlers(FMIService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INIT:
          serviceImpl.init((org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage>) responseObserver);
          break;
        case METHODID_DO_STEP:
          serviceImpl.doStep((org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest) request,
              (io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse>) responseObserver);
          break;
        case METHODID_GET_INITIAL_VALUES:
          serviceImpl.getInitialValues((org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse>) responseObserver);
          break;
        case METHODID_TERMINATE:
          serviceImpl.terminate((org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final FMIService serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_INIT,
          asyncUnaryCall(
            new MethodHandlers<
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage,
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage>(
                serviceImpl, METHODID_INIT)))
        .addMethod(
          METHOD_DO_STEP,
          asyncUnaryCall(
            new MethodHandlers<
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepRequest,
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse>(
                serviceImpl, METHODID_DO_STEP)))
        .addMethod(
          METHOD_GET_INITIAL_VALUES,
          asyncUnaryCall(
            new MethodHandlers<
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage,
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.StepResponse>(
                serviceImpl, METHODID_GET_INITIAL_VALUES)))
        .addMethod(
          METHOD_TERMINATE,
          asyncUnaryCall(
            new MethodHandlers<
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage,
              org.eclipse.papyrus.moka.rcp.protobuff.Fmi.EmptyMessage>(
                serviceImpl, METHODID_TERMINATE)))
        .build();
  }
}
