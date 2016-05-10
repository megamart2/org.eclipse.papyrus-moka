package org.eclipse.papyrus.moka.fmu.rcp.grpc;

import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.papyrus.moka.fmu.rcp.fmu.FMUInterface;
import org.eclipse.papyrus.moka.rcp.protobuff.FMIServiceGrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {



	private Server server;
	private Logger logger = Logger.getAnonymousLogger();
	private int port;
	private FMUInterface fmu;

	public GrpcServer(int port, FMUInterface fmu) {
		this.port = port;
		this.fmu = fmu;
	}

	/** Start serving requests. */
	public void start() throws IOException {
		server = ServerBuilder.forPort(port)
				.addService(FMIServiceGrpc.bindService(new GrpcFMIServiceHandler(fmu)))
				.build()
				.start();
		logger.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may has been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				GrpcServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	public void blockUntilShutdown() throws InterruptedException {
	    if (server != null) {
	      server.awaitTermination();
	    }
	  }
	
	/** Stop serving requests and shutdown resources. */
	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}
}
