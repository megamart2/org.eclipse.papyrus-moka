package org.eclipse.papyrus.moka.fmu.engine.de;

import java.util.concurrent.Semaphore;

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
		stepLock.release();
		// Waits for the next step (cf. FMUControlDelegate.doStep)
		try {
			engineLock.acquire();;
		} catch (InterruptedException e) {
			// Activator.log.error(e);
		}
	}

}
