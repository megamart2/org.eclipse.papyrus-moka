package org.eclipse.papyrus.moka.simex.viewpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.elementtypesconfigurations.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.elementtypesconfigurations.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.DropStrategyManager;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.listener.IPolicyCheckerListener;
import org.eclipse.papyrus.moka.simex.dnd.strategy.AbstractDropInActivityStrategy;




public class SimexViewpointListener implements IPolicyCheckerListener {

	private static final String SIMEX_VIEWPOINT_NAME = "Simex Viewpoint";
	private static final String SIMEX_ELEMENT_TYPES_DEF_PATH = "platform:/plugin/org.eclipse.papyrus.moka.simex/config/simex.elementtypesconfigurations";
	private  String SIMEX_ELEMENT_TYPES_ID ="org.eclipse.papyrus.moka.simex.elementtypes"; 
	
	protected boolean isActive = false;

	public SimexViewpointListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void policyCheckerChanged(PolicyChecker policyChecker) {
		if (SIMEX_VIEWPOINT_NAME.equals(policyChecker.getViewpoint().getName())){
			if(!isActive){
				activatePerspective();
				isActive = true;
			}
		}else  {
			if (isActive){
				desactivatePerspective();
				isActive = false;
			}	
		}	

	}

	
	
	
	//TODO : define a list of drop strategy to activate by default 
	//in addition to the Activity diag drop strategies
	private final List<String> dropStrategiesToActivate= Arrays.asList(	);


	private List<DropStrategy> dropStrategiesToRestore = null;
	private ElementTypeSetConfiguration loadedConfig =null;  

	

	protected void desactivatePerspective() {
		
		restoreDndStrategies();
		removeElementTypesConfiguration();
	}

	protected void activatePerspective() {
		
		configureDndStrategies();
		registerElementTypesConfiguration();	
	}


	protected void registerElementTypesConfiguration() {

		ElementTypeSetConfiguration iptgConfig = getSimexElementTypesConfig(); 
		List<ElementTypeSetConfiguration> tmpCollection = new ArrayList<ElementTypeSetConfiguration>();
		tmpCollection.add(iptgConfig);
		ElementTypeSetConfigurationRegistry.getInstance().loadElementTypeSetConfigurations(tmpCollection);
		ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations().put(SIMEX_ELEMENT_TYPES_ID, iptgConfig);


	}



	protected ElementTypeSetConfiguration getSimexElementTypesConfig() {
		if (loadedConfig != null){
			return loadedConfig ;
		}else {
			Map<String, ElementTypeSetConfiguration> elementTypesConfigs = ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations();
			ResourceSet  elementTypesResSet;
			if (! elementTypesConfigs.isEmpty()){
				ElementTypeSetConfiguration firstConfig = elementTypesConfigs.values().iterator().next();
				elementTypesResSet = firstConfig.eResource().getResourceSet();

			}else  {
				elementTypesResSet = new ResourceSetImpl();
			}
			Resource res = elementTypesResSet.getResource(URI.createURI(SIMEX_ELEMENT_TYPES_DEF_PATH), true);
			loadedConfig = (ElementTypeSetConfiguration) res.getContents().get(0);
			return loadedConfig;
		}

	}

	public void removeElementTypesConfiguration() {
		ElementTypeSetConfigurationRegistry.getInstance().unload(SIMEX_ELEMENT_TYPES_ID);

	}



	protected void restoreDndStrategies() {
		if (dropStrategiesToRestore != null) {
			for (DropStrategy strategy : DropStrategyManager.instance.getAllStrategies()){
				if (dropStrategiesToRestore.contains(strategy)){
					DropStrategyManager.instance.setActive(strategy, true);
				}else {
					DropStrategyManager.instance.setActive(strategy, false);
				}
			}
		}


	}

	protected void configureDndStrategies() {
		dropStrategiesToRestore = new ArrayList<DropStrategy>();

		for (DropStrategy strategy : DropStrategyManager.instance.getAllStrategies()){
			if (DropStrategyManager.instance.isActive(strategy)){
				if (! shouldBeActivated(strategy)){
					DropStrategyManager.instance.setActive(strategy, false);
				}
				dropStrategiesToRestore.add(strategy);
			}else if (shouldBeActivated(strategy)){
				DropStrategyManager.instance.setActive(strategy, true);
			}
		}

	}


	protected boolean shouldBeActivated(DropStrategy strategy) {
		
		return dropStrategiesToActivate.contains(strategy.getID()) || strategy.getID().startsWith(AbstractDropInActivityStrategy.SIMEX_DROP_STRATEGY_PREFIX);
	}

}
