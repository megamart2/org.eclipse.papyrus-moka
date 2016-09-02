/*****************************************************************************
 * 
 * Copyright (c) 2016 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST Initial API and implementation
 * 
 *****************************************************************************/

package org.eclipse.papyrus.moka.fmi.master.fmilibrary;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

public class NativeSizeT extends IntegerType {

	/* A Java representation for a C type size_t, which is 32 or 64 bits.

	   Copyright (c) 2012 The Regents of the University of California.
	   All rights reserved.
	   Permission is hereby granted, without written agreement and without
	   license or royalty fees, to use, copy, modify, and distribute this
	   software and its documentation for any purpose, provided that the above
	   copyright notice and the following two paragraphs appear in all copies
	   of this software.

	   IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
	   FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
	   ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
	   THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
	   SUCH DAMAGE.

	   THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
	   INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
	   MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
	   PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
	   CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
	   ENHANCEMENTS, OR MODIFICATIONS.

	   PT_COPYRIGHT_VERSION_2
	   COPYRIGHTENDKEY

	 */
	
	private static final long serialVersionUID = 1L;

	public NativeSizeT() {
		this(0);
	}

	public NativeSizeT(long value) {
		super(Native.SIZE_T_SIZE, value, true);
	}
}
