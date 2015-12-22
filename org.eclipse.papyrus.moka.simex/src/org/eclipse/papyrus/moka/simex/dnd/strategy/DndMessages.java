/*****************************************************************************
 * Copyright (c) 2015 CEA LIST
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Sebastien Revol - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.simex.dnd.strategy;

import org.eclipse.osgi.util.NLS;

/**
 * custom messages related to Drag and Drop
 *
 * @author SR246418
 *
 */
public class DndMessages extends NLS {

	
	static {
		NLS.initializeMessages("dnd-messages", DndMessages.class); //$NON-NLS-1$
	}

	private DndMessages() {
	}
	
	
	public static String AbstractDropStrategy_Description;
	public static String AbstractDropStrategy_Description_No_Ref;
	public static String AbstractDropStrategy_Label;
	
}
