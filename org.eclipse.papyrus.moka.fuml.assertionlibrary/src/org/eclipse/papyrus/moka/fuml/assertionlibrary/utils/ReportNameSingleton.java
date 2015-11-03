package org.eclipse.papyrus.moka.fuml.assertionlibrary.utils;

public class ReportNameSingleton {

	private String engineName = "";

	private String executedActivityName = "";

	/**
	 * Private constructor
	 */
	private ReportNameSingleton() {

	}

	/** Unique instance not initialized */
	private static ReportNameSingleton INSTANCE = null;

	/** Access for the unique instance of ReportNameSingleton */
	public static ReportNameSingleton getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ReportNameSingleton();
		}
		return INSTANCE;
	}

	public String getEngineName() {
		return engineName;
	}

	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

	public String getExecutedActivityName() {
		return executedActivityName;
	}

	public void setExecutedActivityName(String executedActivityName) {
		this.executedActivityName = executedActivityName;
	}

}
