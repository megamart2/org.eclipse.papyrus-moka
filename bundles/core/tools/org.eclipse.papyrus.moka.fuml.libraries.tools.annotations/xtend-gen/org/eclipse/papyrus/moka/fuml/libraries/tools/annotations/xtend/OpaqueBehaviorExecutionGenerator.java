/**
 * Copyright (c) 2016 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  David LOPEZ BETANCUR (CEA LIST) - david.lopez@cea.fr
 */
package org.eclipse.papyrus.moka.fuml.libraries.tools.annotations.xtend;

import org.eclipse.papyrus.moka.fuml.libraries.tools.annotations.processor.FumlLibraryData;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class OpaqueBehaviorExecutionGenerator {
  public static String className(final FumlLibraryData.OpaqueFunction f) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("� f.name.toFirstUpper �Behavior");
    return _builder.toString();
  }
  
  public static String genInputParamString(final FumlLibraryData.OpaqueFunctionParameter p, final int i, final int size) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(� p.fqType �) in.get(� i �).getValues().get(0)� IF i != size - 1 �,");
    _builder.newLine();
    _builder.append("�ENDIF�");
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String generate(final FumlLibraryData.OpaqueFunction f, final FumlLibraryData lib) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package � lib.packageName �;");
    _builder.newLine();
    _builder.append("�var hasOutput = f.countParams(ParameterDirection.OUT) > 0 �");
    _builder.newLine();
    _builder.newLine();
    _builder.append("�IF hasOutput�");
    _builder.newLine();
    _builder.append("import java.util.ArrayList;");
    _builder.newLine();
    _builder.append("�ENDIF�\t");
    _builder.newLine();
    _builder.append("import java.util.List;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;");
    _builder.newLine();
    _builder.append("import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;");
    _builder.newLine();
    _builder.append("import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class � className(f) � extends OpaqueBehaviorExecution{");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void doBody(List<IParameterValue> in, List<IParameterValue> out) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�var inputIdx = 0 �");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�IF hasOutput �");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("List<IValue> outputs = new ArrayList<IValue>();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�ENDIF�");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�FOR op : f.params �");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("�IF op.direction == ParameterDirection.OUT �");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("� op.fqType � � op.name � = new � op.fqType �(); ");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("outputs.add(� op.name �);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("�ENDIF�");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("�IF op.direction == ParameterDirection.IN �");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("� op.fqType � � op.name � = (� op.fqType �) in.get(� inputIdx++ �).getValues().get(0);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("�ENDIF�");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("� ENDFOR �");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�lib.classElement.simpleName�.�f.methodName�(�FOR op : f.params SEPARATOR \',\' ��op.name�� ENDFOR �);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�IF hasOutput �");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("out.get(0).setValues(outputs);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("�ENDIF�");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IValue new_() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return new � className(f) �();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
