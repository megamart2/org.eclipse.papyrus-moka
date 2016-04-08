package org.eclipse.papyrus.moka.fmi.master.fmuutils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class CoSimulationUtils {

	public void cosimulate(String fmuFileName, String dll, String id, String guid, String knownGoodFileName)
            throws Exception {
        String resultsFileName = File.createTempFile("FMUJUnitTest", "csv")
                .getCanonicalPath();
        
        new CoSimulation2Utils().simulate(fmuFileName,dll, id, guid,  1.0, 0.1,
                false /*logging*/, ',', resultsFileName);

//        String results = Fmu2CoSimulation.readFile(resultsFileName);
//        String knownGood = Fmu2CoSimulation.readFile(knownGoodFileName);
//        if (results.length() != knownGood.length()) {
//            Assert.fail(fmuFileName + ":results length " + results.length()
//                    + " != known good length " + knownGood.length()
//                    + "\nresults:\n" + results + "\nknownGood:\n" + knownGood);
//        }
//        assertArrayEquals(results.getBytes(), knownGood.getBytes());
    }

    public void cosimulate(String testName, String guid) throws Exception {
        cosimulate(topDirectory + "/resources/fmus/" + testName + ".fmu", 
        		topDirectory + "/resources/fmus/" + testName + ".dll", 
        		testName, 
        		guid, 
        		"/resources/fmus/"+ testName + ".csv");
        System.out.println(topDirectory);
    }


   

    public static String readFile(String fileName) throws IOException {
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer results = new StringBuffer();
        try {
            fileInputStream = new FileInputStream(fileName);
            dataInputStream = new DataInputStream(fileInputStream);
            bufferedReader = new BufferedReader(new InputStreamReader(
                    dataInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                results.append(line + lineSeparator);
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return results.toString();
    }

    static String lineSeparator = "\n";

    static String topDirectory;

    static {
        String userDir = System.getProperty("user.dir");
        topDirectory =userDir;
    }
    
}
