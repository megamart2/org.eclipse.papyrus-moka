package org.eclipse.papyrus.moka.fmi.master.fmulibrary;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2CallbackFunctions;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.Fmi2Library;
import org.eclipse.papyrus.moka.fmi.master.fmilibrary.NativeSizeT;

import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;

/**
 * A class implementing the callback functions defined in FMI2Library.java.
 * Contains methods for the invocation of methods of a given DLL 
 * via JNA (Java Native Access) API.
 * @author sahar.guermazi@cea.fr
 *
 */
public class Fmu2Library implements Fmi2Library{
	public static String JNA_LIBRARY_NAME = "";
	public static NativeLibrary JNA_NATIVE_LIB ;
	//= NativeLibrary.getInstance(Fmu2Library.JNA_LIBRARY_NAME)
	public static Fmi2CallbackFunctions.ByValue cbf;
	
	/**
	 * A class implementing the fmi2CallbackLogger defined in FMI2Library.java.
	 * TODO call logger to trace information during simulation.
	 * @author sahar.guermazi@cea.fr
	 *
	 */
    public static class FMULogger implements fmi2CallbackLogger {
		public void invoke(Pointer componentEnvironment, String instanceName,
				int status, String category,  String message,
				Pointer varArgs1) {
			// TODO Auto-generated method stub
			 System.out.println(message);
		}
    }
    
	/**
	 * A class implementing the fmi2CallbackAllocateMemory defined in FMI2Library.java.
	 * Allocate memory that will be used to instantiate an FMU.
	 * @author sahar.guermazi@cea.fr
	 *
	 */
    public static class FMUAllocateMemory implements fmi2CallbackAllocateMemory {
    	public Pointer invoke(NativeSizeT numberOfObjects, NativeSizeT size) {
            int numberOfObjectsValue = numberOfObjects.intValue();
            if (numberOfObjectsValue <= 0) {
                numberOfObjectsValue = 1;
            }
            Memory memory = new Memory(numberOfObjectsValue * size.intValue());
            // FIXME: not sure about alignment.
            Memory alignedMemory = memory.align(4);
            memory.clear();
            Pointer pointer = alignedMemory.share(0);
            pointers.add(pointer);

            return pointer;
        }
        public static Set<Pointer> pointers = new HashSet<Pointer>();
    }
    
	/**
	 * A class implementing the fmi2CallbackFreeMemory defined in FMI2Library.java.
	 * free memory that was allocated at the beginning of the simulation.
	 * @author sahar.guermazi@cea.fr
	 *
	 */
    public static class FMUFreeMemory implements fmi2CallbackFreeMemory {
		public void invoke(Pointer object) {
			FMUAllocateMemory.pointers.remove(object);
        }
    }
    
	/**
	 * A class implementing the fmi2CallbackFreeMemory defined in FMI2Library.java.
	 * TODO Add instructions to know about a finished step.
	 * @author sahar.guermazi@cea.fr
	 *
	 */
    public static class FMUStepFinished implements fmi2StepFinished {
		public void invoke(Pointer fmiComponent, int status) {
            // FIXME: More should be done here.
            System.out.println("\n Java fmiStepFinished: " + fmiComponent + " "
                    + status);
        }
    }
    
    /**
     * Invokes a function returning "void" in the DLL via JNA
     * @param name : the name of the function to invoke
     * @param arguments : the parameters of the function as defined in FMI2.0 standard header files
     * @param message : the message to log if an error occurs
     */
    public void invoke(String name, NativeLibrary dll, Object[] arguments, String message){
    	//Function f = Fmu2Library.JNA_NATIVE_LIB.getFunction(name);
    	Function f = dll.getFunction(name);
    	f.invoke(arguments);
    }
    
    /**
     * Invokes a function returning a pointer in the DLL via JNA
     * @param name : the name of the function to invoke
     * @param arguments : the parameters of the function as defined in FMI2.0 standard header files
     * @param message : the message to log if an error occurs
     * @return
     */
    public Pointer invokePointer(String name, NativeLibrary dll, Object[] arguments, String message){
    	 //Function f = Fmu2Library.JNA_NATIVE_LIB.getFunction(name);
    	Function f = dll.getFunction(name);
    	return (Pointer) f.invoke(Pointer.class,arguments);
    }
    
    /**
     * Invokes a function returning an Integer in the DLL via JNA
     * @param name : the name of the function to invoke
     * @param arguments : the parameters of the function as defined in FMI2.0 standard header files
     * @param message : the message to log if an error occurs
     * @return
     */
    public int invokeInteger(String name,NativeLibrary dll, Object[] arguments, String message){
    	 //Function f = Fmu2Library.JNA_NATIVE_LIB.getFunction(name);
    	Function f = dll.getFunction(name);
    	return (Integer) f.invoke(Integer.class,arguments);
    }
    
    /**
     * Invokes a function returning a string in the DLL via JNA
     * @param name : the name of the function to invoke
     * @param arguments : the parameters of the function as defined in FMI2.0 standard header files
     * @param message : the message to log if an error occurs
     * @return
     */
    public String invokeString(String name, NativeLibrary dll,Object[] arguments, String message){
    	 //Function f = Fmu2Library.JNA_NATIVE_LIB.getFunction(name);
    	Function f = dll.getFunction(name);
    	return (String) f.invoke(String.class,arguments);
    }
    
}
