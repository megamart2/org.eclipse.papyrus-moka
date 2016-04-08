package org.eclipse.papyrus.moka.fmi.master.fmuutils;

import java.io.File;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2CallbackFunctions;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Status;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Type;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.NativeSizeT;
import org.eclipse.papyrus.moka.fmi.master.fmulibrary.Fmu2CallbackFunctions;
import org.eclipse.papyrus.moka.fmi.master.fmuproxy.Fmu2ProxyService;

import com.sun.jna.Function;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;

public class CoSimulation2Utils2 {
	
	//create buffer to real variables
	double h_vr = 0;
	double v_vr = 2;
	double g_vr=4;
	double e_vr=5; 
 	static char csvSeparator = ',';
    static boolean enableLogging = false;
    static double endTime = 1.0;
    Fmu2CallbackFunctions.FMUAllocateMemory fmuAllocateMemory;
    static String fmuFileName = "";
    String modelIdentifier="";
    NativeLibrary nativeLibrary;
    static String outputFileName = "results.csv";
    static double stepSize = 0.1;
    private Fmu2CallbackFunctions.FMUAllocateMemory mem;
    Pointer fmiComponent =null;
    public Fmu2CallbackFunctions.FMUAllocateMemory getFMUAllocateMemory() {
    	if (mem == null) {
    		mem = new Fmu2CallbackFunctions.FMUAllocateMemory();
    	}
    	return mem;
    }
    
    
	public void invoke(String name, Object[] arguments, String message) {
        Function function = getFunction(name);
        invoke(function, arguments, message);
    }
	
    //if FMI1.0 the name of the function is preceded by modelIdentifier
	public Function getFunction(String name) {
        return nativeLibrary.getFunction(modelIdentifier + name);
    }
	
	public void invoke(Function function, Object[] arguments, String message) {
        if (enableLogging) {
            System.out.println("About to call " + function.getName());
        }
        int fmiFlag = ((Integer) function.invoke(Integer.class, arguments))
                .intValue();
        if (fmiFlag > Fmi2Status.fmi2Warning) {
            throw new RuntimeException(message + fmiFlag);
        }
    }
	
	public NativeLibrary loadLib(String dll){
		return NativeLibrary.getInstance(dll);
		
	}
	
	
	public int instantiateAnFmu(Fmu2ProxyService fmu,String dll, String id, String guid, String fmuLocation){
		
		nativeLibrary = NativeLibrary.getInstance(dll);
	      
//		try {
//			fmuLocation = new File(fmuFileName).toURI().toURL().toString();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	        //create callback object
	        Fmi2CallbackFunctions.ByValue callbacks = new Fmi2CallbackFunctions.ByValue(
	        		new Fmu2CallbackFunctions.FMULogger(), new Fmu2CallbackFunctions.FMUAllocateMemory(),
	        		new Fmu2CallbackFunctions.FMUFreeMemory(),
	        		new Fmu2CallbackFunctions.FMUStepFinished(), 
	                null);
	      Function instantiateSlave = getFunction("fmi2Instantiate");
	      fmu.component = (Pointer) instantiateSlave.invoke(Pointer.class,
	        					new Object[] {id, Fmi2Type.fmi2CoSimulation, 
	        					guid, fmuLocation, callbacks, false, false});
	        if (fmu.component.equals(Pointer.NULL)) {
	            throw new RuntimeException("Could not instantiate model.");
	        }
	        return 1;
	}
	
	
	public int setupExperiments(Fmu2ProxyService fmu, double startTime, double stopTime){
		
		 //setup experiments
        invoke("fmi2SetupExperiment", new Object[]{fmu.component, false, startTime, false, stopTime}, "Could not initialize slave: ");
        return 1;
	}
	
	
	public int initializeAnFmu(Fmu2ProxyService fmu){
		
        
        //Initialize FMUs 
        invoke("fmi2EnterInitializationMode", new Object[]{fmu.component}, "error entering intialization mode");
        
        invoke("fmi2ExitInitializationMode", new Object[]{fmu.component}, "error exiting intialization mode");
        
        return 1;
	}
	
	public int terminateSimulation(Fmu2ProxyService fmu){
		
        	invoke("fmi2Terminate", new Object[]{fmu.component},"error when terminating");
        
              
        	invoke("fmi2FreeInstance", new Object[]{fmu.component},"error when freeing memory");
        	return 1;
	}
	
	public void simulateAnFmu(Fmu2ProxyService fmu, double startTime, double stopTime, double stepSize){
		
        	//retrieve outputs
        	double currenttime = startTime;
        	int fmiFlag=1; 
        	
        	IntBuffer valueReferenceIntBuffer = IntBuffer.allocate(4);
        	DoubleBuffer valueBuffer = DoubleBuffer.allocate(4);
        	valueReferenceIntBuffer.put(0,(int) h_vr);
        	valueReferenceIntBuffer.put(1,(int) v_vr);
        	valueReferenceIntBuffer.put(2,(int) g_vr);
        	valueReferenceIntBuffer.put(3,(int) e_vr);
        	
            while ((currenttime<stopTime)){
            	//retrieve outputs
            	
            	
            	//call slaves and check status
                Function doStep = getFunction("fmi2DoStep");
                fmiFlag = (Integer) doStep.invoke(Integer.class,
                					new Object[] {fmu.component, currenttime, stepSize, false});	
               
                
                
                Function getreal = getFunction("fmi2GetReal");
                fmiFlag = ((Integer) getreal.invoke(Integer.class, new Object[] {
                        fmu.component, valueReferenceIntBuffer, new NativeSizeT(4),
                        valueBuffer })).intValue();
                
                System.out.println(	"variable h= " + valueBuffer.get(0) + 
                		"variable v= " + valueBuffer.get(1) + 
                		"variable g= " + valueBuffer.get(2) + 
                		"variable e= " + valueBuffer.get(3) );
                currenttime += stepSize;
            }
            
	}


	public void getresult(){
		
	}
	
	public void simulate(String fmuFileName, String sharedLibrary, String id, String guid, double endTime,
	            double stepSize, boolean enableLogging, char csvSeparator,
	            String outputFileName) throws Exception{
		
       
    	IntBuffer valueReferenceIntBuffer = IntBuffer.allocate(4);
    	DoubleBuffer valueBuffer = DoubleBuffer.allocate(4);
    	valueReferenceIntBuffer.put(0,(int) h_vr);
    	valueReferenceIntBuffer.put(1,(int) v_vr);
    	valueReferenceIntBuffer.put(2,(int) g_vr);
    	valueReferenceIntBuffer.put(3,(int) e_vr);
    	
		setEnableLogging(enableLogging);

        if (enableLogging) {
            System.out.println("FMUCoSimulation: about to load "
                    + sharedLibrary);
        }
        nativeLibrary = NativeLibrary.getInstance(sharedLibrary);
        String fmuLocation = new File(fmuFileName).toURI().toURL().toString();

        //create callback object
        Fmi2CallbackFunctions.ByValue callbacks = new Fmi2CallbackFunctions.ByValue(
        		new Fmu2CallbackFunctions.FMULogger(), new Fmu2CallbackFunctions.FMUAllocateMemory(),
        		new Fmu2CallbackFunctions.FMUFreeMemory(),
        		new Fmu2CallbackFunctions.FMUStepFinished(), 
                null);
        
        byte loggingOn = enableLogging ? (byte) 1 : (byte) 0;
        loggingOn = (byte) 0;

        Function instantiateSlave = getFunction("fmi2Instantiate");
        Pointer fmiComponent = (Pointer) instantiateSlave.invoke(Pointer.class,
        					new Object[] {id, Fmi2Type.fmi2CoSimulation, 
        					guid, fmuLocation, callbacks, false, loggingOn});
        if (fmiComponent.equals(Pointer.NULL)) {
            throw new RuntimeException("Could not instantiate model.");
        }

        double timeout = 1;
        double startTime = 0;

        //setup experiments
        invoke("fmi2SetupExperiment", new Object[]{fmiComponent, false, startTime, false, timeout}, "Could not initialize slave: ");
        
        //Initialize FMUs 
        invoke("fmi2EnterInitializationMode", new Object[]{fmiComponent}, "error entering intialization mode");
        
        invoke("fmi2ExitInitializationMode", new Object[]{fmiComponent}, "error exiting intialization mode");
        
        //Simulation subphase
        double tc = startTime; 
        double h = 0.1; 
        int fmiFlag = Fmi2Status.fmi2OK;
        while ((tc<timeout) && (fmiFlag == Fmi2Status.fmi2OK)){
        	//retrieve outputs
        	
        	
        	//call slaves and check status
            Function doStep = getFunction("fmi2DoStep");
            fmiFlag = (Integer) doStep.invoke(Integer.class,
            					new Object[] {fmiComponent, tc, h, false});	
            
            Function getreal = getFunction("fmi2GetReal");
            fmiFlag = ((Integer) getreal.invoke(Integer.class, new Object[] {
                    fmiComponent, valueReferenceIntBuffer, new NativeSizeT(4),
                    valueBuffer })).intValue();
            System.out.println("time= " + tc + " step size= " + stepSize + 
            		" variable h= " + valueBuffer.get(0) + 
            		" variable v= " + valueBuffer.get(1) + 
            		" variable g= " + valueBuffer.get(2) + 
            		" variable e= " + valueBuffer.get(3)  );
            tc += h;
        }
        
        if ((fmiFlag != Fmi2Status.fmi2Error) && (fmiFlag != Fmi2Status.fmi2Fatal)){
        	invoke("fmi2Terminate", new Object[]{fmiComponent},"error when terminating");
        }
              
        if (fmiFlag != Fmi2Status.fmi2Fatal){
        	invoke("fmi2FreeInstance", new Object[]{fmiComponent},"error when freeing memory");
        }
               

//        File outputFile = new File(outputFileName);
//        PrintStream file = null;
//        try {
//	    	 //does not have this constructor
//            //file = new PrintStream(outputFile);
//            file = new PrintStream(outputFileName);
//            if (enableLogging) {
//                System.out.println("FMUCoSimulation: about to write header");
//            }
//            // Generate header row
//            OutputRow.outputRow(nativeLibrary, fmiModelDescription,
//                    fmiComponent, startTime, file, csvSeparator, Boolean.TRUE);
//            // Output the initial values.
//            OutputRow.outputRow(_nativeLibrary, fmiModelDescription,
//                    fmiComponent, startTime, file, csvSeparator, Boolean.FALSE);
//            // Loop until the time is greater than the end time.
//            double time = startTime;
//
//            Function doStep = getFunction("_fmiDoStep");
//            while (time < endTime) {
//                if (enableLogging) {
//                    System.out.println("FMUCoSimulation: about to call "
//                            + _modelIdentifier
//                            + "_fmiDoStep(Component, /* time */ " + time
//                            + ", /* stepSize */" + stepSize + ", 1)");
//                }
//                invoke(doStep, new Object[] { fmiComponent, time, stepSize,
//                        (byte) 1 }, "Could not simulate, time was " + time
//                        + ": ");
//                time += stepSize;
//                // Generate a line for this step
//                OutputRow.outputRow(_nativeLibrary, fmiModelDescription,
//                        fmiComponent, time, file, csvSeparator, Boolean.FALSE);
//            }
//	    invoke("_fmiTerminateSlave", new Object[] { fmiComponent },
//		   "Could not terminate slave: ");
//
//	    // Don't throw an exception while freeing a slave.  Some
//	    // fmiTerminateSlave calls free the slave for us.
//	    Function freeSlave = getFunction("_fmiFreeSlaveInstance");
//	    int fmiFlag = ((Integer) freeSlave.invoke(Integer.class,
//						      new Object[] { fmiComponent })).intValue();
//	    if (fmiFlag >= FMILibrary.FMIStatus.fmiWarning) {
//		new Exception("Warning: Could not free slave instance: " + fmiFlag)
//                    .printStackTrace();
//	    }
//        } finally {
//            if (file != null) {
//                file.close();
//            }
//	    if (fmiModelDescription != null) {
//		fmiModelDescription.dispose();
//	    }
//	}
//
//        if (enableLogging) {
//            System.out.println("Results are in "
//                    + outputFile.getCanonicalPath());
//	    System.out.flush();
//        }	
		
	}
 
	 protected static void setEnableLogging(boolean logging) {
		 	enableLogging = logging;
	        enableLogging = true;
	    } 
	 
}
