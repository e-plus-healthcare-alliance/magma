package org.obiba.magma.datasource.excel;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.obiba.magma.Value;
import org.obiba.magma.ValueSet;
import org.obiba.magma.ValueType;
import org.obiba.magma.Variable;
import org.obiba.magma.VariableValueSource;
import org.obiba.magma.VectorSource;

public class ExcelVariableValueSource implements VariableValueSource {

  private final Variable variable;

  public ExcelVariableValueSource(Variable variable) {
    this.variable = variable;
  }

  @Override
  public Variable getVariable() {
    return variable;
  }

  @NotNull
  @Override
  public ValueType getValueType() {
    return variable.getValueType();
  }

  @Nullable
  @Override
  public VectorSource asVectorSource() {
    return null;
  }

  @NotNull
  @Override
  public Value getValue(ValueSet valueSet) {
    throw new UnsupportedOperationException();
  }

}
