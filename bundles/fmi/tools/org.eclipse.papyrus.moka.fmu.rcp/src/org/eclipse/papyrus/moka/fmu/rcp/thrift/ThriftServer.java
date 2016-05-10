package org.eclipse.papyrus.moka.fmu.rcp.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.papyrus.moka.fmu.rcp.fmu.FMUInterface;

public class ThriftServer extends Thread {

	private ThriftFMIServiceHandler handler;
	private FMIService.Processor<FMIService.Iface> processor;
	TServer server;
	
	public ThriftServer(String port, FMUInterface fmu){
		
		handler = new ThriftFMIServiceHandler(fmu);
		processor = new FMIService.Processor<FMIService.Iface>(handler);
		
		
		
	      TServerTransport serverTransport;
		try {
			
			serverTransport = new TServerSocket(Integer.decode(port));
			server = new TSimpleServer(new Args(serverTransport).processor(processor));
			
			
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      

	}
	
	@Override
	public void run() {
		server.serve();
	}
}
