package org.eclipse.papyrus.moka.fmi.exporter;


import org.eclipse.core.runtime.Plugin;


import org.osgi.framework.BundleContext;

public class Activator extends Plugin  {
	
	public static final String bundleID = "org.eclipse.papyrus.moka.fmi.exporter";
	private static Activator instance;

	public static Activator getDefault(){
		return instance;
	}
	
	
	@Override
	public void start(BundleContext context) throws Exception {
			super.start(context);
			instance = this;
	}
}
