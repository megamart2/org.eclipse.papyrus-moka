/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.moka.async.fuml.debug;

import org.eclipse.papyrus.moka.fuml.debug.Debug;

// TODO: Auto-generated Javadoc
/**
 * The Class AsyncDebug.
 */
public class AsyncDebug extends Debug {

	/**
	 * Println.
	 *
	 * @param message
	 *            the message
	 */
	public static void println(String message) {
		// int remainingSpaces = 30-Thread.currentThread().getName().length();
		// if(remainingSpaces < 0){
		// System.out.println(
		// String.format("%s", "["+Thread.currentThread().getName()+"]")
		// +message);
		// }else{
		// int sideSpaceCount = remainingSpaces/2;
		// System.out.println(
		// String.format("[%"+sideSpaceCount+"s%s%"+sideSpaceCount+"s]", " ",
		// Thread.currentThread().getName()
		// ," ")+message);
		// }
	}
}
