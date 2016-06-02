package org.eclipse.papyrus.moka.fmu.rcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.papyrus.moka.engine.IExecutionEngine;
//import org.eclipse.papyrus.moka.composites.CompositeStructuresExecutionEngine;
import org.eclipse.papyrus.moka.engine.IExecutionEngine.OperatingMode;
import org.eclipse.papyrus.moka.fmi.fmu.FMUHandler;
import org.eclipse.papyrus.moka.fmi.profile.util.FMIProfileUtil;
import org.eclipse.papyrus.moka.fmu.engine.MokaEngineForFMUExport;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;
import org.eclipse.papyrus.moka.fmu.json.JSONSocketClient;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class FMURCPApplication implements IApplication {

	private static final String FMU_PATH = "fmu";
	private static final String PORT_NUMBER = "port";
	private static final String MOKA_PROP_FILE_NAME = "moka.properties";
	private static final String MODEL_PATH_PROP = "model.path";
	private static final String FMU_QN = "fmu.qualifiedname";



	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("FMU RCP Running");
		CommandLine cmdLine = parseArgs(context);

		if (cmdLine != null){
			String unzippedFmuPath = cmdLine.getOptionValue(FMU_PATH);
			String portNumber = cmdLine.getOptionValue(PORT_NUMBER);

			if (portNumber == null) {
				throw new Exception("The argument "+ PORT_NUMBER +" should be specified");
			}
			int portNumberInt = Integer.parseInt(portNumber);

			JSONSocketClient client = new JSONSocketClient(portNumberInt);
			FMUEngineUtils.setJSONSocketClient(client);

			if (unzippedFmuPath == null){
				throw new Exception("The argument "+ FMU_PATH +" should be specified");
			}

			FMUHandler handler = new FMUHandler(unzippedFmuPath, false);

			File resourceFolder = handler.getResourceFolder();
			if (resourceFolder != null){
				Properties mokaProp = new Properties();
				try(InputStream propFileStream = new FileInputStream(resourceFolder.getAbsolutePath() + File.separator + MOKA_PROP_FILE_NAME)){
					mokaProp.load(propFileStream);
				}

				String modelPath = mokaProp.getProperty(MODEL_PATH_PROP);
				String fmuQN = mokaProp.getProperty(FMU_QN);

				Class fmuClass = loadFMUClass(resourceFolder, modelPath, fmuQN );
				
				if (fmuClass != null){
					launchSimulation(fmuClass);
				}

			}else {
				throw new Exception("FMU doesn't contain a resource folder");
			}
		}

		return IApplication.EXIT_OK;

	}

	private void launchSimulation(Class fmuClass) throws InterruptedException {
		MokaEngineForFMUExport engine = new MokaEngineForFMUExport();
		((IExecutionEngine)engine).init(null, fmuClass, new String[]{}, OperatingMode.NORMAL);		
		engine.start(new NullProgressMonitor());
		engine.waitForTermination();
	}

	private Class loadFMUClass(File resourceFolder, String modelPath, String fmuQN) {
		if (modelPath!= null && fmuQN != null){
			ResourceSet resSet = new ResourceSetImpl();
			URI resURI = URI.createFileURI(resourceFolder.getAbsolutePath()).appendSegment(modelPath);
			Resource res = resSet.getResource(resURI, true);
			if (res != null){
				Collection<Class> candidates = UMLUtil.<Class>findNamedElements(res, fmuQN);

				if (candidates != null){
					for( Class fmuCandidate : candidates){
						if (fmuCandidate.getAppliedStereotype(FMIProfileUtil.CS_FMU_STEREO_QUALIFIED_NAME) != null){
							return fmuCandidate;
						}
					}
				}
			}


		}
		return null;
	}

	private CommandLine parseArgs(IApplicationContext context) {
		System.out.println("Starting Moka Application");
		Map<String, Object> argMap = context.getArguments();
		String[] appArgs = (String[]) argMap.get(IApplicationContext.APPLICATION_ARGS);

		// create Options object
		Options options = new Options();
		// add t option
		options.addOption(PORT_NUMBER, true, " JSON socket server port number");
		options.addOption(FMU_PATH, true, " path to the unzipped FMU folder");

		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse( options, appArgs);
			return cmd;
		} catch (ParseException e) {

			e.printStackTrace();
		}


		return null;
	}

	


	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		// nothing to do
	}
}
