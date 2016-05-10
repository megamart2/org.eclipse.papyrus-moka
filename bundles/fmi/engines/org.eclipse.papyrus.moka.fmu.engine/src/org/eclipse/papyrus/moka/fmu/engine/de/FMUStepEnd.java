package org.eclipse.papyrus.moka.fmu.engine.de;

import java.util.concurrent.Semaphore;

import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.moka.discreteevent.actions.Action;
import org.eclipse.papyrus.moka.fmu.engine.control.FMUControlService;
import org.eclipse.papyrus.moka.fmu.engine.utils.FMUEngineUtils;

public class FMUStepEnd extends Action {

	@Override
	public void execute() {
		FMUControlService fmuControlService = FMUEngineUtils.getFMUControlService() ;
		Semaphore stepLock = fmuControlService.getStepLock() ;
		Semaphore engineLock = fmuControlService.getEngineLock() ;
		// Unlocks the FMU wrapper thread (cf. FMUControlDelegate.doStep)
		//synchronized (stepLock) {
			stepLock.release();
		//}
		// Waits for the next step (cf. FMUControlDelegate.doStep)
		if (!fmuControlService.getDebugTarget().isTerminated()) {
			//synchronized (engineLock) {
				try {
					engineLock.acquire();;
				} catch (InterruptedException e) {
					Activator.log.error(e);
				}
			//}
		}
	}

}
