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

import com.google.common.base.Objects;
import java.util.List;
import javax.lang.model.element.Name;
import org.eclipse.papyrus.moka.fuml.libraries.tools.annotations.processor.FumlLibraryData;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class OpaqueBehaviorExecutionGenerator {
  public static String className(final FumlLibraryData.OpaqueFunction f) {
    StringConcatenation _builder = new StringConcatenation();
    String _firstUpper = StringExtensions.toFirstUpper(f.name());
    _builder.append(_firstUpper);
    _builder.append("Behavior");
    return _builder.toString();
  }
  
  public static String genInputParamString(final FumlLibraryData.OpaqueFunctionParameter p, final int i, final int size) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    String _fqType = p.fqType();
    _builder.append(_fqType);
    _builder.append(") in.get(");
    _builder.append(i);
    _builder.append(").getValues().get(0)");
    {
      if ((i != (size - 1))) {
        _builder.append(",");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  public static String generate(final FumlLibraryData.OpaqueFunction f, final FumlLibraryData lib) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = lib.packageName();
    _builder.append(_packageName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    int _countParams = f.countParams(FumlLibraryData.ParameterDirection.OUT);
    boolean hasOutput = (_countParams > 0);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      if (hasOutput) {
        _builder.append("import java.util.ArrayList;");
        _builder.newLine();
      }
    }
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
    _builder.append("public class ");
    String _className = OpaqueBehaviorExecutionGenerator.className(f);
    _builder.append(_className);
    _builder.append(" extends OpaqueBehaviorExecution{");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void doBody(List<IParameterValue> in, List<IParameterValue> out) {");
    _builder.newLine();
    _builder.append("\t\t");
    int inputIdx = 0;
    _builder.newLineIfNotEmpty();
    {
      if (hasOutput) {
        _builder.append("\t\t");
        _builder.append("List<IValue> outputs = new ArrayList<IValue>();");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      List<FumlLibraryData.OpaqueFunctionParameter> _params = f.params();
      for(final FumlLibraryData.OpaqueFunctionParameter op : _params) {
        {
          FumlLibraryData.ParameterDirection _direction = op.direction();
          boolean _equals = Objects.equal(_direction, FumlLibraryData.ParameterDirection.OUT);
          if (_equals) {
            _builder.append("\t\t");
            String _fqType = op.fqType();
            _builder.append(_fqType, "\t\t");
            _builder.append(" ");
            String _name = op.name();
            _builder.append(_name, "\t\t");
            _builder.append(" = new ");
            String _fqType_1 = op.fqType();
            _builder.append(_fqType_1, "\t\t");
            _builder.append("(); ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("outputs.add(");
            String _name_1 = op.name();
            _builder.append(_name_1, "\t\t");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          FumlLibraryData.ParameterDirection _direction_1 = op.direction();
          boolean _equals_1 = Objects.equal(_direction_1, FumlLibraryData.ParameterDirection.IN);
          if (_equals_1) {
            _builder.append("\t\t");
            String _fqType_2 = op.fqType();
            _builder.append(_fqType_2, "\t\t");
            _builder.append(" ");
            String _name_2 = op.name();
            _builder.append(_name_2, "\t\t");
            _builder.append(" = (");
            String _fqType_3 = op.fqType();
            _builder.append(_fqType_3, "\t\t");
            _builder.append(") in.get(");
            int _plusPlus = inputIdx++;
            _builder.append(_plusPlus, "\t\t");
            _builder.append(").getValues().get(0);");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    Name _simpleName = lib.classElement().getSimpleName();
    _builder.append(_simpleName, "\t\t");
    _builder.append(".");
    String _methodName = f.methodName();
    _builder.append(_methodName, "\t\t");
    _builder.append("(");
    {
      List<FumlLibraryData.OpaqueFunctionParameter> _params_1 = f.params();
      boolean _hasElements = false;
      for(final FumlLibraryData.OpaqueFunctionParameter op_1 : _params_1) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "\t\t");
        }
        String _name_3 = op_1.name();
        _builder.append(_name_3, "\t\t");
      }
    }
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      if (hasOutput) {
        _builder.append("\t\t");
        _builder.append("out.get(0).setValues(outputs);");
        _builder.newLine();
      }
    }
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
    _builder.append("return new ");
    String _className_1 = OpaqueBehaviorExecutionGenerator.className(f);
    _builder.append(_className_1, "\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
