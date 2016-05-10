package org.eclipse.papyrus.moka.fmu.rcp;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.papyrus.moka.fmu.rcp.fmu.example.FMUC1;
import org.eclipse.papyrus.moka.fmu.rcp.fmu.example.TestFMU;
import org.eclipse.papyrus.moka.fmu.rcp.grpc.GrpcServer;
import org.eclipse.papyrus.moka.fmu.rcp.thrift.ThriftServer;

/**
 * This class controls all aspects of the application's execution
 */
public class FMURCPApplication implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("FMU RCP Running");
		
//		String args[] = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		
//		if (args.length >1){
//			String serverName = args[0];
//		
//			String serverPort = args[1];
//			
//			
//			
//			
//			switch(serverName){
//			case "Thrift":
				ThriftServer thriftServer = new ThriftServer("9090", new FMUC1());
				thriftServer.start();
				
				GrpcServer grpcServer = new GrpcServer(9091, new FMUC1());
				grpcServer.start();
				
				grpcServer.blockUntilShutdown();
				//thriftServer.join();
//				break;
//			default :
//				System.err.println("Error, server " + serverName+ " not supported"); 
//			}
//			
//		}else {
//			System.err.println("Error, wrong command : it should be fmurcp <server_name>  <server_port>");
//		}
		return IApplication.EXIT_OK;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		// nothing to do
	}
}
